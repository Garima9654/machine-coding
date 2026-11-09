package org.example.repository;

import org.example.entity.Book;
import org.example.entity.BookCopy;
import org.example.entity.Member;
import org.example.entity.MemberType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepo {
    IssueBookRepo issueBookRepo;

    Map<String, Member> memberMap;

    public MemberRepo(IssueBookRepo issueBookRepo){
        memberMap= new HashMap<>();
        this.issueBookRepo= issueBookRepo;
    }

    public Member findMember(String id){
        return memberMap.get(id);
    }

    public Member findByMobileNo(String mobileNo){
        return memberMap.values().stream().filter(m->m.getMobileNo().equals(mobileNo)).findAny().orElse(null);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(memberMap.values());
    }

    public String addMember(Member member){
        memberMap.put(member.getId(), member);
        return member.getId();
    }

    public Boolean removeMember(String id){
        issueBookRepo.rejectIssues(id);
        return memberMap.remove(id)!=null;
    }

    public Member findById(String id){
        return memberMap.get(id);
    }

}
