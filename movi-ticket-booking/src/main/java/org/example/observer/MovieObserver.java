package org.example.observer;

import org.example.entities.Movie;

public interface MovieObserver {
    void update(Movie movie);
}
