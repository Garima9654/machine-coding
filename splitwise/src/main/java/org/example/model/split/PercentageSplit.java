package org.example.model.split;

import org.example.model.User;

public class PercentageSplit extends Split{
    private Double percent;
    public PercentageSplit(User user, Double percent){
        super(user);
        this.percent= percent;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
