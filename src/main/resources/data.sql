INSERT INTO user_role(role)
VALUES
('ADMIN'),
('EDITOR'),
('LIBRARIAN')
ON CONFLICT DO NOTHING;

INSERT INTO users(name, surname, email, password, status)
VALUES('root', 'root', 'root@root', '1234', 'ACTIVE') ON CONFLICT DO NOTHING;

INSERT INTO users_roles(user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3)
ON CONFLICT DO NOTHING;

