package com.todo.rest;

import com.todo.dao.interfaces.TaskDao;
import com.todo.model.Task;
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

@RequestMapping("api/tasks")
@Controller
public class TaskController {

    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "/read/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> listAll() {
        List<Task> result = taskDao.listAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("taskId") Long taskId) {
        Task task = taskDao.findById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Task task) {
        taskDao.save(task);
        return new ResponseEntity("saved", HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@RequestBody Task task) {
        taskDao.update(task);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("taskId") Long taskId) {
        Task task = taskDao.findById(taskId);
        taskDao.delete(task);
        return new ResponseEntity("deleted", HttpStatus.OK);
    }
}
