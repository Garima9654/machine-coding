package org.example.entity;

import java.util.UUID;

public class Member {
    private final String id;
    private String userName;
    private String mobileNo;
    private final MemberType memberType;

    public Member(String userName, String mobileNo, MemberType memberType){
        this.userName= userName;
        this.mobileNo= mobileNo;
        this.memberType= memberType;
        this.id = UUID.randomUUID().toString();
    }

    public BookingPolicy getBookingPolicy(){
        return BookingPolicy.getPolicy(memberType);
    }

    public String getUserName(){
        return this.userName;
    }

    public MemberType getMemberType(){
        return this.memberType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getId(){ return this.id; }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", memberType=" + memberType +
                '}';
    }
}
