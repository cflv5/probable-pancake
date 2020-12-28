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

INSERT INTO library_setting(name, value, type)
VALUES
('allowedBorrowingBooksSize', '3', 'NUMERIC'),
('retroactiveBorrow', 'false', 'BOOLEAN'),
('forwardBorrow', 'false', 'BOOLEAN'),
('minBorrowDay', '0', 'NUMERIC'),
('maxBorrowDay', '14', 'NUMERIC'),
('maxExtension', '3', 'NUMERIC'),
('extensionDay', '7', 'NUMERIC')
ON CONFLICT DO NOTHING;

INSERT INTO writer(name, surname)
VALUES('Ömer', 'Seyfettin') ON CONFLICT DO NOTHING;

INSERT INTO publisher(name)
VALUES('Yapı Kredi Yayınları') ON CONFLICT DO NOTHING;

INSERT INTO book(name, isbn, publisher_id)
VALUES('Falaka', '12213213', 1) ON CONFLICT DO NOTHING;

INSERT INTO member(member_id, name, surname)
VALUES('o3Svp5XfEs', 'Bedirhan', 'Akçay') ON CONFLICT DO NOTHING;