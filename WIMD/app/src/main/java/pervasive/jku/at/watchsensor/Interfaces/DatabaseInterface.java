package pervasive.jku.at.watchsensor.Interfaces;

import pervasive.jku.at.watchsensor.Location;

public interface DatabaseInterface {
    public void addLocation(Location location);
    public String getLocation(String mac, int rssi);
}
