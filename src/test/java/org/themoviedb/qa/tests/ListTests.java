package org.themoviedb.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.themoviedb.authentication.AuthenticateUser;
import org.themoviedb.base.TestBase;
import org.themoviedb.listoperations.ListOperations;
import org.themoviedb.pojo.ItemEntryPojo;
import org.themoviedb.pojo.ItemListPojo;
import org.themoviedb.pojo.ListPojo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ListTests extends TestBase{
	
	private AuthenticateUser athenticateUser;
	private ListOperations listOperations;
	private ListPojo list;
	private ItemEntryPojo itemEntry;
	private ItemListPojo items;
	private List<ItemEntryPojo> listEntry;
	private String listId;
	private String apiKey;
	private String accessToken;
	private Response response;
	private JsonPath jsonResponse;
	private String listName="My Fav list";
	private String listDescription="my initial list";
	private String setISO="en";
	
	@BeforeSuite
	public void getAccessToken() {
		apiKey=getApiKey();
		athenticateUser=new AuthenticateUser();
		athenticateUser.createRequestToken();
		athenticateUser.approveRequestToken();
		accessToken=athenticateUser.createAccessToken();
		listOperations=new ListOperations();
	}
	
	@Test
	public void createListTest() {
		list=new ListPojo();
		list.setName(listName);
		list.setIso_639_1(setISO);
		list.setDescription(listDescription);
		response=listOperations.createList(accessToken, list);
		jsonResponse=new JsonPath(response.asString());
	    listId=jsonResponse.getString("id");	
		response=listOperations.getList(apiKey, listId);
		jsonResponse=new JsonPath(response.asString());
	    String resListName=jsonResponse.getString("name");	
	    Assert.assertEquals(resListName, listName);
		
	}
	
	@Test(dependsOnMethods={"createListTest"})
	public void updateListTest() {
		String updatedName="This list is pretty awesome";
		list.setName(updatedName);
		response=listOperations.updateList(accessToken, list,listId);
		response=listOperations.getList(apiKey, listId);
		JsonPath jsonResponse=new JsonPath(response.asString());
		String resName=jsonResponse.getString("name");
		Assert.assertEquals(resName, updatedName);
		
	}
	
	
	@Test(dependsOnMethods={"createListTest"})
	public void addItemsTest() {
		
		items=new ItemListPojo();
		
		ItemEntryPojo itemEntry1=new ItemEntryPojo();
		String mediaType="movie";
		int mediaId=244786;
		itemEntry1.setMedia_type(mediaType);
		itemEntry1.setMedia_id(mediaId);
	
		ItemEntryPojo itemEntry2=new ItemEntryPojo();
		mediaType="tv";
		mediaId=1396;
		itemEntry2.setMedia_type(mediaType);
		itemEntry2.setMedia_id(mediaId);
		
		listEntry=new ArrayList<ItemEntryPojo>();
		listEntry.add(itemEntry1);
		listEntry.add(itemEntry2);
		items=new ItemListPojo();
		items.setItems(listEntry);
		
		response=given().log().all()
				  .header("content-type","application/json")
				  .header("Authorization", "Bearer " + accessToken)
				  .pathParam("list_id",listId)
				  .body(items)
				  .when()
				  .post("4/list/{list_id}/items").then().assertThat().statusCode(200).log().all()
				  .extract().response();
		response=listOperations.getList(apiKey, listId);
		JsonPath jsonResponse=new JsonPath(response.asString());
		
	}
	@Test(dependsOnMethods={"createListTest", "addItemsTest"})
	public void updateItemsTest() {
		
		itemEntry=new ItemEntryPojo();
		items=new ItemListPojo();
		String mediaType="movie";
		int mediaId=244786;
		String comment="Excellent";
		itemEntry.setMedia_type(mediaType);
		itemEntry.setMedia_id(mediaId);
		itemEntry.setComment(comment);
		listEntry=new ArrayList<ItemEntryPojo>();
		listEntry.add(itemEntry);
		items.setItems(listEntry);
		
		response=listOperations.updateListItems(accessToken, listId, items);
		response=listOperations.getList(apiKey, listId);
		JsonPath jsonResponse=new JsonPath(response.asString());
		System.out.println(jsonResponse);
		String resComment=jsonResponse.getString("comments['" + mediaType + ":" + mediaId + "']");
		Assert.assertEquals(resComment, comment,"List Items Updated Successfully.");
		
	}
	

	@Test(dependsOnMethods={"createListTest", "addItemsTest","updateItemsTest"})
	public void removeListItemsTest() {
		itemEntry=new ItemEntryPojo();
		items=new ItemListPojo();
		String mediaType="tv";
		int mediaId=1396;
		itemEntry.setMedia_type(mediaType);
		itemEntry.setMedia_id(mediaId);
		listEntry=new ArrayList<ItemEntryPojo>();
		listEntry.add(itemEntry);
		items.setItems(listEntry);
		response=listOperations.removeListItems(accessToken, listId, items);
		response=listOperations.getList(apiKey, listId);
		JsonPath jsonResponse=new JsonPath(response.asString());
		int count=jsonResponse.getInt("results.size()");
		boolean testResult=false;
		for(int i=0;i<count;i++) {
			int resMediaId=jsonResponse.getInt("results["+i+"].id");
			
			if(resMediaId==mediaId) 
				testResult=false;
			else
				testResult=true;
		}
	   
		Assert.assertTrue(testResult,"List Item removed successfully.");
	}
	
	@Test(dependsOnMethods={"createListTest", "addItemsTest","updateItemsTest","removeListItemsTest"})
	public void clearListTest() {
		response=listOperations.clearList(accessToken, listId);		
		response=listOperations.getList(apiKey, listId);
	    Assert.assertEquals(jsonResponse.getInt("results.size()"), 0,"List cleared successfully.");
	}
	
	@Test(dependsOnMethods={"createListTest", "addItemsTest","updateItemsTest","removeListItemsTest"})
	public void deleteListTest() {
		response=listOperations.deleteList(accessToken, listId);		
		response=listOperations.getList(apiKey, listId);
		int statusCode=response.getStatusCode();
	   Assert.assertEquals(statusCode, 404,"List Deleted Successfully.");
	}
	
	
	@AfterSuite
	public void deleteAccessToken() {
		// Delete Access Token end point has issue and a ticket is already created for the same.
		//athenticateUser.deleteAccessToken(readAccessToken,accessToken);
	}
	

}
