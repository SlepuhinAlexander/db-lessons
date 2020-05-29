-- СОЗДАНИЕ ТАБЛИЦ
CREATE TABLE IF NOT EXISTS author(
   author_id serial PRIMARY KEY,
   name VARCHAR (50) NOT NULL,
    age INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS article(
	article_id serial PRIMARY KEY,
	title VARCHAR (50) UNIQUE NOT NULL,
	text TEXT NOT NULL,
	created_on TIMESTAMP NOT NULL,
	author_id INTEGER NOT NULL,
	CONSTRAINT author_article FOREIGN KEY (author_id)
	REFERENCES author(author_id)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS nomination(
   nomination_id serial PRIMARY KEY,
   name VARCHAR (150) NOT NULL
);

CREATE TYPE rate AS ENUM ('first', 'second', 'third');

CREATE TABLE IF NOT EXISTS nomination_article
(
  nomination_id integer NOT NULL,
  article_id integer NOT NULL,
  rating rate,
  PRIMARY KEY (nomination_id, article_id),
  CONSTRAINT nomination_fk FOREIGN KEY (nomination_id)
      REFERENCES nomination (nomination_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT article_fk FOREIGN KEY (article_id)
      REFERENCES article (article_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- ВСТАВКА ДАННЫХ В ТАБЛИЦУ
INSERT INTO author (name, age)
VALUES
('qwerty', 28),
('reanno', 46),
('ivan334', 32),
('apple', 21),
('wind23', 23);

INSERT INTO article (title, text, created_on, author_id)
VALUES
('Stream API', 'Статья про Stream API', '2017-10-19 10:23', 1),
('Lambda', 'Статья про Lambda', '2019-03-27 16:33', 1),
('Java 13', 'Статья про Java 13', '2019-11-28 17:10', 2),
('Garbage Collectors', 'Статья про GC', '2019-08-07 11:55', 2),
('Hibernate', 'Статья про Hibernate', '2018-12-01 21:30', 2),
('Collections API', 'Collections API', '2019-11-22 22:29', 3);

INSERT INTO nomination (name)
VALUES
('Java 8'),
('Базы Данных'),
('Функциональное Программирование Java');

INSERT INTO nomination_article (nomination_id, article_id, rating)
VALUES
(1, 2, 'first'),
(1, 1, 'third'),
(2, 5, 'third'),
(3, 2, 'first'),
(3, 6, 'third');
