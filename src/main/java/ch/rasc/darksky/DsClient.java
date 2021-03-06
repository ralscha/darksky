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
package ch.rasc.darksky;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import ch.rasc.darksky.json.JacksonJsonConverter;
import ch.rasc.darksky.json.JsonConverter;
import ch.rasc.darksky.model.DsBlock;
import ch.rasc.darksky.model.DsForecastRequest;
import ch.rasc.darksky.model.DsLanguage;
import ch.rasc.darksky.model.DsResponse;
import ch.rasc.darksky.model.DsTimeMachineRequest;
import ch.rasc.darksky.model.DsUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DsClient {
	private final String apiKey;

	private final JsonConverter jsonConverter;

	private final OkHttpClient httpClient;

	private Integer apiCalls;

	private String responseTime;

	public DsClient(String apiKey) {
		this(apiKey, new JacksonJsonConverter(), new OkHttpClient());
	}

	public DsClient(String apiKey, OkHttpClient httpClient) {
		this(apiKey, new JacksonJsonConverter(), httpClient);
	}

	public DsClient(String apiKey, JsonConverter jsonConverter) {
		this(apiKey, jsonConverter, new OkHttpClient());
	}

	public DsClient(String apiKey, JsonConverter jsonConverter, OkHttpClient httpClient) {
		this.apiKey = apiKey;
		this.jsonConverter = jsonConverter;
		this.httpClient = httpClient;
	}

	/**
	 * Sends a Forecast Request to darksky.net.
	 *
	 * @param Request object
	 * @return The darksky response
	 * @throws IOException
	 */
	@Nullable
	public DsResponse sendForecastRequest(DsForecastRequest request) throws IOException {
		HttpUrl.Builder urlBuilder = new HttpUrl.Builder().scheme("https")
				.host("api.darksky.net").addPathSegment("forecast")
				.addPathSegment(this.apiKey)
				.addPathSegment(request.latitude() + "," + request.longitude());

		DsUnit unit = request.unit();
		if (unit != null && unit != DsUnit.US) {
			urlBuilder.addQueryParameter("units", unit.getJsonValue());
		}

		Boolean extendHourly = request.extendHourly();
		if (extendHourly != null && extendHourly.booleanValue()) {
			urlBuilder.addQueryParameter("extend", "hourly");
		}

		DsLanguage language = request.language();
		if (language != null && language != DsLanguage.EN) {
			urlBuilder.addQueryParameter("lang",
					language.name().toLowerCase().replace('_', '-'));
		}

		Set<DsBlock> exclude = EnumSet.noneOf(DsBlock.class);

		Set<DsBlock> include = request.includeBlocks();
		if (!include.isEmpty()) {
			for (DsBlock block : DsBlock.values()) {
				if (!include.contains(block)) {
					exclude.add(block);
				}
			}
		}

		if (!request.excludeBlocks().isEmpty()) {
			exclude.addAll(request.excludeBlocks());
			if (exclude.size() == DsBlock.values().length) {
				// Everything excluded
				return null;
			}
		}

		if (!exclude.isEmpty()) {
			urlBuilder.addQueryParameter("exclude", exclude.stream()
					.map(DsBlock::getJsonValue).collect(Collectors.joining(",")));
		}

		Request getRequest = new Request.Builder().get().url(urlBuilder.build()).build();

		try (Response response = this.httpClient.newCall(getRequest).execute();
				ResponseBody body = response.body()) {

			String apiCallsString = response.header("X-Forecast-API-Calls");
			if (apiCallsString != null && apiCallsString.trim().length() > 0) {
				this.apiCalls = Integer.valueOf(apiCallsString);
			}

			this.responseTime = response.header("X-Response-Time");

			if (body != null) {
				String jsonData = body.string();
				return this.jsonConverter.deserialize(jsonData);
			}
		}

		return null;
	}

	/**
	 * Sends a Time Machine Request to darksky.net
	 *
	 * A Time Machine Request returns the observed (in the past) or forecasted (in the
	 * future) hour-by-hour and daily weather conditions for a particular date. A Time
	 * Machine request is identical in structure to a Forecast Request, except:
	 *
	 * <li>The {@link DsResponse#currently()} data point will refer to the time provided,
	 * rather than the current time.</li>
	 * <li>The {@link DsResponse#minutely()} data block will be omitted, unless you are
	 * requesting a time within an hour of the present.</li>
	 * <li>The {@link DsResponse#hourly()} data block will contain data points starting at
	 * midnight (local time) of the day requested, and continuing until midnight (local
	 * time) of the following day.</li>
	 * <li>The {@link DsResponse#daily()} data block will contain a single data point
	 * referring to the requested date.</li>
	 * <li>The {@link DsResponse#alerts()} data block will be omitted.</li>
	 *
	 * @param Request object
	 * @return The darksky response
	 * @throws IOException
	 */
	@Nullable
	public DsResponse sendTimeMachineRequest(DsTimeMachineRequest request)
			throws IOException {
		HttpUrl.Builder urlBuilder = new HttpUrl.Builder().scheme("https")
				.host("api.darksky.net").addPathSegment("forecast")
				.addPathSegment(this.apiKey).addPathSegment(request.latitude() + ","
						+ request.longitude() + "," + request.time());

		DsUnit unit = request.unit();
		if (unit != null && unit != DsUnit.US) {
			urlBuilder.addQueryParameter("units", unit.getJsonValue());
		}

		DsLanguage language = request.language();
		if (language != null && language != DsLanguage.EN) {
			urlBuilder.addQueryParameter("lang",
					language.name().toLowerCase().replace('_', '-'));
		}

		Set<DsBlock> exclude = EnumSet.noneOf(DsBlock.class);

		Set<DsBlock> include = request.includeBlocks();
		if (!include.isEmpty()) {
			for (DsBlock block : DsBlock.values()) {
				if (!include.contains(block)) {
					exclude.add(block);
				}
			}
		}

		if (!request.excludeBlocks().isEmpty()) {
			exclude.addAll(request.excludeBlocks());
			if (exclude.size() == DsBlock.values().length) {
				// Everything excluded
				return null;
			}
		}

		if (!exclude.isEmpty()) {
			urlBuilder.addQueryParameter("exclude", exclude.stream()
					.map(DsBlock::getJsonValue).collect(Collectors.joining(",")));
		}

		Request getRequest = new Request.Builder().get().url(urlBuilder.build()).build();

		try (Response response = this.httpClient.newCall(getRequest).execute();
				ResponseBody body = response.body()) {

			String apiCallsString = response.header("X-Forecast-API-Calls");
			if (apiCallsString != null && apiCallsString.trim().length() > 0) {
				this.apiCalls = Integer.valueOf(apiCallsString);
			}

			this.responseTime = response.header("X-Response-Time");

			if (body != null) {
				String jsonData = body.string();
				return this.jsonConverter.deserialize(jsonData);
			}
		}

		return null;
	}

	/**
	 * The number of API calls made for today. Value is only set after a request.
	 */
	public Integer apiCalls() {
		return this.apiCalls;
	}

	/**
	 * The server-side response time of the last request. Only set after a request.
	 */
	public String responseTime() {
		return this.responseTime;
	}

}
