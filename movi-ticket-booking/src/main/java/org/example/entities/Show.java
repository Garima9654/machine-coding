package org.example.entities;

import java.time.LocalDateTime;

public class Show {
    private final String id;
    private final LocalDateTime duration;
    private final Movie movie;
    private final Screen screen;

    public Show(String id, LocalDateTime duration, Movie movie, Screen screen) {
        this.id = id;
        this.duration = duration;
        this.movie = movie;
        this.screen = screen;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDuration() {
        return duration;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }
//    private pricing strategy

}
