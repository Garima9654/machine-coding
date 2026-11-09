package org.example.service;

import org.example.entity.BookingPolicy;
import org.example.entity.Member;
import org.example.entity.MemberType;
import org.example.repository.MemberRepo;

import java.util.Objects;

public class MemberService {

    private MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo){
        this.memberRepo= memberRepo;
    }

    public String addMember(String username, String mobileNo, MemberType memberType){
        Member member= memberRepo.findMember(username);
        if(!Objects.isNull(member)){
            System.out.println("Member already exists");
            return "";
        }
        return memberRepo.addMember(new Member(username.trim(), mobileNo, memberType));
    }

    public Boolean RemoveMember(String id){
        return memberRepo.removeMember(id);
    }

    public MemberType FindMemberType(String username) {
        return memberRepo.findMember(username).getMemberType();
    }

    public BookingPolicy getPolicy(String username){
        Member member= memberRepo.findMember(username);
        if(Objects.isNull(member)){
            System.out.println("There is no Member");
        }
        return member.getBookingPolicy();
    }
}
