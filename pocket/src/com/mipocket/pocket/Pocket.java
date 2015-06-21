package com.mipocket.pocket;



import com.mipocket.pocket.R.drawable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.appszoom.appszoomsdk.AppsZoom;

public class Pocket extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pocket);
		
		AppsZoom.start(this);
		AppsZoom.showAd(this);
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		

		ImageButton addPerson=(ImageButton)findViewById(R.id.newPerson);
		addPerson.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAddPerson();
			}
		});
		ImageButton mnyTab=(ImageButton)findViewById(R.id.mTab);
		
		mnyTab.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMnyTab();
			}
		});
		ImageButton know=(ImageButton)findViewById(R.id.knowMe);
		know.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startKnowMe();
			}
		});
		ImageButton sett=(ImageButton)findViewById(R.id.Setti);
		sett.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSettings();
			}
		});
		
		ImageButton xpens=(ImageButton)findViewById(R.id.DailyXpenseStart);
		xpens.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startXpens();
			}
		});
		
	}
	
	

	public void startAddPerson()
	{
		Intent launchAddPerson=new Intent(this,AddPerson.class);
		startActivity(launchAddPerson);
	}
	public void startMnyTab()
	{
		Intent launchMnyTab=new Intent(this,MoneyTab.class);
		startActivity(launchMnyTab);
	}
	public void startKnowMe()
	{
		Intent launchKnowMe=new Intent(this,AboutMe.class);
		startActivity(launchKnowMe);
	}
	public void startSettings()
	{
		Intent launchSettings=new Intent(this,Settings.class);
		startActivity(launchSettings);
	}
	public void startXpens()
	{
		Intent launchDaily=new Intent(this,DailyExpense.class);
		startActivity(launchDaily);
	}

	 public void startMeConnect()
	    {
	      Intent launchActivity=new Intent(this,AccountNo.class);
	  	  startActivity(launchActivity);
	    }
	
	 public void goSettings()
	 {
		 Intent launch=new Intent(this,Settings.class);
	    	startActivity(launch);
	    	
	 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.go_connect, menu);
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
        
        case R.menu.go_connect:
        	  startMeConnect();
        	  return true; 
        case R.id.action_settings:
        	goSettings();
        	return true;	  
        	
        default:
            return super.onOptionsItemSelected(item);
        }
    
	}	

	

}
