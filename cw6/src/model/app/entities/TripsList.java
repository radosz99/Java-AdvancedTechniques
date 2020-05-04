package model.app.entities;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="trips")
@XmlAccessorType(XmlAccessType.FIELD)
public class TripsList {
    @XmlElement(name = "trip")
    private List<Trip> trips = null;

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
