CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create default admin user with password 'admin'
INSERT INTO users (email, password, name, role)
VALUES ('admin@gmail.com', '$2a$10$Ewl5M5WzAvGl3./qaT8od.Sz1vj34GkYPMEOnZSpR6351NCRIVr2e', 'Administrator', 'ROLE_ADMIN');

-- Create default user with password 'secret'
INSERT INTO users (email, password, name, role)
VALUES ('siva@gmail.com', '$2a$10$NTH5YKrxFns/DYNc.qVbfOQpHbMZ/SExTorPBcVO1b2exW4QHljm.', 'Siva', 'ROLE_USER');
