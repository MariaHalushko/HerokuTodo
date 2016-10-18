package com.todo.rest;

import com.todo.dao.interfaces.TaskDao;
import com.todo.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/tasks")
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Task task) {
        taskDao.save(task);
        return new ResponseEntity("saved",HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{taskId}", method = RequestMethod.POST)
    public void delete(@PathVariable("taskId") Long taskId) {
        taskDao.delete(taskId);
    }
}
