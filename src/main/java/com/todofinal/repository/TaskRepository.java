package com.todofinal.repository;

import com.todofinal.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    public int toggleAllTasks(boolean completed) {
        String sql = "UPDATE task SET completed = ?";
        return jdbcTemplate.update(sql, completed ? 1 : 0);
    }

    public Task getTaskById(Long id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    public int createTask(Task task) {
        String sql = "INSERT INTO task (title, completed, parent_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, task.getTitle(), task.isCompleted(), task.getParentId());
    }

    public int updateTask(Task task) {
        String sql = "UPDATE task SET title = ?, completed = ?, parent_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getTitle(), task.isCompleted(), task.getParentId(), task.getId());
    }

    public int deleteTask(Long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getBoolean("completed"),
                    rs.getLong("parent_id"),
                    rs.getString("last_updated")
            );
        }
    }
}