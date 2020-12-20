INSERT INTO user_role(role)
VALUES
('ADMIN'),
('EDITOR'),
('LIBRARIAN')
ON CONFLICT DO NOTHING;

INSERT INTO users(name, surname, email, password, status)
VALUES('root', 'root', 'root@root', '$2b$10$kfNyYHVarL1aouXvwJ5Q5eR6.mGsrOqvY7NUb6ga9Y0QjFGB3JJJ.', 'ACTIVE') ON CONFLICT DO NOTHING;

INSERT INTO users_roles(user_id, roles_id)
VALUES
(1, 1),
(1, 2),
(1, 3)
ON CONFLICT DO NOTHING;