-- Create publishers table
CREATE TABLE IF NOT EXISTS publishers (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          organization VARCHAR(255) NOT NULL
);

-- Create authors table
CREATE TABLE IF NOT EXISTS authors (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          expertise VARCHAR(255) NOT NULL,
                                          publisher_id BIGINT,
                                          FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL
);

-- Create books table
CREATE TABLE IF NOT EXISTS books (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      name VARCHAR(255) NOT NULL,
                                      description VARCHAR(255) NOT NULL,
                                      publisher_id BIGINT,
                                      author_id BIGINT,
                                      date DATE,
                                      max_renters INT,
                                      FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL,
                                      FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE SET NULL
);


-- Create renters table
CREATE TABLE IF NOT EXISTS renters (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(255) NOT NULL,
                                         email VARCHAR(255) NOT NULL,
                                         book_id BIGINT,
                                         FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL


);


-- Insert sample data for publishers
INSERT INTO publishers (name, organization) VALUES ('Apple', 'Apple Inc. is an American multinational technology company headquartered in Cupertino, California.');
INSERT INTO publishers (name, organization) VALUES ('Google', 'Google LLC is an American multinational technology company focusing on artificial intelligence and online advertising.');
INSERT INTO publishers (name, organization) VALUES ('Facebook', 'Meta Platforms, Inc., formerly named Facebook, Inc., and TheFacebook, Inc., is an American multinational technology conglomerate based in Menlo Park, California. ');

-- Insert sample data for authors
INSERT INTO authors (name, expertise, publisher_id) VALUES ('Max', 'Web developer', 1);
INSERT INTO authors (name, expertise, publisher_id) VALUES ('Alina', 'Software engineer', 3);
INSERT INTO authors (name, expertise, publisher_id) VALUES ('George', 'Marketing lead', 2);


-- Insert sample data for books
INSERT INTO books (name, description, publisher_id, date, max_renters, author_id)
VALUES ('Apple keynote', 'Keynote is a presentation software application developed as a part of the iWork productivity suite by Apple Inc.', 1, '2023-06-10', 2600, 1);

INSERT INTO books (name, description, publisher_id, date, max_renters, author_id)
VALUES ('Google I/O', 'Google I/O is an annual developer conference held by Google in Mountain View, California.', 2, '2023-06-10', 249, 3);

INSERT INTO books (name, description, publisher_id, date, max_renters, author_id)
VALUES ('React Dev book', 'Upcoming React Conferences in 2023', 3, '2023-06-10', 132, 2);


-- Insert sample data for renters
INSERT INTO renters (name, email, book_id) VALUES ('Husam abadi', 'husam@example.com', 1);
INSERT INTO renters (name, email, book_id) VALUES ('John will', 'jhon@example.com', 1);

