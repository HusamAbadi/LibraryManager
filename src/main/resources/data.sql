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


-- Create renters table
CREATE TABLE IF NOT EXISTS renters (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(255) NOT NULL,
                                         email VARCHAR(255) NOT NULL,
                                         book_id BIGINT,
                                         FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL


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
VALUES ('1984', 'A dystopian novel set in a totalitarian society under constant surveillance, where independent thinking is suppressed and history is manipulated', 1, '1949-06-08', 20, 1);

INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('Animal Farm', 'This allegorical novella satirizes the Russian Revolution and the subsequent rise of Stalinism. It tells the story of a group of farm animals who overthrow their human farmer, hoping to create a society where animals can be equal, free, and happy.', 2, '1945-08-17', 28, 1);

INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('To Kill a Mockingbird', 'A classic novel that addresses racial injustice in the Deep South through the eyes of a young girl named Scout Finch, whose father, Atticus, is an attorney defending a black man falsely accused of raping a white woman.', 2, '1960-07-11', 14, 2);

INSERT INTO books (name, description, publisher_id, date, copies_num, author_id)
VALUES ('The Shining', 'Description: A horror novel about a family staying in an isolated hotel where the father, Jack Torrance, succumbs to supernatural influences and descends into violence and madness.', 3, '1977-01-28', 7, 3);


-- Insert sample data for renters
INSERT INTO renters (name, email, book_id) VALUES ('Wisam abadi', 'wisam@example.com', 1);
INSERT INTO renters (name, email, book_id) VALUES ('John will', 'jhon@example.com', 1);

