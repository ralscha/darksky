##forecastio

[![Build Status](https://api.travis-ci.org/ralscha/forecastio.png)](https://travis-ci.org/ralscha/forecastio)

## Overview

*forecastio* is a Java library for the Forecast.io API.

For more details about the API and for creating an API key go to 
https://developer.forecast.io/ and sign up for a developer account.
The free tier allows an application to send 1,000 API requests per day.

## Example

Create an instance of FioClient

```
FioClient client = new FioClient("...forecast.io.api.key...");
```

build a FioRequest

```
FioRequest request = FioRequest.builder()
                .latitude("46.93011019")
				.longitude("7.5635394")
				.excludeBlock(FioBlock.ALERTS, FioBlock.MINUTELY, FioBlock.HOURLY)
				.unit(FioUnit.SI)
				.build();
```

call the API

```
FioResponse response = client.forecastCall(request);
```

and process the response

```
for (FioDataPoint dataPoint : response.daily().data()) {
	ZoneId zoneId = ZoneId.of(response.timezone());
	Instant instant = Instant.ofEpochSecond(dataPoint.time());
	LocalDateTime time = LocalDateTime.ofInstant(instant, zoneId);

	System.out.print(time);
	System.out.print(": ");
	System.out.println(dataPoint.summary());			
}
```

## Maven

```
	<dependency>
		<groupId>ch.rasc</groupId>
		<artifactId>forecastio</artifactId>
		<version>1.0.0</version>
	</dependency>
```

## Changelog

### 1.0.0 - July 5, 2016
  * Initial release


## License
Code released under [the Apache license](http://www.apache.org/licenses/).


## Links
  * [Forecast.io](http://forecast.io/)


