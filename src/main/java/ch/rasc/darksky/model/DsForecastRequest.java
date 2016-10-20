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
package ch.rasc.darksky.model;

import java.util.Set;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Value.Immutable
@Value.Style(add = "*", depluralize = true, visibility = ImplementationVisibility.PACKAGE)
public interface DsForecastRequest {

	/**
	 * The latitude of a location (in decimal degrees). Positive is north, negative is
	 * south.
	 */
	String latitude();

	/**
	 * The longitude of a location (in decimal degrees). Positive is east, negative is
	 * west.
	 */
	String longitude();

	/**
	 * When called, returns hour-by-hour data for the next 168 hours, instead of the next
	 * 48.
	 */
	@Nullable
	Boolean extendHourly();

	/**
	 * Return summary properties in the desired language. (Note that units in the summary
	 * will be set according to the units parameter, so be sure to set both parameters
	 * appropriately.)
	 */
	@Nullable
	DsLanguage language();

	/**
	 * Return the API response in units other than the default Imperial units
	 */
	@Nullable
	DsUnit unit();

	/**
	 * Exclude some number of data blocks from the API response. This is useful for
	 * reducing latency and saving cache space.
	 * <p>
	 * By default (when this method and {@link #includeBlocks()} is never called) all
	 * blocks are included.
	 */
	Set<DsBlock> excludeBlocks();

	/**
	 * Include some number of data blocks in the API response. Every block that is not
	 * specifed is automatically excluded.
	 * <p>
	 * By default (when this method and {@link #excludeBlocks()} is never called) all
	 * blocks are included.
	 */
	Set<DsBlock> includeBlocks();

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder extends ImmutableDsForecastRequest.Builder {
		// nothing here
	}
}
