package myProject.friends;


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
public class FriendsController {
    @Autowired
    FriendsInterface FIDB;
    @Autowired
    UserInterface UIDB;

    @PostMapping("/friend")
    Friends createFriend(@RequestBody JSONObject jsonObject) {

        User user;
        int i;
        int uid = 0;
        for (i = 1; i <= UIDB.count(); i++) {
            user = UIDB.findById(i).orElseThrow(RuntimeException::new);
            if (user.username.equals(jsonObject.getAsString("receiverusrname"))) {
                uid = user.getId();
                break;
            }
        }

        FriendsKey friendsKey = new FriendsKey();
        friendsKey.setSenderId((Integer) jsonObject.getAsNumber("senderId"));
        friendsKey.setReceiverId(uid);

        Friends f = new Friends();
        f.setId(friendsKey);
        User sender = UIDB.findById(friendsKey.senderId).orElseThrow(NoSuchElementException::new);
        User receiver = UIDB.findById(uid).orElseThrow(NoSuchElementException::new);


        f.setSender(sender);
        f.setReceiver(receiver);

        FriendsKey oldFriend = new FriendsKey();
        oldFriend.setSenderId((Integer) jsonObject.getAsNumber("uid"));
        oldFriend.setReceiverId((Integer) jsonObject.getAsNumber("senderId"));
        Friends old = new Friends();
        int status = 2;
        try {
            old = FIDB.findById(oldFriend).orElseThrow(NoSuchElementException::new);


            if (old.getFriendshipStatus() == 1) ;
            {
                old.setFriendshipStatus(2);
                status = 2;
            }
        } catch (Exception e) {
        }


        f.setFriendshipStatus(status);
        FIDB.save(f);
        return f;
        //  User kevin = UIDB.findById(jsonObject.getAsNumber("uid"));
    }

    @GetMapping("/friends/{id}")
    Set<Friends> returnAllFriends(@PathVariable int id) {

        User u = UIDB.findById(id).orElseThrow(RuntimeException::new);
        Set<Friends> friendsSet = new HashSet<>();
        Set<Friends> allMutralFriends = new HashSet<>();
        friendsSet = u.getFriends();
        Iterator<Friends> friendsIterator = friendsSet.iterator();
        Friends f;
        while (friendsIterator.hasNext()) {
            f = friendsIterator.next();
            if ((id == f.getSender().id) && f.getFriendshipStatus() == 2) {
                allMutralFriends.add(f);
            }
        }
        return allMutralFriends;
    }

//    @DeleteMapping("/friends/{id}")
//    String deletePerson(@ApiParam(value = "The ID of the user you want to delete")@PathVariable Integer id) {
//        JSONObject jsonObject;
//        User u = UIDB.findById((int)jsonObject.getAsNumber("id")).orElseThrow(NoSuchElementException::new);
//        Set<Friends> friendsSet = new HashSet<>();
//        Set<Friends> allMutralFriends = new HashSet<>();
//        friendsSet = u.getFriends();
//        Iterator<Friends> friendsIterator = friendsSet.iterator();
//        Friends f;
//        while(friendsIterator.hasNext())
//        {
//            f = friendsIterator.next();
//            if((u.getId() == f.getSender().id) && f.getFriendshipStatus() == 2)
//            {
//                allMutralFriends.add(f);
//            }
//        }
//        UIDB.deleteById(id);
//        return "deleted " + id;
//    }
}
