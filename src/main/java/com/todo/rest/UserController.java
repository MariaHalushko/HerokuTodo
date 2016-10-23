package com.todo.rest;

import com.todo.dao.interfaces.UserDao;
import com.todo.model.Task;
import com.todo.model.User;
import com.todo.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("api/users")
public class UserController {

//
//    public static void main(String[] args) {
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//        System.out.println(new Random().nextInt(3));
//
//    }


    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/add1000users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add1000users() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 1000; i++) {
            String fName = new BigInteger(130, random).toString(32);
            String lame = new BigInteger(130, random).toString(32);
            User user = new User(fName, lame);
            for (int j = 0; j < 20; j++) {
                Task task = new Task();
                task.setName(new BigInteger(130, random).toString(32));
                task.setComment(new BigInteger(1000, random).toString(32));
                Date date = new Date(new Date().getTime()-7*24*60*60*1000L);
                Long delta = Long.valueOf(new Random().nextInt(14*24*60*60*1000));
                Date randomDate = new Date(date.getTime()+delta);
                task.setStartDate(randomDate);
                Integer estimation = new Random().nextInt(8*60);
                task.setEstimationMinutes(estimation);
                task.setStatus(Status.values()[new Random().nextInt(3)]);
                user.getTasks().add(task);
            }
            userDao.save(user);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("userId") Long userId) {
        User user = userDao.findById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTasks(@PathVariable("userId") Long userId) {
        List<Task> tasks = userDao.getUserTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@RequestBody User user) {
        userDao.update(user);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }


    @RequestMapping(value = "/read/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestParam(value="page",required = false,defaultValue = "1") Integer page,
            @RequestParam(value="per_page",required = false,defaultValue = "20") Integer perPage,
            @RequestParam(value="searchString",required = false) String searchString) {
        List<User> result = userDao.listAll();

        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody User user) {
        System.out.println(user);
        userDao.save(user);
        return new ResponseEntity("saved", HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("userId") Long userId) {
        User user = userDao.findById(userId);
        userDao.delete(user);
        return new ResponseEntity("deleted", HttpStatus.OK);
    }
}
