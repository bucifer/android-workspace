package com.example.getrequest;

import com.android.volley.*;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RequestQueue mRequestQueue;

     // Instantiate the cache
     Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

     // Set up the network to use HttpURLConnection as the HTTP client.
     Network network = new BasicNetwork(new HurlStack());

     // Instantiate the RequestQueue with the cache and network.
     mRequestQueue = new RequestQueue(cache, network);

     // Start the queue
     mRequestQueue.start();
        
        final ImageView myImageView;
        String url = "https://www.google.com/images/srpr/logo11w.png";
        myImageView = (ImageView) findViewById(R.id.imageView1);
     // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				// TODO Auto-generated method stub
	            myImageView.setImageBitmap(response);
			}
		}, 0, 0, null, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
     // Access the RequestQueue through your singleton class.
        mRequestQueue.add(request);
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
