package ch.rasc.darksky.model;

public enum DsAlertSeverity {
	ADVISORY("advisory"), WATCH("watch"), WARNING("warning"), UNKNOWN(null);

	private String jsonValue;

	private DsAlertSeverity(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static DsAlertSeverity findByJsonValue(String jsonValue) {
		for (DsAlertSeverity en : DsAlertSeverity.values()) {
			if (jsonValue.equals(en.jsonValue)) {
				return en;
			}
		}

		if (jsonValue != null) {
			return UNKNOWN;
		}

		return null;
	}

	public String getJsonValue() {
		return this.jsonValue;
	}

}
