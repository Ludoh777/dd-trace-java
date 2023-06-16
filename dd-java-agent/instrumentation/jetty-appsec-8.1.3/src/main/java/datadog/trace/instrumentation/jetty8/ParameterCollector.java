package datadog.trace.instrumentation.jetty8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ParameterCollector {
  boolean isEmpty();

  void put(String key, String value);

  Map<String, List<String>> getMap();

  class ParameterCollectorNoop implements ParameterCollector {
    public static final ParameterCollector INSTANCE = new ParameterCollectorNoop();

    private ParameterCollectorNoop() {}

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public void put(String key, String value) {}

    @Override
    public Map<String, List<String>> getMap() {
      return Collections.emptyMap();
    }
  }

  class ParameterCollectorImpl implements ParameterCollector {
    public Map<String, List<String>> map;

    public boolean isEmpty() {
      return map == null;
    }

    public void put(String key, String value) {
      if (map == null) {
        map = new HashMap<>();
      }
      List<String> strings = map.get(key);
      if (strings == null) {
        strings = new ArrayList<>();
        map.put(key, strings);
      }
      strings.add(value);
    }

    @Override
    public Map<String, List<String>> getMap() {
      return map;
    }
  }
}
