package myProject;


import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)

public class FriendTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }
    @Test
    public void makeAFriend(){
        String json = "{\n" +
                "    \"senderId\":3,\n" +
                "    \"receiverusrname\":\"IChangedMyName\",\n" +
                "    \"friendshipStatus\":1\n" +
                "}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/friend");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"id\":{\"senderId\":3,\"receiverId\":1},\"receiver\":{\"id\":1,\"username\":\"IChangedMyName\",\"accountType\":1,\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"panda\"},\"friendshipStatus\":2}",returnString);


    }
    @Test
    public void makeACloseFriend(){
        String json = "{\n" +
                "    \"senderId\":1,\n" +
                "    \"receiverusrname\":\"Maxim\",\n" +
                "    \"friendshipStatus\":1\n" +
                "}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/friend");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"id\":{\"senderId\":1,\"receiverId\":3},\"receiver\":{\"id\":3,\"username\":\"Maxim\",\"accountType\":1,\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"Cat\"},\"friendshipStatus\":2}",returnString);


    }


    @Test
    public void findAllFriends() {

        Response response = RestAssured
                .given()
                .log()
                .headers()
                .get("/friends/3");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        //            JSONArray returnArr = new JSONArray(returnString);
//            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
        assertEquals("[{\"id\":{\"senderId\":3,\"receiverId\":1},\"receiver\":{\"id\":1,\"username\":\"IChangedMyName\",\"accountType\":1,\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"panda\"},\"friendshipStatus\":2}]", returnString);
    }

//    @Test
//    public void capitalizeTest() {
//        // Send request and receive response
//        Response response = RestAssured.given().
//                header("Content-Type", "text/plain").
//                header("charset","utf-8").
//                body("hello").
//                when().
//                post("/capitalize");
//
//
//        // Check status code
//        int statusCode = response.getStatusCode();
//        assertEquals(200, statusCode);
//
//        // Check response body for correct response
//        String returnString = response.getBody().asString();
//        try {
//            JSONArray returnArr = new JSONArray(returnString);
//            JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
//            assertEquals("HELLO", returnObj.get("data"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }




}