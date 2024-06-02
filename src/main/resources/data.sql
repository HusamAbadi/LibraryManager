-- Create publishers table
CREATE TABLE IF NOT EXISTS publishers (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          description VARCHAR(255) NOT NULL
);

-- Create presenters table
CREATE TABLE IF NOT EXISTS presenters (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(255) NOT NULL,
                                          expertise VARCHAR(255) NOT NULL,
                                          publisher_id BIGINT,
                                          FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL
);

-- Create events table
CREATE TABLE IF NOT EXISTS events (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      name VARCHAR(255) NOT NULL,
                                      description VARCHAR(255) NOT NULL,
                                      publisher_id BIGINT,
                                      presenter_id BIGINT,
                                      date DATE,
                                      max_attendees INT,
                                      FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL,
                                      FOREIGN KEY (presenter_id) REFERENCES presenters(id) ON DELETE SET NULL
);


-- Create attendees table
CREATE TABLE IF NOT EXISTS attendees (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(255) NOT NULL,
                                         email VARCHAR(255) NOT NULL,
                                         event_id BIGINT,
                                         FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE SET NULL


);


-- Insert sample data for publishers
INSERT INTO publishers (name, description) VALUES ('Penguin Random House', 'One of the largest and most renowned publishing houses globally, known for publishing a wide range of fiction, non-fiction, and children’s books.');
INSERT INTO publishers (name, description) VALUES ('HarperCollins', 'A major publishing company with a rich history, publishing a diverse array of genres, including literary fiction, romance, thrillers, and educational books');
INSERT INTO publishers (name, description) VALUES ('Simon & Schuster', 'A prominent American publishing company, known for its broad portfolio of bestselling authors and significant contributions to literature, non-fiction, and self-help');

-- Insert sample data for presenters
INSERT INTO presenters (name, expertise, publisher_id) VALUES ('Max', 'Web developer', 1);
INSERT INTO presenters (name, expertise, publisher_id) VALUES ('Alina', 'Software engineer', 3);
INSERT INTO presenters (name, expertise, publisher_id) VALUES ('George', 'Marketing lead', 2);


-- Insert sample data for events
INSERT INTO events (name, description, publisher_id, date, max_attendees, presenter_id)
VALUES ('Apple keynote', 'Keynote is a presentation software application developed as a part of the iWork productivity suite by Apple Inc.', 1, '2023-06-10', 2600, 1);

INSERT INTO events (name, description, publisher_id, date, max_attendees, presenter_id)
VALUES ('Google I/O', 'Google I/O is an annual developer conference held by Google in Mountain View, California.', 2, '2023-06-10', 249, 3);

INSERT INTO events (name, description, publisher_id, date, max_attendees, presenter_id)
VALUES ('React Dev event', 'Upcoming React Conferences in 2023', 3, '2023-06-10', 132, 2);


-- Insert sample data for attendees
INSERT INTO attendees (name, email, event_id) VALUES ('Wisam abadi', 'wisam@example.com', 1);
INSERT INTO attendees (name, email, event_id) VALUES ('John will', 'jhon@example.com', 1);

