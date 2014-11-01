package com.example.parent_digitalleash;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	EditText latitudeEditTextField;
	EditText longitudeEditTextField;
	EditText radiusEditTextField;
	EditText usernameEditTextField;
	Button createNewUserButton;
	TextView confirmTextViewLabel;
	
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
        radiusEditTextField = (EditText) findViewById(R.id.radiusEditTextField);
        usernameEditTextField = (EditText) findViewById(R.id.usernameEditTextField);
        createNewUserButton = (Button) findViewById(R.id.createNewUserbutton);
        
        this.startUpdatingLocations();
        createNewUserButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if (usernameEditTextField.getText().length() < 2) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
		            builder1.setMessage("Username is too short - please input valid username");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("OK",
		                    new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });
		            AlertDialog alert11 = builder1.create();
		            alert11.show();
				}
				else if (radiusEditTextField.getText().length() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
		            builder1.setMessage("radius doesn't exist - please input valid radius");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("OK",
		                    new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });
		            AlertDialog alert11 = builder1.create();
		            alert11.show();
				}
				else {
					new PostRequestAsyncTask().execute(); 
				}
			}
		});  
    }
    
    public void startUpdatingLocations() {
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

    class PostRequestAsyncTask extends AsyncTask<Void,Void,Void>
    {
              protected void onPreExecute()
              {           super.onPreExecute();
                        //display progress dialog.
              } 
              protected Void doInBackground(Void ...params)
              {  
                //http request. do not update ui here 
           		JSONObject jsonobj;
           		JSONObject user;
           		try {
           	        user = new JSONObject();
           	        user.put("username", usernameEditTextField.getText());
           	        user.put("latitude", latitudeEditTextField.getText());
           	        user.put("longitude", longitudeEditTextField.getText());
           	        user.put("radius", radiusEditTextField.getText());
           	        jsonobj = new JSONObject();
           	        jsonobj.put("user", user);
           	        jsonobj.put("commit", "Create User");
           	        jsonobj.put("action", "update");
           	        jsonobj.put("controller","users");
           			HttpClient httpClient = new DefaultHttpClient();
           	        HttpPost httpPost = new HttpPost("http://protected-wildwood-8664.herokuapp.com/users");
           	        StringEntity se = new StringEntity(jsonobj.toString());
           	        se.setContentType("application/json;charset=UTF-8");
           	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
           	        httpPost.setEntity(se);
           	        HttpResponse httpresponse = httpClient.execute(httpPost);
           	        String responseText = null;
           	        try {
           	        	responseText = EntityUtils.toString(httpresponse.getEntity());
           	        }catch (ParseException e) {
           	        	e.printStackTrace();
           	        	Log.i("Parse Exception", e + "");
           	        }
           	        
           		} catch (JSONException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		} catch (UnsupportedEncodingException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		} catch (ClientProtocolException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		} catch (IOException e) {
           			// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
                    return null;
              } 
               protected void onPostExecute(Void result)
              {     
                        super.onPostExecute(result);
                        confirmTextViewLabel.setText("allright allright allrightttt post successful");
                        Toast.makeText(MainActivity.this,
                        	    "onPostExecute", Toast.LENGTH_LONG).show();
    			        Log.d("TERRY","post request successful");

              } 
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
