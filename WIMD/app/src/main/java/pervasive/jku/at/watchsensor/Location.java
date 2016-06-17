package pervasive.jku.at.watchsensor;

public class Location {
    String location;
    int ssid;
    String mac;
    int rssi;

    Location(String location, int ssid, String mac, int rssi) {
        this.location = location;
        this.ssid = ssid;
        this.mac = mac;
        this.rssi = rssi;
    }
}
