package org.example.entities;

import org.example.observer.MovieSubject;

public class Movie extends MovieSubject {
    private final String id;
    private final String title;
    private final Integer durationTime;

    public Movie(String id, String title, Integer durationTime) {
        this.id = id;
        this.title = title;
        this.durationTime = durationTime;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDurationTime() {
        return durationTime;
    }
}
