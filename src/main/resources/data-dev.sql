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

INSERT INTO member(member_id, name, status, surname)
VALUES
('5qy3QghJLs', 'Bedirhan', 'PASSIVE', 'Akçay'),
('c4BruSd67q', 'Semih', 'ACTIVE', 'Durmaz'),
('Lkmqp90b2S', 'Yunus Emre', 'ACTIVE', 'Demir')
ON CONFLICT DO NOTHING;

INSERT INTO publisher(id, name)
VALUES
(1, 'Bilgi Yayınevi'),
(2, 'İş Bankası Kültür Yayınları'),
(3, 'De Gruyter')
ON CONFLICT DO NOTHING;

INSERT INTO writer(id, name, surname)
VALUES
(1, 'Ömer', 'Seyfettin'),
(2, 'Jonathan','Swift'),
(3, 'Immanuel', 'Kant')
ON CONFLICT DO NOTHING;

INSERT INTO book(id, name, borrowed, cover_format, isbn, language, number_of_pages, publish_date, publisher_id)
VALUES
(1, 'Kritik der reinen Vernunft', false, 'HARD', '9783111043388', 'Almanca', 609, '1900-06-01', 3),
(2, 'Gülliver''in Gezileri', true, 'SOFT', '9786052950982', 'Türkçe', 107, '2017-01-01', 2),
(3, 'Falaka', true, 'SOFT', '9789754944402', 'Türkçe', 136, '2020-01-01', 1)
ON CONFLICT DO NOTHING;

INSERT INTO book_writers(books_id, writers_id)
VALUES
(1, 3),
(2, 2),
(3, 1)
ON CONFLICT DO NOTHING;

INSERT INTO borrowing(id, deadline, extension, start_date, status, creator_id, member_id)
VALUES
('puYv4d2jiL', '2021-01-01', 0, '2020-12-19', 'NOT_RETURNED', 1, 2)
ON CONFLICT DO NOTHING;

INSERT INTO borrowing_books(borrowing_id, books_id)
VALUES
('puYv4d2jiL', 2)
ON CONFLICT DO NOTHING;