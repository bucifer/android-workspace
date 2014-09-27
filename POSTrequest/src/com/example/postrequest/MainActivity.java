package com.example.postrequest;

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
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button myButton = (Button) findViewById(R.id.button1);
        myButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("TERRY", "hi click");
		        new TheTask().execute(); 
			}
		});  
        
        //Android will flip out and throw Networkonmainthreadexception if you try to do a HTTP request in the Main thread
        //or inside onCreate method. make sure to use AsyncTask
        
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
    
    class TheTask extends AsyncTask<Void,Void,Void>
    {
              protected void onPreExecute()
              {           super.onPreExecute();
                        //display progressdialog.
              } 

               protected Void doInBackground(Void ...params)
              {  
                    //http request. do not update ui here
                   

           		JSONObject jsonobj;
           		JSONObject user;
           		try {
           	        user = new JSONObject();
           	        user.put("username", "hamada_masatoshi");
           	        user.put("latitude", "11");
           	        user.put("longitude", "11");
           	        user.put("radius", "11");
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

              } 

    }
    
    
}
