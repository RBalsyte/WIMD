package pervasive.jku.at.watchsensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.lang.Thread;
import java.lang.Runnable;

public class WIMDActivity extends ActionBarActivity {

    private Sensor mSensor;
    private SensorManager mSensorManager;

    private static final String TAG_REG="reg";
    private static final String TAG_SEN="sen";
    private static final String TAG_OTH="oth";

    private Database db;
    private WebserviceCaller ws;
    private int count = 0;
    private TextView tv;
    private WiFiActivity wa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wimd);

        tv = (TextView)findViewById(R.id.message);

        wa = new WiFiActivity();
        ws = new WebserviceCaller();
        db = new Database(this);

        new Thread() {
            public void run() {
                while(true) {
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                showPartnerLocation();
                                sendLocationToWebservice();
                            }
                        });
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void showPartnerLocation() {
        String location = ws.getLocation() + count++;

        tv.setText(location);
    }

    private void sendLocationToWebservice() {
        String mac = "44:e4:d9:ab:42:b0"; //TODO get mac from wifi service
        int rssi = -88; //TODO get rssi from wifi service

        String location = "---";

        location = db.getLocation(mac, rssi);
        tv.setText(mac); //TODO for db testing - delete

        ws.setLocation(location);
    }

}
