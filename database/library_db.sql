-- Library Management System
-- Run this in MySQL (Workaround / command line) before starting the Java app

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

DROP TABLE IF EXISTS issue_records;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS members;

CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    quantity INT NOT NULL DEFAULT 1
);

CREATE TABLE members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15)
);

CREATE TABLE issue_records (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'ISSUED',
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

-- sample data so we can test quickly
INSERT INTO books (title, author, category, quantity) VALUES
('Java Basics', 'Herbert Schildt', 'Programming', 5),
('Clean Code', 'Robert Martin', 'Programming', 3),
('Harry Potter', 'J.K. Rowling', 'Fiction', 4);

INSERT INTO members (name, email, phone) VALUES
('Ravi Kumar', 'ravi@gmail.com', '9876543210'),
('Anitha S', 'anitha@gmail.com', '9123456780');
