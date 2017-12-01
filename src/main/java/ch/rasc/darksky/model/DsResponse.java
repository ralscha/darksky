/**
 * Copyright 2016-2017 the original author or authors.
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
import java.util.List;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The response to a Dark Sky API call
 */
@Value.Immutable
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableDsResponse.class)
public interface DsResponse {

	/**
	 * The requested latitude.
	 */
	BigDecimal latitude();

	/**
	 * The requested longitude.
	 */
	BigDecimal longitude();

	/**
	 * The IANA timezone name for the requested location. This is used for the
	 * {@link DsDataPoint#summary()} text and for determining when {@link #hourly()} and
	 * {@link #daily()} data block objects begin.
	 */
	String timezone();

	/**
	 * The current timezone offset in hours. (Use of this property will almost certainly
	 * result in Daylight Saving Time bugs. Please use timezone, instead.)
	 */
	int offset();

	/**
	 * An instance of {@link DsDataPoint} containing the current weather conditions at the
	 * requested location.
	 */
	@Nullable
	DsDataPoint currently();

	/**
	 * An instance of {@link DsDataPoint} containing the weather conditions
	 * minute-by-minute for the next hour.
	 */
	@Nullable
	DsDataBlock minutely();

	/**
	 * An instance of {@link DsDataPoint} containing the weather conditions hour-by-hour
	 * for the next two days.
	 */
	@Nullable
	DsDataBlock hourly();

	/**
	 * An instance of {@link DsDataPoint} containing the weather conditions day-by-day for
	 * the next week.
	 */
	@Nullable
	DsDataBlock daily();

	/**
	 * A collection of {@link DsAlert} instances, which, if present, contains any severe
	 * weather alerts, issued by a governmental weather authority, pertinent to the
	 * requested location.
	 */
	List<DsAlert> alerts();

	/**
	 * An instance of {@link DsFlag} containing miscellaneous metadata about the request.
	 */
	@Nullable
	DsFlag flags();

}
