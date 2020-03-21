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

import java.util.List;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.darksky.converter.DsIconDeserializer;

/**
 * A data block object represents the various weather phenomena occurring over a period of
 * time.
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableDsDataBlock.class)
public interface DsDataBlock {

	/**
	 * A human-readable summary of this data block.
	 */
	@Nullable
	String summary();

	/**
	 * A machine-readable text summary of this data block. (May take on the same values as
	 * {@link DsDataPoint#icon()}.)
	 */
	@JsonDeserialize(using = DsIconDeserializer.class)
	@Nullable
	DsIcon icon();

	/**
	 * A list of {@link DsDataPoint} instances, ordered by time, which together describe
	 * the weather conditions at the requested location over time.
	 */
	List<DsDataPoint> data();

}
