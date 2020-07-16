package datadog.trace.core.propagation

import datadog.trace.bootstrap.instrumentation.api.AgentPropagation
import datadog.trace.bootstrap.instrumentation.api.CachingContextVisitor

import static datadog.trace.bootstrap.instrumentation.api.AgentPropagation.KeyClassifier.IGNORE

class MapSetter implements AgentPropagation.Setter<Map<String, String>> {
  static final INSTANCE = new MapSetter()

  @Override
  void set(Map<String, String> carrier, String key, String value) {
    carrier.put(key, value)
  }
}

class MapGetter extends CachingContextVisitor<Map<String, String>> {
  static final INSTANCE = new MapGetter()

  @Override
  void forEachKey(Map<String, String> carrier,
                  AgentPropagation.KeyClassifier classifier) {
    for (Map.Entry<String, String> entry : carrier.entrySet()) {
      String lowerCaseKey = toLowerCase(entry.getKey())
      int classification = classifier.classify(lowerCaseKey)
      if (classification != IGNORE) {
        if (!classifier.accept(classification, lowerCaseKey, entry.getValue())) {
          return
        }
      }
    }
  }
}
