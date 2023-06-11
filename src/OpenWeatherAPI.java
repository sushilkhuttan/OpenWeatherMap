import static org.testng.Assert.assertEquals;

import org.apache.http.client.methods.RequestBuilder;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class OpenWeatherAPI {
	String appid = "8f77bcfb6e181d97c6380d1ea53d9ff1";
	RequestSpecification request = new RequestSpecBuilder().setBaseUri("http://api.openweathermap.org")
			.addQueryParam("appid", appid).build();

	String lat;
	String lon;
	String city;
	@Test(dataProvider="dataprovider")
	public void getLongLat(String cityName, String units)
	{
		//Geocoding API: Getting lat and long based on city
		String response = given().spec(request).log().all().queryParam("q",cityName).queryParam("limit","1")
		.when().get("geo/1.0/direct")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp = new JsonPath(response);
		lat = jp.getString("lat[0]");
		lon = jp.getString("lon[0]");
		city = jp.getString("name");

		//Current weather data API: Getting weather information from lat and log
		String WeatherResponse = 
				given().log().all()
				.queryParam("appid", "8f77bcfb6e181d97c6380d1ea53d9ff1").spec(request)
				.queryParam("lat",lat) //mandatory
				.queryParam("lon", lon) //mandatory
				.queryParam("units", units) //optional
				.queryParam("lang", "en") //optional
				.when().get("data/2.5/weather") 
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
			
				JsonPath jwp = new JsonPath(WeatherResponse);
				String cityNameResponse = jwp.getString("name");
				Double minTemp = jwp.getDouble("main.temp_min");
				Double MaxTemp = jwp.getDouble("main.temp_max");
				int timezone = jwp.getInt("timezone");
				String id = jwp.getString("weather.id");
				int visibility = jwp.getInt("visibility");
				int cod = jwp.getInt("cod");

				
		Assert.assertTrue(cityNameResponse.contains(cityName)); //Verify the city name is correct
		Assert.assertTrue(MaxTemp >= minTemp); //Verify the Max temperature is higher than min Temp
		Assert.assertTrue(cod == 200); //Verify the Max temperature is higher than min Temp
		Assert.assertTrue(visibility <= 10000); //Boundary level test. Visibility max is 10 kms
		if(cityName == "London") {
			Assert.assertEquals(timezone, 3600); //Verify timezone is constant 3600
			Assert.assertNotNull(id);
		}else if(cityName == "Chicago") {
			Assert.assertEquals(timezone, -18000); //Verify timezone is constant -18000
		}else if(cityName == "Rome") {
			Assert.assertEquals(timezone, 7200); //Verify timezone is constant 7200		
		}	
	}
	
	@DataProvider(name = "dataprovider")
	public Object[][] data() {
		return new Object[][] {{"London","metric"},{"Chicago","imperial"},{"Rome","imperial"}};
	}
	
}
