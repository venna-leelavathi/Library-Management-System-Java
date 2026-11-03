# Library Management System (Java + MySQL)

Beginner internship mini project. Console application to manage books, students/members, and book issue-return records using Java OOP and JDBC.

## Tech used
- Java
- OOP (classes, objects, encapsulation)
- JDBC
- MySQL

## Project structure
```
src/
  LibraryApp.java          main menu
  model/                   Book, Member, IssueRecord
  dao/                     DBConnection, BookDAO, MemberDAO, IssueDAO
  util/                    InputHelper
database/
  library_db.sql           tables + sample data
lib/                       put mysql-connector jar here
```

## Week 1 (Java + OOP)
- Book module: add, view, search, update, delete, check availability
- Member module: add, view, search, update
- Menu flow in `LibraryApp`

## Week 2 (JDBC + MySQL)
- Connect Java to MySQL (`dao/DBConnection.java`)
- Store and read records from tables
- Issue book / return book
- Track currently issued books
- View available books (`quantity > 0`)
- Simple validation and error messages

## Setup

1. Install JDK and MySQL.
2. Open MySQL and run `database/library_db.sql`.
3. Open `src/dao/DBConnection.java` and set your MySQL username and password.
4. Download **MySQL Connector/J** jar and copy it into the `lib` folder.
5. Compile and run (Windows):

```
run.bat
```

Or manually:

```
javac -cp "lib/*" -d bin src/LibraryApp.java src/model/*.java src/dao/*.java src/util/*.java
java -cp "bin;lib/*" LibraryApp
```

On Linux/Mac use `:` instead of `;` in the classpath.

## How to demo
1. Book Menu → View Books (sample books should show)
2. Member Menu → Add Member
3. Issue / Return → Issue Book (use a book id and member id)
4. Track Issued Books
5. Return Book using the issue id
6. Check Availability / View Available Books

## Submission files
- Java source code in `src/`
- SQL file in `database/library_db.sql`
- This README
- `PROJECT_REPORT.md`
- Screenshots: run the app and save console photos in a `screenshots` folder
