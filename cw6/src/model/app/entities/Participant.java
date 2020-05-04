package model.app.entities;


public class Participant {
    public int getCyclist_id() {
        return cyclist_id;
    }

    public void setCyclist_id(int cyclist_id) {
        this.cyclist_id = cyclist_id;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "cyclist_id=" + cyclist_id +
                ", trip_id=" + trip_id +
                '}';
    }

    public int getTrip_id() {
        return trip_id;
    }

    public Participant(int cyclist_id, int trip_id) {
        this.cyclist_id = cyclist_id;
        this.trip_id = trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    private int cyclist_id;
    private int trip_id;
}
