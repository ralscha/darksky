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

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An alert object represents a severe weather warning issued for the requested location
 * by a governmental authority
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableFioAlert.class)
public interface FioAlert {

	/**
	 * A short text summary of the alert.
	 */
	String title();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which the
	 * alert will cease to be valid.
	 */
	long expires();

	/**
	 * An HTTP(S) URI that contains detailed information about the alert.
	 */
	String uri();

	/**
	 * A detailed text description of the alert from the appropriate weather service.
	 */
	String description();

}
