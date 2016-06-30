##constgen

[![Build Status](https://api.travis-ci.org/ralscha/forecastio.png)](https://travis-ci.org/ralscha/forecastio)

## Overview

*forecastio* is a Java library for the Forecast.io API.

For more details about the API and for creating an API key go to 
https://developer.forecast.io/ and sign up for a developer account.
The free tier allows you to make 1,000 API requests per day.

## Example

First create an instance of FioClient


then build a FioRequest


call the API


and process the response



## Maven

```
	<dependency>
		<groupId>ch.rasc</groupId>
		<artifactId>forecastio</artifactId>
		<version>1.0.0</version>
	</dependency>
```

This library needs a JSON library to deserialize the JSON reponse. 

Jackson is supported out of the box and it's only necessary to 
add the jackson-databind dependency
```
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.8.0.rc2</version>
		</dependency>
```  

Other JSON libraries are supported by implementing the ch.rasc.forcastio.json.JsonConverter interface and
and providing an instance of this implementation to the FioClient constructor

```
JsonConverter myJsonConvert = new MyJsonConverterImplemenation();
FioClient client = FioClient("...api.key...", myJsonConvert);
```

## Changelog

### 1.0.0 - June 30, 2016
  * Initial release


## License
Code released under [the Apache license](http://www.apache.org/licenses/).


## Links
  * [Forecast.io](http://forecast.io/)


