package org.themoviedb.listoperations;

import static io.restassured.RestAssured.given;

import org.themoviedb.pojo.ItemListPojo;
import org.themoviedb.pojo.ListPojo;

import io.restassured.response.Response;

public class ListOperations {
	
	public Response createList(String accessToken,ListPojo list) {
		return given()
		  .header("content-type","application/json")
		  .header("Authorization", "Bearer " + accessToken)
		  .body(list)
		  .when()
		  .post("4/list").then().assertThat().statusCode(201)
		  .extract().response();
	}
	
	public Response updateList(String accessToken,ListPojo list,String listId) {
		return given().log().all()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .body(list)
				  .when()
				  .put("4/list/{list_id}").then().assertThat().statusCode(201).log().all()
				  .extract().response();
	}
	public Response clearList(String accessToken,String listId) {
		return  given()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .when()
				  .get("4/list/{list_id}/clear").then().assertThat().statusCode(200)
				  .extract().response();
	}
	
	public Response updateListItems(String accessToken,String listId,ItemListPojo items) {
		return given()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .body(items)
				  .when()
				  .put("4/list/{list_id}/items").then().assertThat().statusCode(200)
				  .extract().response();
	}
	
	public Response removeListItems(String accessToken,String listId,ItemListPojo items) {
		return given()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .body(items)
				  .when()
				  .delete("4/list/{list_id}/items").then().assertThat().statusCode(200)
				  .extract().response();
	}

	public Response deleteList(String accessToken,String listId) {
		
		return given()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .when()
				  .delete("4/list/{list_id}").then().assertThat().statusCode(200)
				  .extract().response();
		
	}
	
	public Response getList(String apiKey,String listId) {
		return given()
				  .header("content-type","application/json")
				  .queryParam("api_key", apiKey)
				  .pathParam("list_id",listId)
				  .when()
				  .get("4/list/{list_id}").then()
				  .extract().response();
	}
}
