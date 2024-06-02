-- Create publishers table
CREATE TABLE IF NOT EXISTS publishers (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          description VARCHAR(255) NOT NULL
);

-- Create authors table
CREATE TABLE IF NOT EXISTS authors (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          profession VARCHAR(255) NOT NULL,
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
                                      copies_num INT,
                                      FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL,
                                      FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE SET NULL
);


-- Create attendees table
CREATE TABLE IF NOT EXISTS attendees (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(255) NOT NULL,
                                         email VARCHAR(255) NOT NULL,
                                         event_id BIGINT,
                                         FOREIGN KEY (event_id) REFERENCES books(id) ON DELETE SET NULL


);


-- Insert sample data for publishers
INSERT INTO publishers (name, description) VALUES ('Penguin Random House', 'One of the largest and most renowned publishing houses globally, known for publishing a wide range of fiction, non-fiction, and children’s books.');
INSERT INTO publishers (name, description) VALUES ('HarperCollins', 'A major publishing company with a rich history, publishing a diverse array of genres, including literary fiction, romance, thrillers, and educational books');
INSERT INTO publishers (name, description) VALUES ('Simon & Schuster', 'A prominent American publishing company, known for its broad portfolio of bestselling authors and significant contributions to literature, non-fiction, and self-help');

-- Insert sample data for authors
INSERT INTO authors (name, profession, publisher_id) VALUES ('George Orwell', 'Novelist, essayist, journalist', 1);
INSERT INTO authors (name, profession, publisher_id) VALUES ('Harper Lee', 'Novelist', 2);
INSERT INTO authors (name, profession, publisher_id) VALUES ('Stephen King', 'Novelist, short story writer, screenwriter', 3);


-- Insert sample data for books
INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('Apple keynote', 'Keynote is a presentation software application developed as a part of the iWork productivity suite by Apple Inc.', 1, '2023-06-10', 2600, 1);

INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('Google I/O', 'Google I/O is an annual developer conference held by Google in Mountain View, California.', 2, '2023-06-10', 249, 3);

INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('React Dev book', 'Upcoming React Conferences in 2023', 3, '2023-06-10', 132, 2);


-- Insert sample data for attendees
INSERT INTO attendees (name, email, event_id) VALUES ('Wisam abadi', 'wisam@example.com', 1);
INSERT INTO attendees (name, email, event_id) VALUES ('John will', 'jhon@example.com', 1);

