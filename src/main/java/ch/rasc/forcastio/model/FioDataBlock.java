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

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.forcastio.converter.FioIconDeserializer;

/**
 * A data block object represents the various weather phenomena occurring over a period of
 * time.
 *
 * Ideally, the minutely data block will contain data for the next hour, the hourly data
 * block for the next two days, and the daily data block for the next week; however, if we
 * are lacking data for a given time period, the data point sequence may contain gaps or
 * terminate early. Furthermore, if no data points for a time period are known, then the
 * data block will be omitted from the response in its entirety.
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableFioDataBlock.class)
public interface FioDataBlock {

	/**
	 * A human-readable text summary of this data block.
	 */
	String summary();

	/**
	 * A machine-readable text summary of this data block
	 */
	@JsonDeserialize(using = FioIconDeserializer.class)
	FioIcon icon();

	/**
	 * A collection of {@link FioDataPoint} instances, ordered by time, which together
	 * describe the weather conditions at the requested location over time.
	 */
	List<FioDataPoint> data();

}
