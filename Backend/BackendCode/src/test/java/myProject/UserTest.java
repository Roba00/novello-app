package myProject;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import myProject.user.User;
import myProject.user.UserInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)

public class UserTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void makeAllUsers() {
        String json = "[\n" +
                "    \n" +
                "{\n" +
                "    \"name\":\"Goben\",\n" +
                "    \"accountType\":1,\n" +
                "    \"username\":\"Idontknowpickone\",\n" +
                "    \"password\":\"8493\",\n" +
                "    \"securityQuestion\":\"Favorite animal\",\n" +
                "    \"securityAnswer\":\"penguin\",\n" +
                "    \"email\":\"thisisanemail@gmail.com\",\n" +
                "    \"age\":21\n" +
                "},\n" +
                "\n" +
                "{\n" +
                "    \"name\":\"scottg\",\n" +
                "    \"accountType\":1,\n" +
                "    \"username\":\"Scottie\",\n" +
                "    \"password\":\"6969\",\n" +
                "    \"securityQuestion\":\"Favorite animal\",\n" +
                "    \"securityAnswer\":\"dog\",\n" +
                "    \"email\":\"myemail@gmail.com\",\n" +
                "    \"age\":22\n" +
                "},\n" +
                "{\n" +
                "    \"name\":\"Maxim\",\n" +
                "    \"accountType\":1,\n" +
                "    \"username\":\"Maxim\",\n" +
                "    \"password\":\"5760\",\n" +
                "    \"securityQuestion\":\"Favorite animal\",\n" +
                "    \"securityAnswer\":\"Cat\",\n" +
                "    \"email\":\"thisisanemail@gmail.com\",\n" +
                "    \"age\":20\n" +
                "}\n" +
                "\n" +
                "]";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/addAllUsers");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("", returnString);


    }

    @Test
    public void makeUser() {
        String json = "{\n" +
                "    \"username\":\"Phantom\",\n" +
                "    \"password\":\"7893\"\n" +
                "}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/user");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"id\":4,\"username\":\"Phantom\",\"accountType\":null,\"password\":\"7893\",\"securityQuestion\":null,\"securityAnswer\":null}", returnString);


    }

    @Test
    public void getAUser() {
        Response response = RestAssured
                .given()
                .log()
                .headers()
                .get("/user/2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"id\":2,\"username\":\"Scottie\",\"accountType\":1,\"password\":\"6969\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"dog\"}", returnString);

    }

    @Test
    public void updateUser() {
        String json = "{\"id\":1,\"accountType\":1,\"username\":\"IChangedMyName\",\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"panda\"}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                put("/user/1");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"id\":1,\"username\":\"IChangedMyName\",\"accountType\":1,\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"panda\"}", returnString);
    }

    @Test
    public void deleteUser() {
        Response response = RestAssured
                .given()
                .log()
                .headers()
                .delete("/user/4");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("deleted 4", returnString);
    }

    @Test
    public void login() {
        String json = "{\n" +
                "    \"username\":\"Scottie\",\n" +
                "    \"password\":\"6969\"\n" +
                "}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("{\"userId\":2}", returnString);


    }

    @Test
    public void getAllUsers(){
        Response response = RestAssured
                .given()
                .log()
                .headers()
                .get("/users");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();


        assertEquals("[{\"id\":1,\"username\":\"Idontknowpickone\",\"accountType\":1,\"password\":\"8493\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"penguin\"},{\"id\":2,\"username\":\"Scottie\",\"accountType\":1,\"password\":\"6969\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"dog\"},{\"id\":3,\"username\":\"Maxim\",\"accountType\":1,\"password\":\"5760\",\"securityQuestion\":\"Favorite animal\",\"securityAnswer\":\"Cat\"}]",returnString);

    }
    @Autowired
    UserInterface UIDB;
    @Test
    public void getAccountType(){
        User u = new User();
        u = UIDB.findById(3).orElseThrow(NoSuchElementException::new);
        String username = u.getUsername();
        int accountType = u.getAccountType();
        String password = u.getPassword();
        String securityQuestion = u.getSecurityQuestion();
        String securityAnswer = u.getSecurityAnswer();





        assertEquals("Maxim",username);
        assertEquals(1,accountType);
        assertEquals("5760",password);
        assertEquals("Favorite animal",securityQuestion);
        assertEquals("Cat",securityAnswer);


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