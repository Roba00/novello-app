package myProject;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)

public class BookTest {
    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void serverUp() {
        Response response = RestAssured.given().when().get("/");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("HELLO", returnString);
    }

    @Test
    public void addBook() {
        String json = "{\"bookID\":11,\"isbn\":\"0000000000001\",\"title\":\"Pride and Prejudice\",\"author\":\"Jane Austen\",\"publicationYear\":1813,\"overallRating\":5.0,\"msrp\":9.99,\"genre\":\"Literature\",\"description\":\"Pride and Prejudice is an 1813 novel of manners written by Jane Austen. The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the book who learns about the repercussions of hasty judgments and comes to appreciate the difference between superficial goodness and actual goodness.\",\"readingUrl\":\"https://www.gutenberg.org/files/1342/1342-h/1342-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg\"}";

        Response response = RestAssured.given().header("Content-Type", "application/json").body(json).when().post("/book");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals(json, returnString);
    }

    @Test
    public void addAllBook() {
        String json = "[{\"isbn\":\"0000000000001\",\"title\":\"Pride and Prejudice\",\"author\":\"Jane Austen\",\"publicationYear\":1813,\"overallRating\":5.0,\"msrp\":9.99,\"genre\":\"Literature\",\"description\":\"Pride and Prejudice is an 1813 novel of manners written by Jane Austen. The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the book who learns about the repercussions of hasty judgments and comes to appreciate the difference between superficial goodness and actual goodness.\",\"readingUrl\":\"https://www.gutenberg.org/files/1342/1342-h/1342-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg\"},{\"isbn\":\"0000000000002\",\"title\":\"A Doll's House\",\"author\":\"Henrick Ibsen\",\"publicationYear\":1879,\"overallRating\":4.5,\"msrp\":9.99,\"genre\":\"Drama\",\"description\":\"A Doll's House is a three-act play written by Norwegian playwright Henrik Ibsen. It premiered at the Royal Theatre in Copenhagen, Denmark, on 21 December 1879, having been published earlier that month.[1] The play is set in a Norwegian town circa 1879.\",\"readingUrl\":\"https://www.gutenberg.org/files/2542/2542-h/2542-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/2542/pg2542.cover.medium.jpg\"},{\"isbn\":\"0000000000003\",\"title\":\"Moby-Dick; or, The Whale\",\"author\":\"Herman Melville\",\"publicationYear\":1851,\"overallRating\":4.0,\"msrp\":9.99,\"genre\":\"Adventure\",\"description\":\"Moby-Dick; or, The Whale is an 1851 novel by American writer Herman Melville. The book is the sailor Ishmael's narrative of the obsessive quest of Ahab, captain of the whaling ship Pequod, for revenge on Moby Dick, the giant white sperm whale that on the ship's previous voyage bit off Ahab's leg at the knee. A contribution to the literature of the American Renaissance, Moby-Dick was published to mixed reviews, was a commercial failure, and was out of print at the time of the author's death in 1891. Its reputation as a \\\"Great American Novel\\\" was established only in the 20th century, after the centennial of its author's birth. William Faulkner said he wished he had written the book himself,[1] and D. H. Lawrence called it \\\"one of the strangest and most wonderful books in the world\\\" and \\\"the greatest book of the sea ever written\\\". Its opening sentence, \\\"Call me Ishmael\\\", is among world literature's most famous.\",\"readingUrl\":\"https://www.gutenberg.org/files/2701/2701-h/2701-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/2701/pg2701.cover.medium.jpg\"},{\"isbn\":\"0000000000004\",\"title\":\"Heart of Darkness\",\"author\":\"Joseph Conrad\",\"publicationYear\":1899,\"overallRating\":3.5,\"msrp\":9.99,\"genre\":\"Historical Fiction\",\"description\":\"Heart of Darkness (1899) is a novella by Polish-English novelist Joseph Conrad. It tells the story of Charles Marlow, a sailor who takes on an assignment from a Belgian trading company as a ferry-boat captain in the African interior. The novel is widely regarded as a critique of European colonial rule in Africa, whilst also examining the themes of power dynamics and morality. Although Conrad does not name the river where the narrative takes place, at the time of writing the Congo Free State, the location of the large and economically important Congo River, was a private colony of Belgium's King Leopold II. Marlow is given a text by Kurtz, an ivory trader working on a trading station far up the river, who has \\\"gone native\\\" and is the object of Marlow's expedition.\",\"readingUrl\":\"https://www.gutenberg.org/files/219/219-h/219-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/219/pg219.cover.medium.jpg\"},{\"isbn\":\"0000000000005\",\"title\":\"Great Expectations\",\"author\":\"Charles Dickens\",\"publicationYear\":1861,\"overallRating\":3.0,\"msrp\":9.99,\"genre\":\"Coming of Age\",\"description\":\"Great Expectations is the thirteenth novel by Charles Dickens and his penultimate completed novel. It depicts the education of an orphan nicknamed Pip (the book is a bildungsroman, a coming-of-age story). It is Dickens's second novel, after David Copperfield, to be fully narrated in the first person.[N 1] The novel was first published as a serial in Dickens's weekly periodical All the Year Round, from 1 December 1860 to August 1861.[1] In October 1861, Chapman and Hall published the novel in three volumes.\",\"readingUrl\":\"https://www.gutenberg.org/files/1400/1400-h/1400-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/1400/pg1400.cover.medium.jpg\"},{\"isbn\":\"0000000000006\",\"title\":\"Adventures of Huckleberry Finn\",\"author\":\"Mark Twain\",\"publicationYear\":1884,\"overallRating\":4.5,\"msrp\":9.99,\"genre\":\"Adventure\",\"description\":\"Commonly named among the Great American Novels, the work is among the first in major American literature to be written throughout in vernacular English, characterized by local color regionalism. It is told in the first person by Huckleberry \\\"Huck\\\" Finn, the narrator of two other Twain novels (Tom Sawyer Abroad and Tom Sawyer, Detective) and a friend of Tom Sawyer. It is a direct sequel to The Adventures of Tom Sawyer.\",\"readingUrl\":\"https://www.gutenberg.org/files/76/76-h/76-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/76/pg76.cover.medium.jpg\"},{\"isbn\":\"0000000000007\",\"title\":\"Crime and Punishment\",\"author\":\"Fyodor Dostoyevsky\",\"publicationYear\":1866,\"overallRating\":2.5,\"msrp\":9.99,\"genre\":\"Psychological\",\"description\":\"Crime and Punishment follows the mental anguish and moral dilemmas of Rodion Raskolnikov, an impoverished ex-student in Saint Petersburg who plans to kill an unscrupulous pawnbroker, an old woman who stores money and valuable objects in her flat. He theorises that with the money he could liberate himself from poverty and go on to perform great deeds, and seeks to convince himself that certain crimes are justifiable if they are committed in order to remove obstacles to the higher goals of 'extraordinary' men. Once the deed is done, however, he finds himself racked with confusion, paranoia, and disgust. His theoretical justifications lose all their power as he struggles with guilt and horror and confronts both the internal and external consequences of his deed.\",\"readingUrl\":\"https://www.gutenberg.org/files/2554/2554-h/2554-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/2554/pg2554.cover.medium.jpg\"},{\"isbn\":\"0000000000008\",\"title\":\"Ulysses\",\"author\":\"James Joyce\",\"publicationYear\":1922,\"overallRating\":2.0,\"msrp\":9.99,\"genre\":\"Literature\",\"description\":\"Ulysses chronicles the appointments and encounters of the itinerant Leopold Bloom in Dublin in the course of an ordinary day, 16 June 1904.[4][5] Ulysses is the Latinised name of Odysseus, the hero of Homer's epic poem the Odyssey, and the novel establishes a series of parallels between the poem and the novel, with structural correspondences between the characters and experiences of Bloom and Odysseus, Molly Bloom and Penelope, and Stephen Dedalus and Telemachus, in addition to events and themes of the early 20th-century context of modernism, Dublin, and Ireland's relationship to Britain. The novel is highly allusive and also imitates the styles of different periods of English literature.\",\"readingUrl\":\"https://www.gutenberg.org/files/4300/4300-h/4300-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/4300/pg4300.cover.medium.jpg\"},{\"isbn\":\"0000000000009\",\"title\":\"War and Peace\",\"author\":\"Leo Tolstoy\",\"publicationYear\":1869,\"overallRating\":1.5,\"msrp\":9.99,\"genre\":\"Historical Fiction\",\"description\":\"The novel chronicles the French invasion of Russia and the impact of the Napoleonic era on Tsarist society through the stories of five Russian aristocratic families. Portions of an earlier version, titled The Year 1805,[4] were serialized in The Russian Messenger from 1865 to 1867 before the novel was published in its entirety in 1869.[5]\",\"readingUrl\":\"https://www.gutenberg.org/files/2600/2600-h/2600-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/2600/pg2600.cover.medium.jpg\"},{\"isbn\":\"0000000000010\",\"title\":\"Treasure Island\",\"author\":\"Robert Louis Stevenson\",\"publicationYear\":1883,\"overallRating\":1.0,\"msrp\":9.99,\"genre\":\"Coming of Age\",\"description\":\"Treasure Island (originally titled The Sea Cook: A Story for Boys[1]) is an adventure novel by Scottish author Robert Louis Stevenson, telling a story of \\\"buccaneers and buried gold\\\". It is considered a coming-of-age story and is noted for its atmosphere, characters, and action.            \",\"readingUrl\":\"https://www.gutenberg.org/files/120/120-h/120-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/120/pg120.cover.medium.jpg\"}]";
        Response response = RestAssured.given().header("Content-Type", "application/json").body(json).when().post("/addAllBooks");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertNotEquals("", returnString);

        Response response2 = RestAssured.given().when().get("/book");

        assertNotEquals("", response2.getBody().asString());

        String json5 = "{\"isbn\":\"0000000000001\",\"title\":\"Pride and Prejudice\",\"author\":\"stee\",\"publicationYear\":1813,\"overallRating\":5.0,\"msrp\":9.99,\"genre\":\"Literature\",\"description\":\"Pride and Prejudice is an 1813 novel of manners written by Jane Austen. The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the book who learns about the repercussions of hasty judgments and comes to appreciate the difference between superficial goodness and actual goodness.\",\"readingUrl\":\"https://www.gutenberg.org/files/1342/1342-h/1342-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg\"}";
        Response response5 = RestAssured.given().header("Content-Type", "application/json").body(json).when().put("/book/2");

        int statusCode5 = response5.getStatusCode();
        assertEquals(200, statusCode);

        assertEquals(response5.getBody().asString(), response5.getBody().asString());
    }

