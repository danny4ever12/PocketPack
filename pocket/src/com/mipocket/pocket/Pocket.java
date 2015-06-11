package com.mipocket.pocket;



import com.mipocket.pocket.R.drawable;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Pocket extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pocket);
		
		
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		

		Button addPerson=(Button)findViewById(R.id.newPerson);
		addPerson.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startAddPerson();
			}
		});
		Button mnyTab=(Button)findViewById(R.id.mTab);
		
		mnyTab.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMnyTab();
			}
		});
		Button know=(Button)findViewById(R.id.knowMe);
		know.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startKnowMe();
			}
		});
		Button sett=(Button)findViewById(R.id.Setti);
		sett.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSettings();
			}
		});
		
		Button xpens=(Button)findViewById(R.id.DailyXpenseStart);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_pocket,
					container, false);
			return rootView;
		}
	}

}
