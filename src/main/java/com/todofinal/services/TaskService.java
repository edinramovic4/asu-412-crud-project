package com.todofinal.services;

import com.todofinal.models.Task;

import java.util.List;

public interface TaskService {
    void createTask(Task task);

    List<Task> getAllTasks();

    void updateTask(Long id, Task taskDetails);

    void deleteTask(Long id);

    void createParentAndChildTask(String parentTitle, String childTitle);

    void toggleTasks(List<Long> taskIds, boolean completed);

    void deleteAllCompletedTasks();
}
