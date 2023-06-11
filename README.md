# OpenWeatherMap
Hi,
Following are the details for my API automation plan, approach and scope.
Tech: Java, Rest Assured, Testng, Maven
Automation approach:I have automated 2 apis.
Scope:
Automate Current weather data API and verify the responses by using asserts. 
Approach:
1. I obtained the Longitude(lon) and Latitude(lat) values by automating Geocoding API and storing from the response.
2. I used testng dataprovider to get lon and lat data for 2 cities.
3. I used the obtained data from Geocoding API's response to feed into Current weather data API's url.
4. I also sent the optional query parameters to test the api fully.
5. Using an object for JsonPath, i extracted the data from response and using asserts i validated the response.

Inscope tests:
1. The response mandatory fields
2. Testing logic. Example - 1. Max temp cannot be less than min temp. 2. Response is for the requested city
3. Boundary level testing. Example - visibility
4. Negative cases. 
Out of Scope:
Geocoding API testing 