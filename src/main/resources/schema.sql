CREATE TABLE IF NOT EXISTS task (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    title TEXT NOT NULL,
                                    completed BOOLEAN NOT NULL DEFAULT 0,
                                    parent_id INTEGER,
                                    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (parent_id) REFERENCES task(id)
    );

-- Auto update last updated timestamp
CREATE TRIGGER IF NOT EXISTS update_last_modified
    AFTER UPDATE ON task
    FOR EACH ROW
BEGIN
    UPDATE task
    SET last_updated = CURRENT_TIMESTAMP
    WHERE id = OLD.id;
END;