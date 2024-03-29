package com.datadog.debugger.util;

import datadog.trace.relocate.api.RatelimitedLogger;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;

/** Helper class for rate limiting & logging exceptions */
public class ExceptionHelper {
  private static final Object[] EMPTY_ARRAY = new Object[0];

  public static void logException(Logger log, Throwable ex, String message, Object... params) {
    if (log.isDebugEnabled()) {
      if (params == null) {
        params = EMPTY_ARRAY;
      }
      Object[] fullParams = Arrays.copyOf(params, params.length + 1);
      fullParams[params.length] = ex;
      log.debug(message, fullParams);
    } else {
      log.warn(message + " " + ex.toString(), params);
    }
  }

  public static void rateLimitedLogException(
      RatelimitedLogger ratelimitedLogger,
      Logger log,
      Throwable ex,
      String message,
      Object... params) {
    if (log.isDebugEnabled()) {
      logException(log, ex, message, params);
    } else {
      ratelimitedLogger.warn(message + " " + ex.toString(), params);
    }
  }

  public static String foldExceptionStackTrace(Throwable t) {
    StringWriter writer = new StringWriter();
    t.printStackTrace(new NoNewLinePrintWriter(writer));
    return writer.toString();
  }

  private static class NoNewLinePrintWriter extends PrintWriter {
    public NoNewLinePrintWriter(Writer out) {
      super(out);
    }

    @Override
    public void println() {}

    @Override
    public void write(String s, int off, int len) {
      super.write(s.replace('\t', ' '), off, len);
    }
  }

  public static Throwable getInnerMostThrowable(Throwable t) {
    int i = 100;
    while (t.getCause() != null && i > 0) {
      t = t.getCause();
      i--;
    }
    if (i == 0) {
      return null;
    }
    return t;
  }

  public static StackTraceElement[] flattenStackTrace(Throwable t) {
    List<StackTraceElement> result = new ArrayList<>();
    result.addAll(Arrays.asList(t.getStackTrace()));
    if (t.getCause() != null) {
      internalFlattenStackTrace(t.getCause(), t.getStackTrace(), result);
    }
    return result.toArray(new StackTraceElement[0]);
  }

  private static void internalFlattenStackTrace(
      Throwable t, StackTraceElement[] enclosingTrace, List<StackTraceElement> elements) {
    StackTraceElement[] trace = t.getStackTrace();
    int m = trace.length - 1;
    int n = enclosingTrace.length - 1;
    while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
      m--;
      n--;
    }
    for (int i = 0; i <= m; i++) {
      elements.add(trace[i]);
    }
    if (t.getCause() != null) {
      internalFlattenStackTrace(t.getCause(), trace, elements);
    }
  }

  public static int[] createThrowableMapping(Throwable innerMost, Throwable current) {
    StackTraceElement[] trace = innerMost.getStackTrace();
    StackTraceElement[] currentTrace = flattenStackTrace(current);
    int[] mapping = new int[trace.length];
    for (int i = 0; i < trace.length; i++) {
      mapping[i] = -1;
      for (int j = 0; j < currentTrace.length; j++) {
        if (trace[i].equals(currentTrace[j])) {
          mapping[i] = j;
          break;
        }
      }
    }
    return mapping;
  }
}
