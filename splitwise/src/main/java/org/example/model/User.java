package org.example.model;

public class User {
    private final Integer userId;
    private String username;
    private String emailId;
    private String mobileNo;


    public User(Integer userId, String username, String emailId, String mobileNo) {
        this.userId = userId;
        this.username = username;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + emailId + '\'' +
                ", mobileNo'" + mobileNo +
                '}';
    }
}
