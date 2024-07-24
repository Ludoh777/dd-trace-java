package datadog.trace.api;

public enum UserIdCollectionMode {
  IDENTIFICATION("identification", "ident"),
  ANONYMIZATION("anonymization", "anon"),
  DISABLED("disabled");

  private final String[] values;

  UserIdCollectionMode(final String... values) {
    this.values = values;
  }

  public static UserIdCollectionMode fromString(String collectionMode, String trackingMode) {
    if (collectionMode == null && trackingMode != null) {
      return fromTracking(trackingMode);
    } else {
      return fromMode(collectionMode);
    }
  }

  private static UserIdCollectionMode fromMode(String mode) {
    if (mode == null || IDENTIFICATION.matches(mode)) {
      return IDENTIFICATION;
    } else if (ANONYMIZATION.matches(mode)) {
      return ANONYMIZATION;
    }
    return DISABLED;
  }

  private static UserIdCollectionMode fromTracking(String tracking) {
    switch (UserEventTrackingMode.fromString(tracking)) {
      case SAFE:
        return ANONYMIZATION;
      case EXTENDED:
        return IDENTIFICATION;
      default:
        return DISABLED;
    }
  }

  private boolean matches(final String mode) {
    for (String value : values) {
      if (value.equalsIgnoreCase(mode)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return values[0];
  }
}
