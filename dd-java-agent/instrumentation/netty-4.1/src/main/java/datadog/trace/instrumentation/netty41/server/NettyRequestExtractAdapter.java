package datadog.trace.instrumentation.netty41.server;

import static datadog.trace.bootstrap.instrumentation.api.AgentPropagation.KeyClassifier.IGNORE;

import datadog.trace.bootstrap.instrumentation.api.AgentPropagation;
import datadog.trace.bootstrap.instrumentation.api.CachingContextVisitor;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.Map;

public class NettyRequestExtractAdapter extends CachingContextVisitor<HttpHeaders> {

  public static final NettyRequestExtractAdapter GETTER = new NettyRequestExtractAdapter();

  @Override
  public void forEachKey(HttpHeaders carrier, AgentPropagation.KeyClassifier classifier) {
    for (Map.Entry<String, String> header : carrier) {
      String lowerCaseKey = toLowerCase(header.getKey());
      int classification = classifier.classify(lowerCaseKey);
      if (classification != IGNORE) {
        if (!classifier.accept(classification, lowerCaseKey, header.getValue())) {
          return;
        }
      }
    }
  }
}
