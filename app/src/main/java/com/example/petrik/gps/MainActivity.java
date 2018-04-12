package com.example.petrik.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mezo;
    private String szoveg = null;
    private Timer myTimer;
    private TimerTask timerTask;
    private float longitude;        //hosszúság
    private float latitude;         //szélességi kör
    private Naplozas naplo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //naplózó osztály példányosítása
        Naplozas naplo = new Naplozas();

        //szöveges mező inicializálása
        mezo = (TextView) findViewById(R.id.szoveg);

        //helymeghatározás előkészítése
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Koordináták lekérdezése
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //aktiválás
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        myTimer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        };
        myTimer.schedule(timerTask,500,5000);
    }

    //időzítő keresztfüggvény
    public void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    //időzítő műveletei
    public Runnable Timer_Tick = new Runnable() {
        @Override
        public void run() {
            mezo.setText("Lat:" + Float.toString(latitude) +
                         "\r\nLong:" + Float.toString(longitude));
            try
            {
                naplo.kiiras(longitude,latitude);
                Toast.makeText(MainActivity.this, "Új koordináta feljegyezve!", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    };
}
