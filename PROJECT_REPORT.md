# Project Report

**Title:** Library Management System  
**Level:** Beginner  
**Duration:** 2 weeks  
**Language:** Java  
**Database:** MySQL (JDBC)

## 1. Objective
Build a small console application that can manage books, library members, and issue/return records.

## 2. Approach
I first designed 3 simple classes: Book, Member and IssueRecord.  
Then I created DAO classes to talk to MySQL.  
`LibraryApp` is the main class. It only shows menus and takes input.  
All database work is inside dao package.

## 3. Database tables
- **books** – id, title, author, category, quantity
- **members** – id, name, email, phone
- **issue_records** – id, book_id, member_id, issue_date, return_date, status

When a book is issued, quantity is reduced by 1.  
When it is returned, quantity is increased by 1.

## 4. Features
### Book
Add, view, search (id/title), update, delete, check availability

### Member
Add, view, search, update, delete

### Library
Issue book, return book, track issued books, view available books

## 5. Validation (basic)
- Title, author, member name cannot be empty
- Quantity should be at least 1 while adding
- Email should contain @ if entered
- Cannot issue a book if quantity is 0
- Cannot return the same issue twice

## 6. How to run
1. Import `database/library_db.sql` in MySQL
2. Set username/password in `src/dao/DBConnection.java`
3. Keep mysql connector jar in `lib`
4. Run `run.bat`

## 7. What I learned
- Classes and objects
- Encapsulation (private fields + getters/setters)
- JDBC connection, PreparedStatement, ResultSet
- Foreign keys between tables
- Simple menu driven program flow
