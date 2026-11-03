package model;

import java.sql.Date;

// One row from issue_records (book given to a member)
public class IssueRecord {
    private int issueId;
    private int bookId;
    private int memberId;
    private String bookTitle;
    private String memberName;
    private Date issueDate;
    private Date returnDate;
    private String status;

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return issueId + " | Book: " + bookTitle + " (" + bookId + ") | Member: " + memberName
                + " (" + memberId + ") | Issued: " + issueDate + " | Returned: "
                + (returnDate == null ? "-" : returnDate) + " | " + status;
    }
}
