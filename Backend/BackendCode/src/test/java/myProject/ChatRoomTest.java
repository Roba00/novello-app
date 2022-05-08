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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)

public class ChatRoomTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void createChatRoom() {
        String json = "[\n" +
                "    {\n" +
                "        \"name\" : \"Single Private Chat\",\n" +
                "        \"type\" : 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\" : 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\" : 2\n" +
                "    }\n" +
                "]";
        Response response = RestAssured.given().header("Content-Type", "application/json").
                body(json).
                when().
                post("/chatRoom");


        String json2 = "{\n" +
                "    \"userId\" : 1,\n" +
                "    \"bookId\" : 9\n" +
                "}";
        Response response2 = RestAssured.given().header("Content-Type", "application/json").
                body(json2).
                when().
                delete("/bookData");
        assertEquals("", response2.getBody().asString());

        assertEquals(response.getBody().asString(), "");
    }
}