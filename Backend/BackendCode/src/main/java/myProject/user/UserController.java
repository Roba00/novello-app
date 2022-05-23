package myProject.user;

import io.swagger.annotations.*;
import myProject.chat.ChatRoom;
import myProject.chat.ChatRoomRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Api(value = "UserController")
@RestController
public class UserController {

    @Autowired
	UserInterface db;
    @Autowired
    ChatRoomRepository crdb;

    @Autowired
    UserInfoInterface userInfoInterfaceDB;

    @ApiOperation(value = "Get a user from a userID", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/user/{id}")
	User getUser(@ApiParam(value = "ID of the user you are looking for") @PathVariable Integer id) {
        return db.findById(id).
                orElseThrow(RuntimeException::new);
    }

    @ApiOperation(value = "Gets the username if login is successful", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/login")
    JSONObject login(@ApiParam (value = "json object that has the username and password entered by user to check if they can log in")@RequestBody JSONObject json){

        User user;
        JSONObject jsonReturn = new JSONObject();

        // Database counting order: 4, 14, 24, 34, 44, ...
        int lastElement = 4 + (int)(db.count() - 1) * 10;
        if (db.count() == 0) lastElement = 0;

        for (int i = 4; i <= lastElement; i+=20) {
            user = db.findById(i).orElseThrow(RuntimeException::new);
            if (user.username.equals(json.getAsString("username"))) {
                if (user.password.equals(json.getAsString("password"))) {
                    jsonReturn.put("userId",i);
                    return jsonReturn;
                }
            }
        }
        return null;
    }

    @ApiOperation(value = "Creates all the users at once and adds them to the data base", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/addAllUsers")
    void createAllPersons(@ApiParam (value = "All of the users jsonObjects")@RequestBody JSONObject[] jsonObject) {
        for (JSONObject object : jsonObject) {
            User u = new User();
            u.setAccountType((Integer) object.getAsNumber("accountType"));
            u.setUsername(object.getAsString("username"));
            u.setPassword(object.getAsString("password"));
            u.setSecurityQuestion(object.getAsString("securityQuestion"));
            u.setSecurityAnswer(object.getAsString("securityAnswer"));

            UserInfo ui = new UserInfo();
            ui.setUser(u);
            ui.setAge((Integer) object.getAsNumber("age"));
            ui.setEmail(object.getAsString("email"));
            ui.setName(object.getAsString("name"));

            u.setUserInfo(ui);

            db.save(u);
            userInfoInterfaceDB.save(ui);
        }
    }
    @ApiOperation(value = "Gets all users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/users")//was request mapping
    List<User> getPersons() {
        return db.findAll();
    }

    @ApiOperation(value = "Creates a specific user", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/user")
	User createPerson(@ApiParam (value = "jsonObject with all of the info of a user", example = "{foo: whatever, bar: whatever2}") @RequestBody JSONObject jsonObject) {
        User u = new User();
        if( jsonObject.getAsNumber("accountType") == null)
        {
            u.setUsername(jsonObject.getAsString("username"));
            u.setPassword(jsonObject.getAsString("password"));
            UserInfo ui = new UserInfo();
            ui.setUser(u);
            db.save(u);
            userInfoInterfaceDB.save(ui);
            return u;

        }
        u.setAccountType((Integer) jsonObject.getAsNumber("accountType"));
        u.setUsername(jsonObject.getAsString("username"));
        u.setPassword(jsonObject.getAsString("password"));
        u.setSecurityQuestion(jsonObject.getAsString("securityQuestion"));
        u.setSecurityAnswer(jsonObject.getAsString("securityAnswer"));

        UserInfo ui = new UserInfo();
        ui.setUser(u);
        ui.setAge((Integer) jsonObject.getAsNumber("age"));
        ui.setEmail(jsonObject.getAsString("email"));
        ui.setName(jsonObject.getAsString("name"));

        u.setUserInfo(ui);

        db.save(u);
        userInfoInterfaceDB.save(ui);
        return u;
    }
    @ApiOperation(value = "updating a user via a userID", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping("/user/{id}")
	User updatePerson(@ApiParam (value = "The Jason object of the updated person")@RequestBody JSONObject jsonObject, @ApiParam (value = "The ID of the person we are updating")@PathVariable Integer id) {
        User old_u = db.findById(id).orElseThrow(RuntimeException::new);
        UserInfo old_ui = old_u.getUserInfo();
        if (jsonObject.getAsString("name") != null)
            old_ui.setName(jsonObject.getAsString("name"));
        if (jsonObject.getAsNumber("accountType") != null)
            old_u.setAccountType((Integer) jsonObject.getAsNumber("accountType"));
        if (jsonObject.getAsString("username") != null)
            old_u.setUsername(jsonObject.getAsString("username"));
        if (jsonObject.getAsString("password") != null)
            old_u.setPassword(jsonObject.getAsString("password"));
        if (jsonObject.getAsString("securityAnswer")!= null)
            old_u.setSecurityAnswer(jsonObject.getAsString("securityAnswer"));
        if (jsonObject.getAsString("securityQuestion") != null)
            old_u.setSecurityQuestion(jsonObject.getAsString("securityQuestion"));
        if (jsonObject.getAsString("email") != null)
            old_ui.setEmail(jsonObject.getAsString("email"));
        if (jsonObject.getAsNumber("age") != null)
            old_ui.setAge((Integer) jsonObject.getAsNumber("age"));
        db.save(old_u);
        userInfoInterfaceDB.save((old_ui));
        return old_u;
    }

    @ApiOperation(value = "Deletes a user from a userID", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/user/{id}")
    String deletePerson(@ApiParam (value = "The ID of the user you want to delete")@PathVariable Integer id) {
        db.deleteById(id);
        return "deleted " + id;
    }

    @PostMapping("/room")
    void addRoom(@RequestBody JSONObject json){
        User user = db.findById(Integer.parseInt( json.getAsString("userId"))).orElseThrow(NoSuchElementException::new);
        Set<ChatRoom> chatRoomSet = user.getChatRooms();
        chatRoomSet.add(crdb.findById(Integer.parseInt( json.getAsString("roomId"))).orElseThrow(NoSuchElementException::new));
        user.setChatRooms(chatRoomSet);
        db.save(user);
        ChatRoom chatroom = crdb.findById(Integer.parseInt( json.getAsString("roomId"))).orElseThrow(NoSuchElementException::new);
        Set<User> userSet = chatroom.getUsers();
        userSet.add(user);
        chatroom.setUsers(userSet);
        crdb.save(chatroom);
    }

    @GetMapping("/room/{id}")
    Set<ChatRoom> getRoom(@PathVariable Integer id/*@RequestBody JSONObject json[]*/){
        User user = db.findById(id).orElseThrow(NoSuchElementException::new);
        return user.getChatRooms();
    }
}
