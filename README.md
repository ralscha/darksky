# Update: support for the Dark Sky API will be ending on March 31st, 2023
# https://darksky.net/dev


## darksky

![Test Status](https://github.com/ralscha/darksky/workflows/test/badge.svg)

## Overview

*darksky* is a Java client library for the [Dark Sky API](https://darksky.net/dev/) API (former forecast.io).

For more details about the API and for creating an API key go to 
https://darksky.net/dev/ and sign up for a developer account.
The free tier allows an application to send 1,000 API requests per day.

## Example: Forecast Request

Create an instance of DsClient

```
DsClient client = new DsClient("...darksky.net.api.key...");
```

build a DsForecastRequest

```
DsForecastRequest request = DsForecastRequest.builder()
        .latitude("46.93011019")
		.longitude("7.5635394")
		.excludeBlock(DsBlock.ALERTS, DsBlock.MINUTELY, DsBlock.HOURLY)
		.unit(DsUnit.SI)
		.build();
```

call the API

```
DsResponse response = client.sendForecastRequest(request);
```

and process the response

```
for (DsDataPoint dataPoint : response.daily().data()) {
    ZoneId zoneId = ZoneId.of(response.timezone());
    Instant instant = Instant.ofEpochSecond(dataPoint.time());
    ZonedDateTime time = ZonedDateTime.ofInstant(instant, zoneId);

    System.out.print(time);
    System.out.print(": ");
    System.out.println(dataPoint.summary());
}
```


## Example: Time Machine Request

Create an instance of DsClient

```
DsClient client = new DsClient("...darksky.net.api.key...");
```

build a DsTimeMachineRequest.  

```
DsTimeMachineRequest request = DsTimeMachineRequest.builder()
        .latitude("46.93011019")
		.longitude("7.5635394")				
		.unit(DsUnit.SI)
		.time(ZonedDateTime.now().minusDays(10).toEpochSecond())
		.build();
```

call the API

```
DsResponse response = client.sendTimeMachineRequest(request);
```

and process the response

```
for (DsDataPoint dataPoint : response.hourly().data()) {			
    ZoneId zoneId = ZoneId.of(response.timezone());
    Instant instant = Instant.ofEpochSecond(dataPoint.time());
    ZonedDateTime time = ZonedDateTime.ofInstant(instant, zoneId);

    System.out.print(time);
    System.out.print(": ");
    System.out.println(dataPoint);            
}
```

## Maven

```
	<dependency>
		<groupId>ch.rasc</groupId>
		<artifactId>darksky</artifactId>
		<version>2.0.1</version>
	</dependency>
```

## Changelog

### 2.0.1 - March 21, 2020
  * Add missing properties to DsAlert and DsDataPoint according to the [Dark Sky API documentation](https://darksky.net/dev/docs).

      - DsAlert.regions
      - DsAlert.severity
      - DsAlert.time
      - DsDataPoint.apparentTemperatureHigh
      - DsDataPoint.apparentTemperatureHighTime
      - DsDataPoint.apparentTemperatureLow
      - DsDataPoint.apparentTemperatureLowTime
      - DsDataPoint.precipIntensityError
      - DsDataPoint.temperatureHigh
      - DsDataPoint.temperatureHighTime
      - DsDataPoint.temperatureLow
      - DsDataPoint.temperatureLowTime
      - DsDataPoint.uvIndex
      - DsDataPoint.uvIndexTime
      - DsDataPoint.windGust
      - DsDataPoint.windGustTime


### 2.0.0 - October 20, 2016
  * Change package to ch.rasc.darksky (previously: ch.rasc.forecastio)
  * Change class name prefix from Fio* to Ds* (i.e. FioClient -> DsClient)
  * Fix include/exclude handling
  * Add time machine requests
  

### 1.0.1 - September 30, 2016
  * Change endpoint from api.forecast.io to api.darksky.net

  
### 1.0.0 - July 5, 2016
  * Initial release


## License
Code released under [the Apache license](http://www.apache.org/licenses/).


[![Powered by Dark Sky](https://darksky.net/dev/img/attribution/poweredby-oneline.png)](https://darksky.net/poweredby/)