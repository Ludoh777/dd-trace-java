package datadog.trace.instrumentation.kafka_clients38;

import static java.nio.charset.StandardCharsets.UTF_8;

import datadog.trace.api.Config;
import datadog.trace.bootstrap.instrumentation.api.AgentPropagation;
import java.nio.ByteBuffer;
import java.util.Base64;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextMapExtractAdapter
    implements AgentPropagation.ContextVisitor<Headers>,
        AgentPropagation.BinaryContextVisitor<Headers> {

  private static final Logger log = LoggerFactory.getLogger(TextMapExtractAdapter.class);

  public static final TextMapExtractAdapter GETTER =
      new TextMapExtractAdapter(Config.get().isKafkaClientBase64DecodingEnabled());

  private final Base64.Decoder base64;

  public TextMapExtractAdapter(boolean base64DecodeHeaders) {
    this.base64 = base64DecodeHeaders ? Base64.getDecoder() : null;
  }

  @Override
  public void forEachKey(Headers carrier, AgentPropagation.KeyClassifier classifier) {
    for (Header header : carrier) {
      String key = header.key();
      byte[] value = header.value();
      if (null != value) {
        String string =
            base64 != null
                ? new String(base64.decode(header.value()), UTF_8)
                : new String(header.value(), UTF_8);
        if (!classifier.accept(key, string)) {
          return;
        }
      }
    }
  }

  @Override
  public void forEachKey(Headers carrier, AgentPropagation.BinaryKeyClassifier classifier) {
    for (Header header : carrier) {
      String key = header.key();
      byte[] value = header.value();
      if (null != value) {
        if (!classifier.accept(key, value)) {
          return;
        }
      }
    }
  }

  public long extractTimeInQueueStart(Headers carrier) {
    Header header = carrier.lastHeader(KafkaDecorator.KAFKA_PRODUCED_KEY);
    if (null != header) {
      try {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.put(base64 != null ? base64.decode(header.value()) : header.value());
        buf.flip();
        return buf.getLong();
      } catch (Exception e) {
        log.debug("Unable to get kafka produced time", e);
      }
    }
    return 0;
  }
}
