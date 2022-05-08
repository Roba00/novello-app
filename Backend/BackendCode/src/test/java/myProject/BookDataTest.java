package myProject;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)

public class BookDataTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createBookData() {
        String json = "{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":1,\n" +
                "    \"rating\":9.5,\n" +
                "    \"review\":\"This book was kinda good\",\n" +
                "    \"category\": 1,\n" +
                "    \"page\": 3\n" +
                "}";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/bookData");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertNotEquals("", returnString);
    }

    @Test
    public void getBookData() {
        Response response = RestAssured.given().
                when().
                get("/bookData/1/1");
        Response response1 = RestAssured.given().
                when().
                get("/bookData/user/1");
        Response response2 = RestAssured.given().
                when().
                get("/bookData/book/1");
        Response response3 = RestAssured.given().
                when().
                get("/bookData");


        assertNotEquals("", response.getBody().asString());
    }

    @Test
    public void addAllBookData() {
        String json = "[{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":1,\n" +
                "    \"rating\":9.5,\n" +
                "    \"review\":\"This book was kinda good\",\n" +
                "    \"category\": 1,\n" +
                "    \"page\": 3\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":2,\n" +
                "    \"rating\":4.0,\n" +
                "    \"review\":\"I didn't really like it\",\n" +
                "    \"category\": 2,\n" +
                "    \"page\": 45\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":3,\n" +
                "    \"rating\":5.5,\n" +
                "    \"review\":\"THIS WAS THE BEST BOOK EVER!!!!\",\n" +
                "    \"category\": 3,\n" +
                "    \"page\": 56\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":4,\n" +
                "    \"rating\":6.5,\n" +
                "    \"review\":\"The ending was bad\",\n" +
                "    \"category\": 4,\n" +
                "    \"page\": 124\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":7,\n" +
                "    \"rating\":7.5,\n" +
                "    \"review\":\"I wish it was a shorter book\",\n" +
                "    \"category\": 5,\n" +
                "    \"page\": 420\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":6,\n" +
                "    \"rating\":6.0,\n" +
                "    \"review\":\"This was really cool\",\n" +
                "    \"category\": 1,\n" +
                "    \"page\": 69\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":7,\n" +
                "    \"rating\":9.0,\n" +
                "    \"review\":\"Captain America was better\",\n" +
                "    \"category\": 2,\n" +
                "    \"page\": 265\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":8,\n" +
                "    \"rating\":8.0,\n" +
                "    \"review\":\"The second one was better\",\n" +
                "    \"category\": 3,\n" +
                "    \"page\": 200\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":9,\n" +
                "    \"rating\":3.0,\n" +
                "    \"review\":\"Did a kindergartener write this?\",\n" +
                "    \"category\": 4,\n" +
                "    \"page\": 4\n" +
                "},{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":10,\n" +
                "    \"rating\":4.0,\n" +
                "    \"review\":\"BUY IT BUY IT BUY IT\",\n" +
                "    \"category\": 5,\n" +
                "    \"page\": 323\n" +
                "}]";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/addAllBookData");


        String json2 = "{\n" +
                "    \"bookId\":1,\n" +
                "    \"userId\":1\n" +
                "}";

        Response response2 = RestAssured.given().header("Content-Type", "application/json").
                body(json2).
                when().
                get("/bookData");
        assertNotEquals("", response2.getBody().asString());
        String json3 = "{\n" +
                "    \"userId\":1,\n" +
                "    \"bookId\":9,\n" +
                "    \"category\":4\n" +
                "}";
        Response response3 = RestAssured.given().header("Content-Type", "application/json").
                body(json3).
                when().
                put("/bookData");
        assertNotEquals("", response3.getBody().asString());

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }
}