    @Test
    public void getBook() {
        Response response = RestAssured.given().when().get("/book/1");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String correct = "{\"bookID\":1,\"isbn\":\"0000000000001\",\"title\":\"Pride and Prejudice\",\"author\":\"Jane Austen\",\"publicationYear\":1813,\"overallRating\":5.0,\"msrp\":9.99,\"genre\":\"Literature\",\"description\":\"Pride and Prejudice is an 1813 novel of manners written by Jane Austen. The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the book who learns about the repercussions of hasty judgments and comes to appreciate the difference between superficial goodness and actual goodness.\",\"readingUrl\":\"https://www.gutenberg.org/files/1342/1342-h/1342-h.htm\",\"imageUrl\":\"https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg\"}";
        assertEquals(response.getBody().asString(), correct);

    }

    @Test
    public void getAllBooks() {
        Response response = RestAssured.given().when().get("/book");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        assertNotEquals("", response.getBody().asString());
    }

//    @Test
//    public void z() {
//
//    }

    @Test
    public void deleteBook() {
        Response response = RestAssured.given().when().delete("/book/5");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        assertEquals(response.getBody().asString(), "deleted 5");
    }

    @Test
    public void duckScreen() {

        Response response = RestAssured.given().when().get("/duck");
        assertEquals("https://i.pinimg.com/originals/62/37/d4/6237d416dec1d84c8afbb9dce847e2bc.jpg", response.getBody().asString());
    }
}