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

import java.util.Set;

import javax.annotation.Nullable;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Value.Immutable
@Value.Style(add = "*", depluralize = true, visibility = ImplementationVisibility.PACKAGE)
public interface DsTimeMachineRequest {

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
	 * UNIX time (that is, seconds since midnight GMT on 1 Jan 1970).
	 */
	long time();

	/**
	 * Return summary properties in the desired language. (Note that units in the summary
	 * will be set according to the {@link #unit()} parameter, so be sure to set both
	 * parameters appropriately.)
	 * <p>
	 * Default: {@link DsLanguage#EN}
	 */
	@Nullable
	DsLanguage language();

	/**
	 * Return weather conditions in the requested units.
	 * <p>
	 * Default: {@link DsUnit#US}
	 */
	@Nullable
	DsUnit unit();

	/**
	 * Exclude some number of data blocks from the API response. This is useful for
	 * reducing latency and saving cache space.
	 * <p>
	 * By default (when this method and {@link #includeBlocks()} are never called) all
	 * blocks are included.
	 */
	Set<DsBlock> excludeBlocks();

	/**
	 * Include some number of data blocks in the API response. Blocks that are not
	 * specifed are automatically excluded.
	 * <p>
	 * By default (when this method and {@link #excludeBlocks()} are never called) all
	 * blocks are included.
	 */
	Set<DsBlock> includeBlocks();

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder extends ImmutableDsTimeMachineRequest.Builder {
		// nothing here
	}
}
