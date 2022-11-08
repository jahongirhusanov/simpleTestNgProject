package sku;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class SKU_Test {

    private static String URL = "https://1ryu4whyek.execute-api.us-west-2.amazonaws.com/dev/skus";
    private static String sku_id = "johny_test_2";

    private static String requestBody = "{\n" +
            "  \"sku\": \"johny_test_2\",\n" +
            "  \"description\": \"Jelly donut Test By Johny H\",\n" +
            "  \"price\": \"2.44\" \n}";

    @Test(priority = 1)
    public void PostSku() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post(URL)
                .then()
                .extract().response();
        Assert.assertEquals(200, response.statusCode());
    }

    @Test(priority = 2)
    public void GetAllSku() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .extract().response();

        System.out.println(response.getStatusCode());
        int statusCode = response.getStatusCode();
        //Asserting status code
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 3)
    public void GetSkuById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/" + sku_id)
                .then()
                .extract().response();
        Assert.assertEquals(200, response.statusCode());

        JsonPath jsonpath = response.jsonPath();
        String price = jsonpath.get("Item.price");
        String description = jsonpath.get("Item.description");
        System.out.println("price for skuId: " + sku_id + " is: " + price);
        System.out.println("description for skuId: " + sku_id + " is: " + description);

        //ToDo: Need to add log4j
        System.out.println("sku id: "+sku_id+" is successfully retrieved");
    }

    @Test(priority = 4)
    public void DeleteSkuById() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(URL + "/" + sku_id)
                .then()
                .extract().response();
        Assert.assertEquals(200, response.statusCode());

        //ToDo: Need to add log4j
        System.out.println("sku id: "+sku_id+" is deleted");
    }
}
