package org.example.model;

public class Ladder {
    private final Integer stPos;
    private final Integer enPos;

    public Ladder(Integer stPos, Integer enPos) {
        this.enPos = enPos;
        this.stPos = stPos;
    }

    public Integer getEnPos() {
        return enPos;
    }

    public Integer getStPos() {
        return stPos;
    }
}
