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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.forcastio.converter.FioIconDeserializer;
import ch.rasc.forcastio.converter.FioPrecipTypeDeserializer;

/**
 * A data point object contains various properties, each representing a particular weather
 * phenomenon occurring at a specific point in time. All of these properties are optional,
 * and will only be set if we have that type of information for that location and time.
 * <p>
 * minutely data points are always aligned to the top of the minute, hourly points to the
 * top of the hour, and daily points to midnight of the day, all according to the local
 * time zone.
 * <p>
 * Data points in the daily data block are special: instead of representing the weather
 * phenomena at a given instant of time, they are an aggregate point representing (unless
 * otherwise noted) the average weather conditions that will occur over the entire day.
 * <p>
 * All of the numeric, non-time fields may, optionally, have an associated Error value
 * defined (with the property precipIntensityError, windSpeedError, pressureError, etc.),
 * representing our system's confidence in its prediction. Such properties represent
 * standard deviations of the value of their associated property; small error values
 * therefore represent a strong confidence, while large error values represent a weak
 * confidence. These properties are omitted where the confidence is not precisely known.
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableFioDataPoint.class)
public abstract class FioDataPoint {

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which this
	 * data point occurs
	 */
	public abstract long time();

	/**
	 * A human-readable text summary of this data point. (Do not use this value for
	 * automated purposes: you should use the icon property, instead.)
	 */
	@Nullable
	public abstract String summary();

	/**
	 * A machine-readable text summary of this data point, suitable for selecting an icon
	 * for display.
	 */
	@Nullable
	@JsonDeserialize(using = FioIconDeserializer.class)
	public abstract FioIcon icon();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of the last
	 * sunrise before the solar noon closest to local noon on the given day. (Note: near
	 * the poles, these may occur on a different day entirely!)
	 * <p>
	 * Only defined on daily data points
	 */
	@Nullable
	public abstract Long sunriseTime();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) of the first
	 * sunset after the solar noon closest to local noon on the given day. (Note: near the
	 * poles, these may occur on a different day entirely!)
	 * <p>
	 * Only defined on daily data points
	 */
	@Nullable
	public abstract Long sunsetTime();

	/**
	 * A number representing the fractional part of the lunation number of the given day:
	 * a value of 0 corresponds to a new moon, 0.25 to a first quarter moon, 0.5 to a full
	 * moon, and 0.75 to a last quarter moon. (The ranges in between these represent
	 * waxing crescent, waxing gibbous, waning gibbous, and waning crescent moons,
	 * respectively.)
	 * <p>
	 * Only defined on daily data points
	 */
	@Nullable
	public abstract BigDecimal moonPhase();

	/**
	 * A numerical value representing the distance to the nearest storm in miles. (This
	 * value is very approximate and should not be used in scenarios requiring accurate
	 * results. In particular, a storm distance of zero doesn't necessarily refer to a
	 * storm at the requested location, but rather a storm in the vicinity of that
	 * location.)
	 * <p>
	 * Only defined on currently data points
	 */
	@Nullable
	public abstract BigDecimal nearestStormDistance();

	/**
	 * A numerical value representing the direction of the nearest storm in degrees, with
	 * true north at 0° and progressing clockwise. (If nearestStormDistance is zero, then
	 * this value will not be defined. The caveats that apply to nearestStormDistance also
	 * apply to this value.)
	 * <p>
	 * Only defined on currently data points
	 */
	@Nullable
	public abstract BigDecimal nearestStormBearing();

	/**
	 * A numerical value representing the average expected intensity (in inches of liquid
	 * water per hour) of precipitation occurring at the given time conditional on
	 * probability (that is, assuming any precipitation occurs at all). A very rough guide
	 * is that a value of 0 in./hr. corresponds to no precipitation, 0.002 in./hr.
	 * corresponds to very light precipitation, 0.017 in./hr. corresponds to light
	 * precipitation, 0.1 in./hr. corresponds to moderate precipitation, and 0.4 in./hr.
	 * corresponds to heavy precipitation.
	 */
	@Nullable
	public abstract BigDecimal precipIntensity();

	/**
	 * Numerical values representing the maximumum expected intensity of precipitation on
	 * the given day in inches of liquid water per hour.
	 * <p>
	 * Only defined on daily data points
	 */
	@Nullable
	public abstract BigDecimal precipIntensityMax();

	/**
	 * The UNIX time at which {@link #precipIntensityMax()} occurs on the given day
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract Long precipIntensityMaxTime();

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the probability of
	 * precipitation occurring at the given time.
	 */
	@Nullable
	public abstract BigDecimal precipProbability();

	/**
	 * The type of precipitation occurring at the given time. If
	 * {@link #precipIntensity()} is zero, then this property will not be defined.)
	 */
	@Nullable
	@JsonDeserialize(using = FioPrecipTypeDeserializer.class)
	public abstract FioPrecipType precipType();

	/**
	 * The amount of snowfall accumulation expected to occur on the given day, in inches.
	 * (If no accumulation is expected, this property will not be defined.)
	 * <p>
	 * Only defined on hourly and daily data points
	 */
	@Nullable
	public abstract BigDecimal precipAccumulation();

	/**
	 * A numerical value representing the temperature at the given time in degrees
	 * Fahrenheit.
	 * <p>
	 * Not defined on daily data points.
	 */
	@Nullable
	public abstract BigDecimal temperature();

	/**
	 * Numerical values representing the minimum temperature on the given day in degrees
	 * Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract BigDecimal temperatureMin();

	/**
	 * The UNIX time at which {@link #temperatureMin()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract Long temperatureMinTime();

	/**
	 * A numerical value representing the maximumum temperature on the given day in
	 * degrees Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract BigDecimal temperatureMax();

	/**
	 * The UNIX time at which {@link #temperatureMax()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract Long temperatureMaxTime();

	/**
	 * A numerical value representing the apparent (or "feels like") temperature at the
	 * given time in degrees Fahrenheit.
	 * <p>
	 * Not defined on daily data points
	 */
	@Nullable
	public abstract BigDecimal apparentTemperature();

	/**
	 * A numerical value representing the minimum apparent temperature on the given day in
	 * degrees Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract BigDecimal apparentTemperatureMin();

	/**
	 * The UNIX time at which {@link #apparentTemperatureMin()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract Long apparentTemperatureMinTime();

	/**
	 * A numerical value representing the maximumum apparent temperature on the given day
	 * in degrees Fahrenheit.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract BigDecimal apparentTemperatureMax();

	/**
	 * The UNIX time at which {@link #apparentTemperatureMax()} occurs.
	 * <p>
	 * Only defined on daily data points.
	 */
	@Nullable
	public abstract Long apparentTemperatureMaxTime();

	/**
	 * A numerical value representing the dew point at the given time in degrees
	 * Fahrenheit.
	 */
	@Nullable
	public abstract BigDecimal dewPoint();

	/**
	 * A numerical value representing the wind speed in miles per hour.
	 */
	@Nullable
	public abstract BigDecimal windSpeed();

	/**
	 * A numerical value representing the direction that the wind is coming from in
	 * degrees, with true north at 0° and progressing clockwise. (If windSpeed is zero,
	 * then this value will not be defined.)
	 */
	@Nullable
	public abstract BigDecimal windBearing();

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the percentage of sky
	 * occluded by clouds.
	 */
	@Nullable
	public abstract BigDecimal cloudCover();

	/**
	 * A numerical value between 0 and 1 (inclusive) representing the relative humidity.
	 */
	@Nullable
	public abstract BigDecimal humidity();

	/**
	 * A numerical value representing the sea-level air pressure in millibars.
	 */
	@Nullable
	public abstract BigDecimal pressure();

	/**
	 * A numerical value representing the average visibility in miles, capped at 10 miles.
	 */
	@Nullable
	public abstract BigDecimal visibility();

	/**
	 * A numerical value representing the columnar density of total atmospheric ozone at
	 * the given time in Dobson units.
	 */
	@Nullable
	public abstract BigDecimal ozone();

	final Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnySetter
	void handleUnknown(String key, Object value) {
		this.additionalProperties.put(key, value);
	}

	public Object getAdditionalProperty(String key) {
		return this.additionalProperties.get(key);
	}

	public Map<String, Object> getAdditionalProperties() {
		return Collections.unmodifiableMap(this.additionalProperties);
	}
}
