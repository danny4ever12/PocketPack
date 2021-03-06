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

public class Update_Debt extends ActionBarActivity {

	EditText give,borrow;
	Context ctx= this;
	String retDTM,retMSND,getNME;
	Float getval;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update__debt);
		
		Intent k=getIntent();
	    retDTM=k.getStringExtra("DTM");
	    retMSND=k.getStringExtra("MSND");
	    getval=Float.valueOf(retMSND);
	    getNME=k.getStringExtra("MNME");
	     //for menu back button
	    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		
	   
	   
		 give=(EditText)findViewById(R.id.upt1);
	     borrow=(EditText)findViewById(R.id.upt2);
	     Button startBtn=(Button)findViewById(R.id.upbtn);
	     startBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			   startUpdate();
			   
			}
		});
	}

	//for updating persons tabs
	@SuppressLint("SimpleDateFormat")
	public void startUpdate()
	{
		    float gval=Float.valueOf(give.getText().toString());
			float bval=Float.valueOf(borrow.getText().toString());
			float rval= gval-bval;
			getval=getval+rval;
			
			String DateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
			float tmpval=-rval;
			DBoperations DB=new DBoperations(ctx);
			@SuppressWarnings("unused")
			int k=DB.updateDebt(DB, retDTM, getval);
			DB.putDaily(DB,getNME,tmpval,DateandTime);
			Toast.makeText(this, "debt list updated", Toast.LENGTH_LONG).show();
			goMnytb();
			
	}
	public void goMnytb()
	{
		Intent launch=new Intent(this,MoneyTab.class);
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