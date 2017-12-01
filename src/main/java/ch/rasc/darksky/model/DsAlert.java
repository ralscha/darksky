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

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * An alert object represents a severe weather warning issued for the requested location
 * by a governmental authority. (See the
 * <a href="https://darksky.net/dev/docs/sources">data sources page</a> for a list of
 * sources)
 */
@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableDsAlert.class)
public interface DsAlert {

	/**
	 * A brief description of the alert.
	 */
	String title();

	/**
	 * The UNIX time (that is, seconds since midnight GMT on 1 Jan 1970) at which the
	 * alert will expire.
	 */
	long expires();

	/**
	 * An HTTP(S) URI that one may refer to for detailed information about the alert.
	 */
	String uri();

	/**
	 * A detailed description of the alert.
	 */
	String description();

}
