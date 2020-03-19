/**
 * Copyright 2016-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.darksky.model;

public enum DsPrecipType {
	RAIN("rain"), SNOW("snow"), SLEET("sleet"), UNKNOWN(null);

	private String jsonValue;

	private DsPrecipType(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static DsPrecipType findByJsonValue(String jsonValue) {
		for (DsPrecipType en : DsPrecipType.values()) {
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
