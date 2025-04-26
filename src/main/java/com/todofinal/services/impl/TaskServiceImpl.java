package com.todofinal.services.impl;

import com.todofinal.models.Task;
import com.todofinal.repository.TaskRepository;
import com.todofinal.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(Task task) {
        taskRepository.createTask(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    @Override
    public void updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setCompleted(taskDetails.isCompleted());
        task.setParentId(taskDetails.getParentId());
        taskRepository.updateTask(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteTask(id);
    }
}
