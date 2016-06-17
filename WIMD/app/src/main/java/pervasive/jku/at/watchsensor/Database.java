package pervasive.jku.at.watchsensor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Timestamp;
import java.util.Date;

import pervasive.jku.at.watchsensor.Interfaces.DatabaseInterface;

public class Database extends SQLiteOpenHelper implements DatabaseInterface {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jku_mapping";
    private static final String TABLE_DATA = "DATA";

    // measurements table columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_LOCATION = "LOCATION";
    private static final String KEY_SSID = "SSID";
    private static final String KEY_MAC = "MAC";
    private static final String KEY_RSSI = "RSSI";
    private static final String KEY_TIMESTAMP = "TIMESTAMP";

    /** CONSTRUCTORS */
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** INSTANCE METHODS */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LOCATION + " TEXT,"
                + KEY_SSID + " INTEGER,"
                + KEY_MAC + " TEXT,"
                + KEY_RSSI + " INTEGER,"
                + KEY_TIMESTAMP + " INTEGER)";
        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        // Create tables again
        onCreate(db);
    }

    @Override
    public void addLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        long timestamp = new Timestamp(new Date().getTime()).getTime();

        ContentValues locationValues = new ContentValues();
        locationValues.put(KEY_LOCATION, location.location);
        locationValues.put(KEY_SSID, location.ssid);
        locationValues.put(KEY_MAC, location.mac);
        locationValues.put(KEY_RSSI, location.rssi);
        locationValues.put(KEY_TIMESTAMP, timestamp);

        db.insert(TABLE_DATA, null, locationValues);

        db.close();
    }

    @Override
    public String getLocation(String mac, int rssi) {
        String location = "empty result set";

        SQLiteDatabase db = this.getReadableDatabase();

        //FIXME seems to return an empty String
        String queryString = "SELECT LOCATION FROM DATA WHERE MAC = '"+mac+"' AND RSSI = "+rssi;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToNext()) {
            location = cursor.getString(1);
        }

        cursor.close();
        db.close();
        return location;
    }
}
