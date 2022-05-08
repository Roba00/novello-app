package myProject.book;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import myProject.user.User;
import myProject.user.UserInterface;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;


@RestController
public class BookDataController {

    @Autowired
    BookDataInterface db;
    @Autowired
    BookInterface bdb;
    @Autowired
    UserInterface pdb;

    @ApiOperation(value = "Add book data to the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping("/bookData")
    BookData createBookData(@ApiParam(value = "Json of Book data to add to the library") @RequestBody JSONObject jsonObject) {

        BookDataKey bookDataKey = new BookDataKey();
        bookDataKey.setBookId((Integer) jsonObject.getAsNumber("bookId"));
        bookDataKey.setUserId((Integer) jsonObject.getAsNumber("userId"));

        BookData bookData = new BookData();


        bookData.setId(bookDataKey);
        User p = pdb.findById(bookDataKey.userId).orElseThrow(NoSuchElementException::new);
        Book b = bdb.findById(bookDataKey.bookId).orElseThrow(NoSuchElementException::new);

        bookData.setBook(b);
        bookData.setUser(p);

        if (jsonObject.getAsNumber("rating") != null) {
            bookData.setRating((Double) jsonObject.getAsNumber("rating"));
        }
        if (jsonObject.getAsString("review") != null) {
            bookData.setReview(jsonObject.getAsString("review"));
        }
        if (jsonObject.getAsNumber("category") != null) {
            bookData.setCategory((Integer) jsonObject.getAsNumber("category"));
        }
        if (jsonObject.getAsNumber("page") != null) {
            bookData.setPage((Integer) jsonObject.getAsNumber("page"));
        }

        db.save(bookData);
        return bookData;
    }

    @ApiOperation(value = "Add all book data to the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping("/addAllBookData")
    void addAllBookData(@ApiParam(value = "Json array of Book data to add to the system") @RequestBody JSONObject[] jsonObjectIn) {

        JSONObject jsonObject;
        for (JSONObject object : jsonObjectIn) {
            jsonObject = object;
            BookDataKey bookDataKey = new BookDataKey();
            bookDataKey.setBookId((Integer) jsonObject.getAsNumber("bookId"));
            bookDataKey.setUserId((Integer) jsonObject.getAsNumber("userId"));

            BookData bookData = new BookData();


            bookData.setId(bookDataKey);
            User p = pdb.findById(bookDataKey.userId).orElseThrow(NoSuchElementException::new);
            Book b = bdb.findById(bookDataKey.bookId).orElseThrow(NoSuchElementException::new);

            bookData.setBook(b);
            bookData.setUser(p);

            if (jsonObject.getAsNumber("rating") != null) {
                bookData.setRating((Double) jsonObject.getAsNumber("rating"));
            }
            if (jsonObject.getAsString("review") != null) {
                bookData.setReview(jsonObject.getAsString("review"));
            }
            if (jsonObject.getAsNumber("category") != null) {
                bookData.setCategory((Integer) jsonObject.getAsNumber("category"));
            }
            if (jsonObject.getAsNumber("page") != null) {
                bookData.setPage((Integer) jsonObject.getAsNumber("page"));
            }

            db.save(bookData);
        }
    }

    @ApiOperation(value = "Change book data in the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping("/bookData")
    BookData creatBookData(@ApiParam(value = "Json of Book data to add") @RequestBody JSONObject jsonObject) {

        BookDataKey bookDataKey = new BookDataKey();
        bookDataKey.setBookId((Integer) jsonObject.getAsNumber("bookId"));
        bookDataKey.setUserId((Integer) jsonObject.getAsNumber("userId"));

        BookData bookData = db.findById(bookDataKey).orElseThrow(NoSuchElementException::new);

        if (jsonObject.getAsNumber("rating") != null) {
            bookData.setRating((Double) jsonObject.getAsNumber("rating"));
        }
        if (jsonObject.getAsString("review") != null) {
            bookData.setReview(jsonObject.getAsString("review"));
        }
        if (jsonObject.getAsNumber("category") != null) {
            bookData.setCategory((Integer) jsonObject.getAsNumber("category"));
        }
        if (jsonObject.getAsNumber("page") != null) {
            bookData.setPage((Integer) jsonObject.getAsNumber("page"));
        }
        db.save(bookData);
        return bookData;
    }

    @ApiOperation(value = "Get book data from the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/bookData")
    BookData findAReview(@ApiParam(value = "Json of book and user id to search with") @RequestBody JSONObject jsonObject) {
        BookDataKey bookDataKey = new BookDataKey();
        bookDataKey.setBookId((Integer) jsonObject.getAsNumber("bookId"));
        bookDataKey.setUserId((Integer) jsonObject.getAsNumber("userId"));
        return db.findById(bookDataKey).orElseThrow(NoSuchElementException::new);
    }

    @ApiOperation(value = "Delete book data from the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping("/bookData")
    void delete(@ApiParam(value = "Json of book and user id to search with") @RequestBody JSONObject jsonObject) {
        BookDataKey bookDataKey = new BookDataKey();
        bookDataKey.setBookId((Integer) jsonObject.getAsNumber("bookId"));
        bookDataKey.setUserId((Integer) jsonObject.getAsNumber("userId"));

        db.deleteById(bookDataKey);
    }

    @ApiOperation(value = "Get reviews from book id from the system", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/bookData/book/{bid}")
    Set<BookData> getRatings(@ApiParam(value = "book id to search by") @PathVariable Integer bid) {
        Book b = bdb.findById(bid).orElseThrow(NoSuchElementException::new);
        return b.getBookData();
    }

    @ApiOperation(value = "Get all book data from user and the category", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/bookData/{pid}/{category}")
    Set<BookData> getAllBooksFromUserCategory(@ApiParam(value = "Person id to search with") @PathVariable Integer pid, @ApiParam(value = "Category id to search with") @PathVariable Integer category) {
        //return by category
        User p = pdb.findById(pid).orElseThrow(NoSuchElementException::new);
        Set<BookData> library = p.getBookData();
        Set<BookData> librarySet = new HashSet<>();
        Iterator<BookData> libraryIterator = library.iterator();
        BookData l;
        int c;
        while (libraryIterator.hasNext()) {
            l = libraryIterator.next();
            c = l.getCategory();
            if (c == category) {
                librarySet.add(l);
            }
        }
        return librarySet;
    }

    @ApiOperation(value = "Get all book data from the user id", response = Iterable.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved list"), @ApiResponse(code = 401, message = "You are not authorized to view the resource"), @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"), @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping("/bookData/user/{pid}")
    Set<BookData> getAllRatingsFromUser(@ApiParam(value = "Person id to search with") @PathVariable Integer pid) {

        User p = pdb.findById(pid).orElseThrow(NoSuchElementException::new);
        return p.getBookData();
    }
}
