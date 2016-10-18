package com.todo.rest;

import com.todo.dao.interfaces.UserDao;
import com.todo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by mary on 17.10.16.
 */
@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserDao userDao;


    @RequestMapping(value = "/read/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll() {
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
        return new ResponseEntity("saved",HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("userId") Long userId) {
        User user = userDao.findById(userId);
        userDao.delete(user);
        return new ResponseEntity("deleted",HttpStatus.OK);
    }
}
