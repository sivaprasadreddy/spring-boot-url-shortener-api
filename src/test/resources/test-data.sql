delete from short_urls;
delete from users;

alter sequence users_id_seq restart with 100;
alter sequence short_urls_id_seq restart with 100;

INSERT INTO users (id, email, password, name, role) VALUES
(1, 'admin@gmail.com', '$2a$10$Ewl5M5WzAvGl3./qaT8od.Sz1vj34GkYPMEOnZSpR6351NCRIVr2e', 'Administrator', 'ROLE_ADMIN'),
(2, 'siva@gmail.com', '$2a$10$NTH5YKrxFns/DYNc.qVbfOQpHbMZ/SExTorPBcVO1b2exW4QHljm.', 'Siva', 'ROLE_USER')
;

INSERT INTO short_urls (short_key, original_url, created_by, created_at, expires_at, is_private, click_count) VALUES
('github', 'https://github.com', 1, TIMESTAMP '2024-07-15', NULL, FALSE, 0),
('stacko', 'https://stackoverflow.com', 1, TIMESTAMP '2024-07-14', NULL, FALSE, 0),
('mdnmoz', 'https://developer.mozilla.org', 1, TIMESTAMP '2024-07-13', NULL, FALSE, 0),
('w3scho', 'https://www.w3schools.com', 1, TIMESTAMP '2024-07-12', NULL, TRUE, 0),
('medium', 'https://medium.com', 2, TIMESTAMP '2024-07-11', TIMESTAMP '2024-08-10', FALSE, 0),
('devtow', 'https://dev.to', 2, TIMESTAMP '2024-07-10', TIMESTAMP '2024-08-9', FALSE, 0),
('hackrn', 'https://news.ycombinator.com', 1, TIMESTAMP '2024-07-9', NULL, FALSE, 0),
('reddit', 'https://www.reddit.com/r/programming', 1, TIMESTAMP '2024-07-8', NULL, FALSE, 0),
('codeac', 'https://www.codecademy.com', 1, TIMESTAMP '2024-07-7', NULL, FALSE, 0),
('freeco', 'https://www.freecodecamp.org', 1, TIMESTAMP '2024-07-6', NULL, FALSE, 0),
('leetco', 'https://leetcode.com', 1, TIMESTAMP '2024-07-5', NULL, FALSE, 0),
('spring', 'https://spring.io', 1, TIMESTAMP '2024-07-4', NULL, TRUE, 0),
('javdoc', 'https://docs.oracle.com/en/java', 1, TIMESTAMP '2024-07-3', NULL, FALSE, 0),
('pytorg', 'https://www.python.org', 1, TIMESTAMP '2024-07-2', NULL, FALSE, 0),
('npmjsc', 'https://www.npmjs.com', 1, TIMESTAMP '2024-07-1', NULL, TRUE, 0),
('docker', 'https://www.docker.com', 1, TIMESTAMP '2024-03-15', NULL, FALSE, 0),
('kubern', 'https://kubernetes.io', 1, TIMESTAMP '2024-03-10', NULL, FALSE, 0),
('awsdoc', 'https://docs.aws.amazon.com', 1, TIMESTAMP '2024-02-5', NULL, TRUE, 0),
('azured', 'https://docs.microsoft.com/en-us/azure', 1, TIMESTAMP '2024-02-2', NULL, FALSE, 0),
('gcpdoc', 'https://cloud.google.com/docs', 1, TIMESTAMP '2024-01-15', NULL, FALSE, 0);
