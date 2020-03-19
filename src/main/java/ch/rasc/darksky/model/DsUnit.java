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

public enum DsUnit {

	/**
	 * Imperial units (the default)
	 */
	US("us"),

	/**
	 * Returns results in SI units. In particular, properties now have the following
	 * units:
	 * <p>
	 * <ul>
	 * <li>{@link DsDataPoint#summary()}: Any summaries containing temperature or snow
	 * accumulation units will have their values in degrees Celsius or in centimeters
	 * (respectively).</li>
	 * <li>{@link DsDataPoint#nearestStormDistance()}: Kilometers.</li>
	 * <li>{@link DsDataPoint#precipIntensity()}: Millimeters per hour.</li>
	 * <li>{@link DsDataPoint#precipIntensityMax()}: Millimeters per hour.</li>
	 * <li>{@link DsDataPoint#precipAccumulation()}: Centimeters.</li>
	 * <li>{@link DsDataPoint#temperature()}: Degrees Celsius.</li>
	 * <li>{@link DsDataPoint#temperatureMin()}: Degrees Celsius.</li>
	 * <li>{@link DsDataPoint#temperatureMax()}: Degrees Celsius.</li>
	 * <li>{@link DsDataPoint#apparentTemperature()}: Degrees Celsius.</li>
	 * <li>{@link DsDataPoint#dewPoint()}: Degrees Celsius.</li>
	 * <li>{@link DsDataPoint#windSpeed()}: Meters per second.</li>
	 * <li>{@link DsDataPoint#pressure()}: Hectopascals.</li>
	 * <li>{@link DsDataPoint#visibility()}: Kilometers.</li>
	 * </ul>
	 */
	SI("si"),

	/**
	 * Same as {@link #SI}, except that {@link DsDataPoint#windSpeed()} is in kilometers
	 * per hour
	 */
	CA("ca"),

	/**
	 * Same as {@link #SI}, except that nearestStormDistance and visibility are in miles
	 * and windSpeed is in miles per hour
	 */
	UK2("uk2"),

	/**
	 * Selects the relevant units automatically, based on geographic location.
	 */
	AUTO("auto"),

	UNKNOWN(null);

	private String jsonValue;

	private DsUnit(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public String getJsonValue() {
		return this.jsonValue;
	}

	public static DsUnit findByJsonValue(String jsonValue) {
		for (DsUnit unit : DsUnit.values()) {
			if (jsonValue.equals(unit.jsonValue)) {
				return unit;
			}
		}

		if (jsonValue != null) {
			return UNKNOWN;
		}

		return null;
	}
}
