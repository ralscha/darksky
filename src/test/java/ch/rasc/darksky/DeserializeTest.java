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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ch.rasc.darksky.json.JacksonJsonConverter;
import ch.rasc.darksky.model.DsDataBlock;
import ch.rasc.darksky.model.DsDataPoint;
import ch.rasc.darksky.model.DsFlag;
import ch.rasc.darksky.model.DsIcon;
import ch.rasc.darksky.model.DsPrecipType;
import ch.rasc.darksky.model.DsResponse;
import ch.rasc.darksky.model.DsUnit;

public class DeserializeTest {

	@Test
	public void serializeForecast() throws IOException, URISyntaxException {
		JacksonJsonConverter converter = new JacksonJsonConverter();

		String json = new String(Files.readAllBytes(Paths.get(
				this.getClass().getClassLoader().getResource("forecast.json").toURI())),
				StandardCharsets.UTF_8);

		DsResponse response = converter.deserialize(json);

		assertThat(response.latitude()).isEqualByComparingTo(new BigDecimal("37.8267"));
		assertThat(response.longitude())
				.isEqualByComparingTo(new BigDecimal("-122.4233"));
		assertThat(response.timezone()).isEqualTo("America/Los_Angeles");
		assertThat(response.offset()).isEqualTo(-7);

		DsFlag flags = response.flags();
		assertThat(flags).isNotNull();
		assertThat(flags.darkskyUnavailable()).isNull();
		assertThat(flags.sources()).containsExactly("nwspa", "cmc", "gfs", "hrrr", "icon",
				"isd", "madis", "nam", "sref", "darksky", "nearest-precip");
		assertThat(flags.nearestStation()).isEqualByComparingTo(new BigDecimal("2.24"));
		assertThat(flags.units()).isEqualTo(DsUnit.US);

		DsDataPoint currently = response.currently();
		assertThat(currently.time()).isEqualTo(1584723707);
		assertThat(currently.summary()).isEqualTo("Clear");
		assertThat(currently.icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(currently.nearestStormDistance()).isEqualTo(new BigDecimal("1"));
		assertThat(currently.nearestStormBearing()).isEqualTo(new BigDecimal("105"));
		assertThat(currently.precipIntensity()).isEqualTo(new BigDecimal("0"));
		assertThat(currently.precipProbability()).isEqualTo(new BigDecimal("0"));
		assertThat(currently.temperature()).isEqualTo(new BigDecimal("52.35"));
		assertThat(currently.apparentTemperature()).isEqualTo(new BigDecimal("52.38"));
		assertThat(currently.dewPoint()).isEqualTo(new BigDecimal("42.31"));
		assertThat(currently.humidity()).isEqualTo(new BigDecimal("0.69"));
		assertThat(currently.pressure()).isEqualTo(new BigDecimal("1023.4"));
		assertThat(currently.windSpeed()).isEqualTo(new BigDecimal("5.11"));
		assertThat(currently.windGust()).isEqualTo(new BigDecimal("8.26"));
		assertThat(currently.windBearing()).isEqualTo(new BigDecimal("93"));
		assertThat(currently.cloudCover()).isEqualTo(new BigDecimal("0.09"));
		assertThat(currently.uvIndex()).isEqualTo(2);
		assertThat(currently.visibility()).isEqualTo(new BigDecimal("10"));
		assertThat(currently.ozone()).isEqualTo(new BigDecimal("354.1"));

		DsDataBlock minutely = response.minutely();
		assertThat(minutely).isNotNull();
		assertThat(minutely.summary()).isEqualTo("Clear for the hour.");
		assertThat(minutely.icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(minutely.data()).hasSize(61);
		assertThat(minutely.data().get(0).time()).isEqualTo(1584723660);
		assertThat(minutely.data().get(0).precipIntensity()).isEqualTo(BigDecimal.ZERO);
		assertThat(minutely.data().get(0).precipProbability()).isEqualTo(BigDecimal.ZERO);
		assertThat(minutely.data().get(60).time()).isEqualTo(1584727260);
		assertThat(minutely.data().get(60).precipIntensity())
				.isEqualTo(new BigDecimal("0.003"));
		assertThat(minutely.data().get(60).precipIntensityError())
				.isEqualTo(new BigDecimal("0.002"));
		assertThat(minutely.data().get(60).precipProbability())
				.isEqualTo(new BigDecimal("0.02"));

		DsDataBlock hourly = response.hourly();
		assertThat(hourly).isNotNull();
		assertThat(hourly.summary()).isEqualTo("Clear throughout the day.");
		assertThat(hourly.icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(hourly.data()).hasSize(49);
		assertThat(hourly.data().get(0).time()).isEqualTo(1584723600);
		assertThat(hourly.data().get(0).summary()).isEqualTo("Clear");
		assertThat(hourly.data().get(0).icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(hourly.data().get(0).precipIntensity()).isEqualTo(BigDecimal.ZERO);
		assertThat(hourly.data().get(0).precipProbability()).isEqualTo(BigDecimal.ZERO);
		assertThat(hourly.data().get(0).temperature()).isEqualTo(new BigDecimal("52.29"));
		assertThat(hourly.data().get(0).apparentTemperature())
				.isEqualTo(new BigDecimal("52.29"));
		assertThat(hourly.data().get(0).dewPoint()).isEqualTo(new BigDecimal("42.3"));
		assertThat(hourly.data().get(0).humidity()).isEqualTo(new BigDecimal("0.69"));
		assertThat(hourly.data().get(0).pressure()).isEqualTo(new BigDecimal("1023.4"));
		assertThat(hourly.data().get(0).windSpeed()).isEqualTo(new BigDecimal("5.11"));
		assertThat(hourly.data().get(0).windGust()).isEqualTo(new BigDecimal("8.23"));
		assertThat(hourly.data().get(0).windBearing()).isEqualTo(new BigDecimal("94"));
		assertThat(hourly.data().get(0).cloudCover()).isEqualTo(new BigDecimal("0.09"));
		assertThat(hourly.data().get(0).uvIndex()).isEqualTo(2);
		assertThat(hourly.data().get(0).visibility()).isEqualTo(new BigDecimal("10"));
		assertThat(hourly.data().get(0).ozone()).isEqualTo(new BigDecimal("353.9"));

		assertThat(hourly.data().get(48).time()).isEqualTo(1584896400);
		assertThat(hourly.data().get(48).summary()).isEqualTo("Mostly Cloudy");
		assertThat(hourly.data().get(48).icon()).isEqualTo(DsIcon.PARTLY_CLOUDY_DAY);
		assertThat(hourly.data().get(48).precipIntensity())
				.isEqualTo(new BigDecimal("0.0002"));
		assertThat(hourly.data().get(48).precipProbability())
				.isEqualTo(new BigDecimal("0.02"));
		assertThat(hourly.data().get(48).precipType()).isEqualTo(DsPrecipType.RAIN);
		assertThat(hourly.data().get(48).temperature())
				.isEqualTo(new BigDecimal("51.99"));
		assertThat(hourly.data().get(48).apparentTemperature())
				.isEqualTo(new BigDecimal("51.99"));
		assertThat(hourly.data().get(48).dewPoint()).isEqualTo(new BigDecimal("40.95"));
		assertThat(hourly.data().get(48).humidity()).isEqualTo(new BigDecimal("0.66"));
		assertThat(hourly.data().get(48).pressure()).isEqualTo(new BigDecimal("1019.2"));
		assertThat(hourly.data().get(48).windSpeed()).isEqualTo(new BigDecimal("6.57"));
		assertThat(hourly.data().get(48).windGust()).isEqualTo(new BigDecimal("9.91"));
		assertThat(hourly.data().get(48).windBearing()).isEqualTo(new BigDecimal("53"));
		assertThat(hourly.data().get(48).cloudCover()).isEqualTo(new BigDecimal("0.79"));
		assertThat(hourly.data().get(48).uvIndex()).isEqualTo(2);
		assertThat(hourly.data().get(48).visibility()).isEqualTo(new BigDecimal("10"));
		assertThat(hourly.data().get(48).ozone()).isEqualTo(new BigDecimal("387.8"));

		DsDataBlock daily = response.daily();
		assertThat(daily).isNotNull();
		assertThat(daily.summary())
				.isEqualTo("Possible drizzle on Tuesday and Wednesday.");
		assertThat(daily.icon()).isEqualTo(DsIcon.RAIN);
		assertThat(daily.data()).hasSize(8);
		assertThat(daily.data().get(0).time()).isEqualTo(1584687600);
		assertThat(daily.data().get(0).summary()).isEqualTo("Clear throughout the day.");
		assertThat(daily.data().get(0).icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(daily.data().get(0).sunriseTime()).isEqualTo(1584713580);
		assertThat(daily.data().get(0).sunsetTime()).isEqualTo(1584757380);
		assertThat(daily.data().get(0).moonPhase()).isEqualTo(new BigDecimal("0.9"));
		assertThat(daily.data().get(0).precipIntensity())
				.isEqualTo(new BigDecimal("0.0007"));
		assertThat(daily.data().get(0).precipIntensityMax())
				.isEqualTo(new BigDecimal("0.003"));
		assertThat(daily.data().get(0).precipIntensityMaxTime()).isEqualTo(1584749280);
		assertThat(daily.data().get(0).precipProbability())
				.isEqualTo(new BigDecimal("0.16"));
		assertThat(daily.data().get(0).precipType()).isEqualTo(DsPrecipType.RAIN);
		assertThat(daily.data().get(0).temperatureHigh())
				.isEqualTo(new BigDecimal("62.07"));
		assertThat(daily.data().get(0).temperatureHighTime()).isEqualTo(1584742680);
		assertThat(daily.data().get(0).temperatureLow())
				.isEqualTo(new BigDecimal("46.95"));
		assertThat(daily.data().get(0).temperatureLowTime()).isEqualTo(1584801480);
		assertThat(daily.data().get(0).apparentTemperatureHigh())
				.isEqualTo(new BigDecimal("61.57"));
		assertThat(daily.data().get(0).apparentTemperatureHighTime())
				.isEqualTo(1584742680);
		assertThat(daily.data().get(0).apparentTemperatureLow())
				.isEqualTo(new BigDecimal("45.44"));
		assertThat(daily.data().get(0).apparentTemperatureLowTime())
				.isEqualTo(1584802020);
		assertThat(daily.data().get(0).dewPoint()).isEqualTo(new BigDecimal("41.28"));
		assertThat(daily.data().get(0).humidity()).isEqualTo(new BigDecimal("0.64"));
		assertThat(daily.data().get(0).pressure()).isEqualTo(new BigDecimal("1022"));
		assertThat(daily.data().get(0).windSpeed()).isEqualTo(new BigDecimal("4.55"));
		assertThat(daily.data().get(0).windGust()).isEqualTo(new BigDecimal("9.35"));
		assertThat(daily.data().get(0).windGustTime()).isEqualTo(1584729120);
		assertThat(daily.data().get(0).windBearing()).isEqualTo(new BigDecimal("27"));
		assertThat(daily.data().get(0).cloudCover()).isEqualTo(new BigDecimal("0.1"));
		assertThat(daily.data().get(0).uvIndex()).isEqualTo(5);
		assertThat(daily.data().get(0).uvIndexTime()).isEqualTo(1584735300);
		assertThat(daily.data().get(0).visibility()).isEqualTo(new BigDecimal("10"));
		assertThat(daily.data().get(0).ozone()).isEqualTo(new BigDecimal("354.8"));
		assertThat(daily.data().get(0).temperatureMin())
				.isEqualTo(new BigDecimal("47.44"));
		assertThat(daily.data().get(0).temperatureMinTime()).isEqualTo(1584709860);
		assertThat(daily.data().get(0).temperatureMax())
				.isEqualTo(new BigDecimal("62.07"));
		assertThat(daily.data().get(0).temperatureMaxTime()).isEqualTo(1584742680);
		assertThat(daily.data().get(0).apparentTemperatureMin())
				.isEqualTo(new BigDecimal("46.22"));
		assertThat(daily.data().get(0).apparentTemperatureMinTime())
				.isEqualTo(1584715920);
		assertThat(daily.data().get(0).apparentTemperatureMax())
				.isEqualTo(new BigDecimal("61.57"));
		assertThat(daily.data().get(0).apparentTemperatureMaxTime())
				.isEqualTo(1584742680);

		assertThat(daily.data().get(7).time()).isEqualTo(1585292400);
		assertThat(daily.data().get(7).summary())
				.isEqualTo("Overcast throughout the day.");
		assertThat(daily.data().get(7).icon()).isEqualTo(DsIcon.CLOUDY);
		assertThat(daily.data().get(7).sunriseTime()).isEqualTo(1585317720);
		assertThat(daily.data().get(7).sunsetTime()).isEqualTo(1585362600);
		assertThat(daily.data().get(7).moonPhase()).isEqualTo(new BigDecimal("0.11"));
		assertThat(daily.data().get(7).precipIntensity())
				.isEqualTo(new BigDecimal("0.0012"));
		assertThat(daily.data().get(7).precipIntensityMax())
				.isEqualTo(new BigDecimal("0.0052"));
		assertThat(daily.data().get(7).precipIntensityMaxTime()).isEqualTo(1585363140);
		assertThat(daily.data().get(7).precipProbability())
				.isEqualTo(new BigDecimal("0.11"));
		assertThat(daily.data().get(7).precipType()).isEqualTo(DsPrecipType.RAIN);
		assertThat(daily.data().get(7).temperatureHigh())
				.isEqualTo(new BigDecimal("56.59"));
		assertThat(daily.data().get(7).temperatureHighTime()).isEqualTo(1585343100);
		assertThat(daily.data().get(7).temperatureLow())
				.isEqualTo(new BigDecimal("49.24"));
		assertThat(daily.data().get(7).temperatureLowTime()).isEqualTo(1585405560);
		assertThat(daily.data().get(7).apparentTemperatureHigh())
				.isEqualTo(new BigDecimal("56.09"));
		assertThat(daily.data().get(7).apparentTemperatureHighTime())
				.isEqualTo(1585343100);
		assertThat(daily.data().get(7).apparentTemperatureLow())
				.isEqualTo(new BigDecimal("49.73"));
		assertThat(daily.data().get(7).apparentTemperatureLowTime())
				.isEqualTo(1585405560);
		assertThat(daily.data().get(7).dewPoint()).isEqualTo(new BigDecimal("38.72"));
		assertThat(daily.data().get(7).humidity()).isEqualTo(new BigDecimal("0.65"));
		assertThat(daily.data().get(7).pressure()).isEqualTo(new BigDecimal("1022.5"));
		assertThat(daily.data().get(7).windSpeed()).isEqualTo(new BigDecimal("6.29"));
		assertThat(daily.data().get(7).windGust()).isEqualTo(new BigDecimal("13.75"));
		assertThat(daily.data().get(7).windGustTime()).isEqualTo(1585292400);
		assertThat(daily.data().get(7).windBearing()).isEqualTo(new BigDecimal("243"));
		assertThat(daily.data().get(7).cloudCover()).isEqualTo(new BigDecimal("0.8"));
		assertThat(daily.data().get(7).uvIndex()).isEqualTo(4);
		assertThat(daily.data().get(7).uvIndexTime()).isEqualTo(1585341360);
		assertThat(daily.data().get(7).visibility()).isEqualTo(new BigDecimal("10"));
		assertThat(daily.data().get(7).ozone()).isEqualTo(new BigDecimal("327.8"));
		assertThat(daily.data().get(7).temperatureMin())
				.isEqualTo(new BigDecimal("46.64"));
		assertThat(daily.data().get(7).temperatureMinTime()).isEqualTo(1585316700);
		assertThat(daily.data().get(7).temperatureMax())
				.isEqualTo(new BigDecimal("56.59"));
		assertThat(daily.data().get(7).temperatureMaxTime()).isEqualTo(1585343100);
		assertThat(daily.data().get(7).apparentTemperatureMin())
				.isEqualTo(new BigDecimal("44.02"));
		assertThat(daily.data().get(7).apparentTemperatureMinTime())
				.isEqualTo(1585293360);
		assertThat(daily.data().get(7).apparentTemperatureMax())
				.isEqualTo(new BigDecimal("56.09"));
		assertThat(daily.data().get(7).apparentTemperatureMaxTime())
				.isEqualTo(1585343100);
	}

	@Test
	public void serializeForecastHourly() throws IOException, URISyntaxException {
		JacksonJsonConverter converter = new JacksonJsonConverter();

		String json = new String(
				Files.readAllBytes(Paths.get(this.getClass().getClassLoader()
						.getResource("forecast-hourly.json").toURI())),
				StandardCharsets.UTF_8);

		DsResponse response = converter.deserialize(json);

		assertThat(response.latitude()).isEqualByComparingTo(new BigDecimal("37.8267"));
		assertThat(response.longitude())
				.isEqualByComparingTo(new BigDecimal("-122.4233"));
		assertThat(response.timezone()).isEqualTo("America/Los_Angeles");
		assertThat(response.offset()).isEqualTo(-7);

		DsFlag flags = response.flags();
		assertThat(flags).isNotNull();
		assertThat(flags.darkskyUnavailable()).isNull();
		assertThat(flags.sources()).containsExactly("nwspa", "cmc", "gfs", "hrrr", "icon",
				"isd", "madis", "nam", "sref", "darksky", "nearest-precip");
		assertThat(flags.nearestStation()).isEqualByComparingTo(new BigDecimal("2.24"));
		assertThat(flags.units()).isEqualTo(DsUnit.US);

		DsDataPoint currently = response.currently();
		assertThat(currently).isNotNull();

		DsDataBlock minutely = response.minutely();
		assertThat(minutely).isNotNull();
		assertThat(minutely.summary()).isEqualTo("Clear for the hour.");
		assertThat(minutely.icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(minutely.data()).hasSize(61);

		DsDataBlock hourly = response.hourly();
		assertThat(hourly).isNotNull();
		assertThat(hourly.summary()).isEqualTo("Clear throughout the day.");
		assertThat(hourly.icon()).isEqualTo(DsIcon.CLEAR_DAY);
		assertThat(hourly.data()).hasSize(169);

		DsDataBlock daily = response.daily();
		assertThat(daily).isNotNull();
		assertThat(daily.summary())
				.isEqualTo("Possible drizzle on Tuesday and Wednesday.");
		assertThat(daily.icon()).isEqualTo(DsIcon.RAIN);
		assertThat(daily.data()).hasSize(8);
	}
}
