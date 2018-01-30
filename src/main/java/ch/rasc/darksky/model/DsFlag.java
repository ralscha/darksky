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

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ch.rasc.darksky.converter.DsUnitDeserializer;

/**
 * The flags object contains various metadata information related to the request.
 */
@Value.Immutable
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(as = ImmutableDsFlag.class)
public interface DsFlag {

	/**
	 * The presence of this property indicates that the Dark Sky data source supports the
	 * given location, but a temporary error (such as a radar station being down for
	 * maintenance) has made the data unavailable.
	 */
	@Nullable
	@JsonProperty("darksky-unavailable")
	Object darkskyUnavailable();

	/**
	 * The presence of this property indicates that data from
	 * <a href="http://api.met.no/">api.met.no</a> was utilized in order to facilitate
	 * this request (as per their license agreement).
	 */
	@Nullable
	@JsonProperty("metno-license")
	String metnoLicense();

	/**
	 * This property contains list of IDs for each
	 * <a href="https://darksky.net/dev/docs/sources">data source</a> utilized in
	 * servicing this request.
	 */
	List<String> sources();

	/**
	 * Indicates the units which were used for the data in this request.
	 */
	@Nullable
	@JsonDeserialize(using = DsUnitDeserializer.class)
	DsUnit units();

}
