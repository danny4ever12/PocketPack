package com.mipocket.pocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mipocket.pocket.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDaily extends ActionBarActivity {
	
	EditText name,give,borrow;
	String pname;
	Context ctx= this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_daily);
		
		 name=(EditText)findViewById(R.id.daily_occtn);
	     give=(EditText)findViewById(R.id.dailygive);
	     borrow=(EditText)findViewById(R.id.dailygot);
	     //for menu back button
	     if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
			{
				ActionBar actionbar=getActionBar();
				actionbar.setDisplayHomeAsUpEnabled(true);
				actionbar.setHomeAsUpIndicator(drawable.action_previous);
			}
			
	     
	        Button startButton=(Button)findViewById(R.id.dlydnebtn);
	        startButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    startDetails();	
				}
			});
	}
	//for adding daily expense transactions
	@SuppressLint("SimpleDateFormat")
	public void startDetails()
	{
		pname=name.getText().toString();
	    float gval=Float.valueOf(give.getText().toString());
		float bval=Float.valueOf(borrow.getText().toString());
		float rval=bval-gval;
	
		DBoperations DB=new DBoperations(ctx);
		String DateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		DB.putDaily(DB, pname, rval, DateandTime);
		Toast.makeText(getBaseContext(),"transaction added", Toast.LENGTH_LONG).show();
		startDaily();
	}
	public void startDaily()
	{
		Intent launch=new Intent(this,DailyExpense.class);
		startActivity(launch);
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
	
}
