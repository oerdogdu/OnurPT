package com.eon.atoi.onurpt.POJOs;

/**
 * Created by Atoi on 4.12.2017.
 */

public class Workout {
    private String workoutName, description;
    private String thumbnail;
    private String set, reps;

    public Workout(String workoutName, String thumbnail) {
        this.workoutName = workoutName;
        this.thumbnail = thumbnail;
    }

    public Workout() {}

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
