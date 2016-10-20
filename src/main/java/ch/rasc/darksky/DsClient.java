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
package ch.rasc.darksky;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import ch.rasc.darksky.json.JacksonJsonConverter;
import ch.rasc.darksky.json.JsonConverter;
import ch.rasc.darksky.model.DsBlock;
import ch.rasc.darksky.model.DsLanguage;
import ch.rasc.darksky.model.DsRequest;
import ch.rasc.darksky.model.DsResponse;
import ch.rasc.darksky.model.DsUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
	 * Sends a forecast call to darksky.net.
	 *
	 * @param Request object
	 * @return The darksky response
	 * @throws IOException
	 */
	public DsResponse sendForecastRequest(DsRequest request) throws IOException {
		HttpUrl.Builder urlBuilder = new HttpUrl.Builder().scheme("https")
				.host("api.darksky.net").addPathSegment("forecast")
				.addPathSegment(this.apiKey)
				.addPathSegment(request.latitude() + "," + request.longitude());

		if (request.unit() != null && request.unit() != DsUnit.US) {
			urlBuilder.addQueryParameter("units", request.unit().getJsonValue());
		}

		if (request.extendHourly() != null && request.extendHourly().booleanValue()) {
			urlBuilder.addQueryParameter("extend", "hourly");
		}

		if (request.language() != null && request.language() != DsLanguage.EN) {
			urlBuilder.addQueryParameter("lang",
					request.language().name().toLowerCase().replace('_', '-'));
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

		HttpUrl url = urlBuilder.build();
		System.out.println(url);
		Request getRequest = new Request.Builder().get().url(url).build();

		try (Response response = this.httpClient.newCall(getRequest).execute()) {

			String apiCallsString = response.header("X-Forecast-API-Calls");
			if (apiCallsString != null && apiCallsString.trim().length() > 0) {
				this.apiCalls = Integer.valueOf(apiCallsString);
			}

			this.responseTime = response.header("X-Response-Time");

			String jsonData = response.body().string();
			return this.jsonConverter.deserialize(jsonData);
		}
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
