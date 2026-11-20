CREATE TABLE IF NOT EXISTS employees (
                                         id BIGINT PRIMARY KEY CHECK (id > 0),
    name VARCHAR(100) NOT NULL,
    salary DECIMAL(15, 2) NOT NULL CHECK (salary >= 0)
    );