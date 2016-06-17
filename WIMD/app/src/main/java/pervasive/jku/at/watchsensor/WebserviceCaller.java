package pervasive.jku.at.watchsensor;

import pervasive.jku.at.watchsensor.Interfaces.WebserviceCallerInterface;

public class WebserviceCaller implements WebserviceCallerInterface {

    public WebserviceCaller() {
        //TODO connect to webservice
    }

    @Override
    public void setLocation(String location) {
        //TODO add location with timestamp
    }

    @Override
    public String getLocation() {
        int timestamp = 0; //TODO get timestamp
        String location = ""; //TODO get location

        if(location==null) return "Unknown location";
        else if(timestamp>100000) return "Your partner is probably dead."; //TODO adapt 100000 to a suitable time
        else if(timestamp>60000) return "There currently is no connection.";
        else return "Your partner is at " + location;
    }
}
