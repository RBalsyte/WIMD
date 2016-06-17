package pervasive.jku.at.watchsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends ActionBarActivity implements SensorEventListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;

    private static final String TAG_REG="reg";
    private static final String TAG_SEN="sen";
    private static final String TAG_OTH="oth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        SensorManager mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        final List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        final List<String> sensorNames=new ArrayList<String>(deviceSensors.size());
        for(final Sensor sensor:deviceSensors) {
            sensorNames.add(sensor.getName());
        }
        StringBuffer sb=new StringBuffer();
        GridView gridView=(GridView)findViewById(R.id.sensors);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sensorNames);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                Sensor mySensor = deviceSensors.get(position);
                unregister();
                register(mySensor);
            }
        });
        Log.d(TAG_OTH, "on create");
    }

    void unregister(){
        if(mSensor!=null) {
            Log.d(TAG_REG, "unregistering " + mSensor.getName());
            ((SensorManager) getSystemService(SENSOR_SERVICE)).unregisterListener(this);
        }
    }

    void register(Sensor newSensor){
        Log.d(TAG_REG,"registering " + newSensor.getName());
        mSensor=((SensorManager)getSystemService(SENSOR_SERVICE)).getDefaultSensor(newSensor.getType());
        ((SensorManager)getSystemService(SENSOR_SERVICE)).registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG_SEN,"accuracy changed of " + sensor.getName() + " to "+ accuracy);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        Log.d(TAG_SEN, "sensor event received from " + event.sensor.getName());
        ((TextView)findViewById(R.id.sensorName)).setText(event.sensor.getName());
        TextView tc=(TextView)findViewById(R.id.sensorContent);
        StringBuffer sb=new StringBuffer();
        for(float data:event.values) {
            sb.append(data+",");
        }
        tc.setText(sb.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG_OTH, "on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG_OTH, "on pause");
    }
}
