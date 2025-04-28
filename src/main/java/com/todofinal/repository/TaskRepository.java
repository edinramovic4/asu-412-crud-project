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

    public int toggleTasks(List<Long> taskIds, boolean completed) {
        if (taskIds == null || taskIds.isEmpty()) {
            return 0;
        }

        String inSql = String.join(",", java.util.Collections.nCopies(taskIds.size(), "?"));
        String sql = String.format("UPDATE task SET completed = ? WHERE id IN (%s)", inSql);

        List<Object> params = new java.util.ArrayList<>();
        params.add(completed ? 1 : 0);
        params.addAll(taskIds);

        return jdbcTemplate.update(sql, params.toArray());
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

    public void createParentAndChildTask(String parentTitle, String childTitle) {
        jdbcTemplate.update("BEGIN TRANSACTION");
        try {
            String parentSql = "INSERT INTO task (title, completed, parent_id) VALUES (?, 0, NULL)";
            jdbcTemplate.update(parentSql, parentTitle);

            String childSql = "INSERT INTO task (title, completed, parent_id) VALUES (?, 0, (SELECT last_insert_rowid()))";
            jdbcTemplate.update(childSql, childTitle);

            jdbcTemplate.update("COMMIT");
        } catch (Exception e) {
            jdbcTemplate.update("ROLLBACK");
            throw e;
        }
    }

    public void deleteAllCompletedTasks() {
        jdbcTemplate.update("BEGIN TRANSACTION");
        try {
            jdbcTemplate.update("DELETE FROM task WHERE completed = 1");
            jdbcTemplate.update("COMMIT");
        } catch (Exception e) {
            jdbcTemplate.update("ROLLBACK");
            throw e;
        }
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