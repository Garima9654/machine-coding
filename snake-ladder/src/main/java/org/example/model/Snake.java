package org.example.model;

public class Snake {
    private final Integer stPos;
    private final Integer enPos;

    public Snake(Integer stPos, Integer enPos) {
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
