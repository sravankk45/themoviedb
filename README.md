## Automation test regression suite for checking list functionality in themoviedb.org api.

### The below functionalities covered in testcases.

* Create list
* Update list
* Add Items to list
* Update Items in list
* Remove Items from list
* Clear list
* Delete list

### Tools and Technologies used

* Java
* RestAssured
* TestNG
* Maven

### Configuration/setup to be done to run the project

* The below details are configured in config.properies file(available in themoviedb\src\main\java\org\themoviedb\config\config.properties)
	* baseURI
	* approveRequestTokenBaseURI
	* apiKey
	* readAccessToken
	* userName
	* password

**Update the values of apiKey,readAccessToken,userName and password with account specific details so that the project reads these values and use them during the authentication process**

_Known issue: The AccessToken end point is not working at the moment and there is one bug created already for the same_