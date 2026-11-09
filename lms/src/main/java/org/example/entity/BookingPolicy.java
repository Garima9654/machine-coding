package org.example.entity;

import java.util.Map;

public class BookingPolicy {
    private final Integer issueCapacity;
    private final Integer fineAmountPerDay;
    private final Integer daysLimit;
    private final Integer finePenaltyAmountPerDay;
    private BookingPolicy(Integer issueCapacity, Integer fineAmountPerDay, Integer daysLimit, Integer finePenaltyAmountPerDay){
        this.issueCapacity= issueCapacity;
        this.fineAmountPerDay= fineAmountPerDay;
        this.daysLimit = daysLimit;
        this.finePenaltyAmountPerDay = finePenaltyAmountPerDay;
    }

    private static final Map<MemberType, BookingPolicy> POLICIES = Map.of(
            MemberType.FACULTY, new BookingPolicy(5, 1, 90, 1),
            MemberType.STUDENT, new BookingPolicy(3, 1, 30, 2)
    );

    public static BookingPolicy getPolicy(MemberType memberType){
        return POLICIES.get(memberType);
    }

    public Integer getDaysLimit(){
        return this.daysLimit;
    }

    public Integer getFineAmountPerDay(){
        return this.fineAmountPerDay;
    }

    public Integer getIssueCapacity(){
        return this.issueCapacity;
    }

    public Integer getFinePenaltyAmountPerDay() {
        return finePenaltyAmountPerDay;
    }
}
