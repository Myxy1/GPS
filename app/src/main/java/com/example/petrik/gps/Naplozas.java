package com.example.petrik.gps;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Naplozas {

    public static void kiiras(float longitude, float latitude) throws IOException
    {
        String state;
        File file;
        String szoveges_adat;
        Date c = Calendar.getInstance().getTime();
        //kiíratás ==> Current time => Thu Mar 29 04:07:06 CEST 2018
        //System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String formattedDate = df.format(c);

        szoveges_adat= String.valueOf(longitude) + "," + String.valueOf(latitude) + "," + formattedDate + "," +  "\r\n";

        state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))
        {
            file = new File(Environment.getExternalStorageDirectory(),"gps_track.csv");
            try
            {
                BufferedWriter out = new BufferedWriter(new FileWriter(file,true),1024);
                out.append(szoveges_adat);
                out.close();
            }
            catch (IOException e)
            {

            }
        }
    }
}
