# OpenWeatherMap
Hi,
Following are the details for my API automation plan, approach and scope.

Tech: Java v17(Prerequisite), Rest Assured, Testng, Maven

Scope: Automate Current weather data API and verify the responses by using asserts. 

Automation Approach:
1. I obtained the Longitude(lon) and Latitude(lat) values by automating Geocoding API and storing from the response.
2. I used testng dataprovider to get lon and lat data for 3 cities (data set).
3. I used the obtained data from Geocoding API's response to feed into Current weather data API's url request.
4. I also sent the optional query parameters to test the api fully.
5. Using an object for JsonPath, i extracted the data from response and using asserts i validated the response.

InScope tests:
1. The response mandatory fields
2. Testing logic. Example - 1. Max temp cannot be less than min temp. 2. Response is for the requested city
3. Boundary level testing. Example - visibility field has max defined value of 10 kms
4. Check the units with Imperial and Metric
 
Out of Scope for current test:
1. Geocoding API testing 
2. Negative cases but not in this exercise. 
3. Database testing but not in this exercise.
4. Performance/Stress testing but not in this exercise.
5. Weather parameters based on id

How to execute:
Please Run as TestNg test
