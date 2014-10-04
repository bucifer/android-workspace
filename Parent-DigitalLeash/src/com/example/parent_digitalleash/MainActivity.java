package com.example.parent_digitalleash;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

	EditText latitudeEditTextField;
	EditText longitudeEditTextField;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* 
         1) First step - as soon as the app loads, we are loading user current location GPS and displaying on textfields
         2) Then we put button functionalities - Create New User
         	- It's a POST request to the server with longitude, latitude, and whatever user inputs for name/radius
         3) Then we implement Child App that updates current location to the server
         4) Then we implement "Check Location" in Parent App that checks for location
         */
        
        latitudeEditTextField = (EditText) findViewById(R.id.latitudeEditTextField);
        longitudeEditTextField = (EditText) findViewById(R.id.longitudeEditTextField);
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
						
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
		        Log.d("TERRY","Latitude: " + location.getLatitude() + "/n Longitude: " + location.getLongitude());
		        latitudeEditTextField.setText(String.valueOf(location.getLatitude()));
		        longitudeEditTextField.setText(String.valueOf(location.getLongitude()));
			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
		};
        
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
