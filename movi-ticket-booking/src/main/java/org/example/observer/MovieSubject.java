package org.example.observer;

import org.example.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public abstract class MovieSubject {
    private  final List<MovieObserver> observers = new ArrayList<>();

    public void addObserver(MovieObserver movieObserver){
        observers.add(movieObserver);
    }

    public void removeObservers(MovieObserver movieObserver){
        observers.remove(movieObserver);
    }

    public void notifyObservers(){
        for(MovieObserver movieObserver: observers){
            movieObserver.update((Movie) this);
        }
    }
}
