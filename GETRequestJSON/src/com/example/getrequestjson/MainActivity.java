package com.example.getrequestjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {	
	
	EditText etResponse;
	private ProgressDialog simpleWaitDialog;
	EditText usernameEt;
	EditText latitudeEt;
	EditText longitudeEt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etResponse = (EditText) findViewById(R.id.terryEditText1);
        TextView tv = (TextView) findViewById(R.id.connectedTv);
        
        usernameEt = (EditText) findViewById(R.id.editText1);
        latitudeEt = (EditText) findViewById(R.id.editText3);
        longitudeEt = (EditText) findViewById(R.id.editText2);
        
        if (isConnected()) {
        	tv.setBackgroundColor(0xFF00CC00);
        	tv.setText("You are connected");
        }
        else{
            tv.setText("You are NOT conncted");
        }
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://protected-wildwood-8664.herokuapp.com/users/hamada_masatoshi.json");
    }

    
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
    
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
    	@Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
             
        protected void onPreExecute() {
        	Log.i("Async-Example", "onPreExecute Called");
			simpleWaitDialog = ProgressDialog.show(MainActivity.this,
					"Wait", "Downloading JSON Data");
        }
                
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
			simpleWaitDialog.dismiss();
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            JSONObject json;
            try {
				json = new JSONObject(result);
	            etResponse.setText(json.toString(1));
	           	String username = json.getString("username");
	           	Log.i("TERRY", username);
	            usernameEt.setText(username);
	            latitudeEt.setText(json.getString("latitude"));
	            longitudeEt.setText(json.getString("longitude"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
    }
    
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
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
