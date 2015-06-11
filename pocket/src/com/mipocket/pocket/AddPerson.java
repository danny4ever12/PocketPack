package com.mipocket.pocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mipocket.pocket.R.drawable;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddPerson extends Activity {

	EditText name,give,borrow;
	String pname;
	Context ctx= this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_person);
        name=(EditText)findViewById(R.id.name1);
        give=(EditText)findViewById(R.id.give1);
        borrow=(EditText)findViewById(R.id.borrow1);
        
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		
        Button startButton=(Button)findViewById(R.id.done1);
        startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    startDetails();	
			}
		});
		
	}

	@SuppressLint("SimpleDateFormat")
	public void startDetails()
	{
		pname=name.getText().toString();
	    float gval=Float.valueOf(give.getText().toString());
		float bval=Float.valueOf(borrow.getText().toString());
		float rval= gval-bval;
	    float tmp=-rval;
		DBoperations DB=new DBoperations(ctx);
		String DateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		DB.putInformation(DB, pname, rval, DateandTime);
		DB.putDaily(DB, pname, tmp, DateandTime);
		Toast.makeText(getBaseContext(),"person added", Toast.LENGTH_LONG).show();
		startMoneyTab();
		
	
	}
	
	public void startMoneyTab()
	{
		Intent launchActivity=new Intent(this,MoneyTab.class);
		startActivity(launchActivity);
		finish();
	}
	 public void goSettings()
	 {
		 Intent launch=new Intent(this,Settings.class);
	    	startActivity(launch);
	    	finish();
	 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_me, menu);
		return true;
			}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        case R.id.action_settings:
        	goSettings();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    
	}	


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_person,
					container, false);
			return rootView;
		}
	}

}
