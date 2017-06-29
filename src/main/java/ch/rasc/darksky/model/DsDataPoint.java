/**
 * Copyright 2016-2017 Ralph Schaer <ralphschaer@gmail.com>
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

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.darksky.converter.DsIconDeserializer;
import ch.rasc.darksky.converter.DsPrecipTypeDeserializer;

/**
 * A data point object contains various properties, each representing the average (unless
 * otherwise specified) of a particular weather phenomenon occurring during a period of
 * time: an instant in the case of currently, a minute for minutely, an hour for hourly,
 * and a day for daily.
 */
@Value.Immutable
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableDsDataPoint.class)
public abstract class DsDataPoint {

	/**
	 * The apparent (or "feels like") temperature in degrees Fahrenheit.
	 * <p>
	 * Not on {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract BigDecimal apparentTemperature();

	/**
	 * The maximum value of {@link #apparentTemperature()} during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract BigDecimal apparentTemperatureMax();

	/**
	 * The UNIX time of when {@link #apparentTemperatureMax()} occurs during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract Long apparentTemperatureMaxTime();

	/**
	 * The minimum value of {@link #apparentTemperature()} during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract BigDecimal apparentTemperatureMin();

	/**
	 * The UNIX time of when {@link #apparentTemperatureMin()} occurs during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract Long apparentTemperatureMinTime();

	/**
	 * The percentage of sky occluded by clouds, between 0 and 1, inclusive.
	 */
	@Nullable
	public abstract BigDecimal cloudCover();

	/**
	 * The dew point in degrees Fahrenheit.
	 */
	@Nullable
	public abstract BigDecimal dewPoint();

	/**
	 * The relative humidity, between 0 and 1, inclusive.
	 */
	@Nullable
	public abstract BigDecimal humidity();

	/**
	 * A machine-readable text summary of this data point, suitable for selecting an icon
	 * for display.
	 */
	@Nullable
	@JsonDeserialize(using = DsIconDeserializer.class)
	public abstract DsIcon icon();

	/**
	 * The fractional part of the lunation number during the given day: a value of 0
	 * corresponds to a new moon, 0.25 to a first quarter moon, 0.5 to a full moon, and
	 * 0.75 to a last quarter moon. (The ranges in between these represent waxing
	 * crescent, waxing gibbous, waning gibbous, and waning crescent moons, respectively.)
	 * <p>
	 * Only on {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract BigDecimal moonPhase();

	/**
	 * The approximate direction of the nearest storm in degrees, with true north at 0°
	 * and progressing clockwise. (If {@link #nearestStormDistance()} is zero, then this
	 * value will not be defined.)
	 * <p>
	 * Only on {@link DsResponse#currently()} data points
	 */
	@Nullable
	public abstract BigDecimal nearestStormBearing();

	/**
	 * The approximate distance to the nearest storm in miles. (A storm distance of 0
	 * doesn't necessarily refer to a storm at the requested location, but rather a storm
	 * in the vicinity of that location.)
	 * <p>
	 * Only on {@link DsResponse#currently()} data points
	 */
	@Nullable
	public abstract BigDecimal nearestStormDistance();

	/**
	 * The columnar density of total atmospheric ozone at the given time in Dobson units.
	 */
	@Nullable
	public abstract BigDecimal ozone();

	/**
	 * The amount of snowfall accumulation expected to occur, in inches. (If no snowfall
	 * is expected, this property will not be defined.)
	 * <p>
	 * Only on {@link DsResponse#hourly()} and {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract BigDecimal precipAccumulation();

	/**
	 * The intensity (in inches of liquid water per hour) of precipitation occurring at
	 * the given time. This value is conditional on probability (that is, assuming any
	 * precipitation occurs at all) for minutely data points, and unconditional otherwise.
	 */
	@Nullable
	public abstract BigDecimal precipIntensity();

	/**
	 * The maximum value of {@link #precipIntensity()} during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract BigDecimal precipIntensityMax();

	/**
	 * The UNIX time of when {@link #precipIntensityMax()} occurs during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract Long precipIntensityMaxTime();

	/**
	 * The probability of precipitation occurring, between 0 and 1, inclusive.
	 */
	@Nullable
	public abstract BigDecimal precipProbability();

	/**
	 * The type of precipitation occurring at the given time. (If
	 * {@link #precipIntensity()} is zero, then this property will not be defined.)
	 */
	@Nullable
	@JsonDeserialize(using = DsPrecipTypeDeserializer.class)
	public abstract DsPrecipType precipType();

	/**
	 * The sea-level air pressure in millibars.
	 */
	@Nullable
	public abstract BigDecimal pressure();

	/**
	 * A human-readable text summary of this data point. (This property has millions of
	 * possible values, so don't use it for automated purposes: use the icon property,
	 * instead!)
	 */
	@Nullable
	public abstract String summary();

	/**
	 * The UNIX time of when the sun will rise during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract Long sunriseTime();

	/**
	 * The UNIX time of when the sun will set during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points
	 */
	@Nullable
	public abstract Long sunsetTime();

	/**
	 * The air temperature in degrees Fahrenheit.
	 * <p>
	 * Not on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract BigDecimal temperature();

	/**
	 * The maximum value of temperature during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract BigDecimal temperatureMax();

	/**
	 * The UNIX time of when {@link #temperatureMax()} occurs during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract Long temperatureMaxTime();

	/**
	 * The minimum value of temperature during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract BigDecimal temperatureMin();

	/**
	 * The UNIX time of when {@link #temperatureMin()} occurs during a given day.
	 * <p>
	 * Only on {@link DsResponse#daily()} data points.
	 */
	@Nullable
	public abstract Long temperatureMinTime();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which this
	 * data point begins. {@link DsResponse#minutely()} data point are always aligned to
	 * the top of the minute, {@link DsResponse#hourly()} data point objects to the top of
	 * the hour, and {@link DsResponse#daily()} data point objects to midnight of the day,
	 * all according to the local time zone.
	 */
	public abstract long time();

	/**
	 * The average visibility in miles, capped at 10 miles.
	 */
	@Nullable
	public abstract BigDecimal visibility();

	/**
	 * The direction that the wind is coming from in degrees, with true north at 0° and
	 * progressing clockwise. (If {@link #windSpeed()} is zero, then this value will not
	 * be defined.)
	 */
	@Nullable
	public abstract BigDecimal windBearing();

	/**
	 * The wind speed in miles per hour.
	 */
	@Nullable
	public abstract BigDecimal windSpeed();

}
