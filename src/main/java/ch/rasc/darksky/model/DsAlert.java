/**
 * Copyright 2016-2018 the original author or authors.
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

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.darksky.converter.DsAlertSeverityDeserializer;

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
	 * A detailed description of the alert.
	 */
	String description();

	/**
	 * The UNIX time at which the alert will expire.
	 */
	long expires();

	/**
	 * A list of strings representing the names of the regions covered by this weather
	 * alert.
	 */
	List<String> regions();

	/**
	 * The severity of the weather alert. Will take one of the following values:
	 * {@link DsAlertSeverity#ADVISORY} (an individual should be aware of potentially
	 * severe weather), {@link DsAlertSeverity#WATCH} (an individual should prepare for
	 * potentially severe weather), or {@link DsAlertSeverity#WARNING} (an individual
	 * should take immediate action to protect themselves and others from potentially
	 * severe weather).
	 */
	@JsonDeserialize(using = DsAlertSeverityDeserializer.class)
	DsAlertSeverity severity();

	/**
	 * The UNIX time at which the alert was issued.
	 */
	long time();

	/**
	 * A brief description of the alert.
	 */
	String title();

	/**
	 * An HTTP(S) URI that one may refer to for detailed information about the alert.
	 */
	String uri();

}
