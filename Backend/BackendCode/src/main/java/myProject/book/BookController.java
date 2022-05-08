package myProject.book;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Api(value = "BookController")
@RestController
public class BookController {

    @Autowired
    BookInterface db;

    @GetMapping("/")
    public String welcome() {
        return "HELLO";
    }


    @ApiOperation(value = "Add a book to the system", response = Iterable.class)
    @PostMapping("/book")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    Book addBook(@ApiParam(value = "Json of Book to be added", example = "{foo: whatever, bar: whatever2}") @RequestBody Book b) {
        db.save(b);
        return b;
    }

    @ApiOperation(value = "Add all books to the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping("/addAllBooks")
    Book[] addAllBooks(@ApiParam(value = "Json array of Books to add to the global library") @RequestBody Book[] b) {
        db.saveAll(Arrays.asList(b));
        return b;
    }

    @ApiOperation(value = "Get a book by id from the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/book/{id}")
    Optional<Book> getBook(@ApiParam(value = "Id of the Book to be found") @PathVariable Integer id) {
        return db.findById(id);
    }

    @ApiOperation(value = "Get all books from the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/book")
    List<Book> getAllBooks() {
        return db.findAll();
    }

    @ApiOperation(value = "Update a book by id in the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping("/book/{id}")
    Book updateBook(@ApiParam(value = "Json of the updated fields of a book") @RequestBody Book b, @ApiParam(value = "Id of the Book to update") @PathVariable Integer id) {
        Book old_b = db.findById(id).orElseThrow(RuntimeException::new);
        if (b.isbn != null) old_b.setIsbn(b.isbn);
        if (b.title != null) old_b.setTitle(b.title);
        if (b.author != null) old_b.setAuthor(b.author);
        if (b.publicationYear != null) old_b.setPublicationYear(b.publicationYear);
        if (b.overallRating != null) old_b.setOverallRating(b.overallRating);
        if (b.msrp != null) old_b.setMsrp(b.msrp);
        if (b.genre != null) old_b.setGenre(b.genre);
        db.save(old_b);
        return old_b;
    }


    @ApiOperation(value = "Delete a book by id in the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping("/book/{id}")
    String deleteBook(@ApiParam(value = "Id of the Book to be deleted") @PathVariable Integer id) {
        db.deleteById(id);
        return "deleted " + id;
    }

    @ApiOperation(value = "Get the duck screen", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/duck")
    String duckScreen() {
        return "https://i.pinimg.com/originals/62/37/d4/6237d416dec1d84c8afbb9dce847e2bc.jpg";
    }

}
