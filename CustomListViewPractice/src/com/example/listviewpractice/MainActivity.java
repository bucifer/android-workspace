package com.example.listviewpractice;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //ArrayAdapter takes the string array and adapts it to be laid out for our listview
        setListAdapter(new MyAdapter(this, 
        		android.R.layout.simple_list_item_1, R.id.textView1, 
        		getResources().getStringArray(R.array.companies)));
        
        //this is a simple default style for listview, simple list item 1.
        //you can create your custom cells too, your custom layout
        		
    }

    
    //We subclass ArrayAdapter to make our own custom rows
    private class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int resource, int textViewResourceId,
				String[] strings) {
			super(context, resource, textViewResourceId, strings);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		//called for every row in the list
		//when this is called, we get the option to modify every row
		//it's like cellForRowATIndexPath
		
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			//represents a single row
			View row = inflater.inflate(R.layout.list_item, parent, false);
			String[] items = getResources().getStringArray(R.array.companies);
			
			ImageView iv = (ImageView) row.findViewById(R.id.imageView1);
			TextView tv = (TextView) row.findViewById(R.id.textView1);
			
			//position property is what you use to use as index
			tv.setText(items[position]);
			
			if (items[position].equals("Apple")  || items[position].equals("Google") ) {
				iv.setImageResource(R.drawable.america);
			}
			else if (items[position].equals("Samsung")  || items[position].equals("LG") ) {
				iv.setImageResource(R.drawable.korea);
			}
			
			return row;
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
