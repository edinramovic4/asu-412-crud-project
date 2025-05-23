package com.todofinal.controller;

import com.todofinal.models.Task;
import com.todofinal.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public void createTask(@RequestBody Task task) {
        taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/parent-child")
    public void createParentAndChild(@RequestParam String parentTitle, @RequestParam String childTitle) {
        taskService.createParentAndChildTask(parentTitle, childTitle);
    }

    @PutMapping("/toggle")
    public void toggleTasks(@RequestParam List<Long> taskIds, @RequestParam boolean completed) {
        taskService.toggleTasks(taskIds, completed);
    }

    @DeleteMapping("/clear")
    public void deleteAllCompletedTasks() {
        taskService.deleteAllCompletedTasks();
    }
}