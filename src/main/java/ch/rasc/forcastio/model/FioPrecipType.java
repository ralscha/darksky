/**
 * Copyright 2016-2016 Ralph Schaer <ralphschaer@gmail.com>
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
package ch.rasc.forcastio.model;

public enum FioPrecipType {
	RAIN("rain"), SNOW("snow"), SLEET("sleet"), UNKNOWN(null);

	private String jsonValue;

	private FioPrecipType(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public static FioPrecipType findByJsonValue(String jsonValue) {
		for (FioPrecipType en : FioPrecipType.values()) {
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
		return jsonValue;
	}

}
