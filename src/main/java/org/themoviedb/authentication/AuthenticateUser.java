package org.themoviedb.authentication;

import org.themoviedb.base.TestBase;
import org.themoviedb.pages.ApproveRequestTokenPage;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthenticateUser extends TestBase{

	private ApproveRequestTokenPage approveRequestTokenPage;
	private String baseUri;
	private String approveRequestTokenBaseUri;
	private String readAccessToken;
	private String userName;
	private String password;
	private String requestToken;
	private Response response;
	private JsonPath jsonResponse;
	
	public AuthenticateUser() {
		baseUri=getBaseUri();
		approveRequestTokenBaseUri=getApproveRequestTokenBaseUri();
		userName=getUserName();
		password=getPassword();
		readAccessToken=getReadAccessToken();
		RestAssured.baseURI=baseUri;
	}
	
	public void createRequestToken() {
		response=given()
				  .header("content-type","application/json")
				  .header("Authorization","Bearer "+ readAccessToken)
				  .when()
				  .post("4/auth/request_token").then().assertThat().statusCode(200)
				  .extract().response();
		jsonResponse=new JsonPath(response.asString());
		requestToken=jsonResponse.getString("request_token");

	}
	
	public void approveRequestToken() {
		approveRequestTokenPage=new ApproveRequestTokenPage();		
		approveRequestTokenPage.launchApproveTokenRequestPage(approveRequestTokenBaseUri+"/auth/access?request_token="+requestToken);
		approveRequestTokenPage.clickLoginButton();
		approveRequestTokenPage.enterUserName(userName);
		approveRequestTokenPage.enterPassword(password);
		approveRequestTokenPage.clickSubmitButton();
		approveRequestTokenPage.clickApproveButton();
		approveRequestTokenPage.closeBrowser();
		
	}
	
	public String createAccessToken() {
		response=given()
				  .header("content-type","application/json")
				  .header("Authorization","Bearer "+ readAccessToken)
				  .body("{\r\n" + 
				  		"  \"request_token\": \""+ requestToken + "\"\r\n" + 
				  		"}")
				  .when()
				  .post("4/auth/access_token").then().assertThat().statusCode(200)
				  .extract().response();
		jsonResponse=new JsonPath(response.asString());
		return jsonResponse.getString("access_token");

	}
	
	public Response deleteAccessToken(String readAccessToken,String accessToken) {
		return response=given()
						  .header("content-type","application/json")
						  .header("Authorization","Bearer "+readAccessToken)
						  .body("{\r\n" + 
						  		"  \"access_token\": \""+ accessToken +"\"\r\n" + 
						  		"}")
						  .when()
						  .delete("4/auth/access_token").then().statusCode(200)
						  .extract()
						  .response();
		

	}

}
