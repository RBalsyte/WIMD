package pervasive.jku.at.watchsensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GUI extends Activity {
    private static final String TAG_GUI="gui";
    private static final String TAG_Sensors="sensors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gui);

        final Button button = (Button)findViewById(R.id.serviceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GUI.this, WiFiActivity.class));
                Log.d(TAG_GUI, "locations mapping");
            }
        });

        final Button button2 = (Button)findViewById(R.id.wimdButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GUI.this, WIMDActivity.class));
                Log.d(TAG_Sensors, "on create");
            }
        });
    }
}
