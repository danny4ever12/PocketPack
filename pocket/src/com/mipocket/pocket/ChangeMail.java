package com.mipocket.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.mipocket.pocket.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeMail extends ActionBarActivity {
	
	EditText t;
	Editable pt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_mail);
		t=(EditText)findViewById(R.id.changemailEdit);
		pt=t.getText();
		Button start=(Button)findViewById(R.id.ChangeMailStart);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startChange();
			}
		});
	}

	public void startChange()
	{    
	try{	
		 File rFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recoverymail.pkt");
	     System.out.println(rFile.getAbsolutePath());
	     
	     FileInputStream fin=new FileInputStream(rFile);
         InputStreamReader isr = new InputStreamReader(fin);
         BufferedReader bufferedReader = new BufferedReader(isr);
         String receiveString1 = "";
                
         receiveString1 = bufferedReader.readLine();     
         String temp = receiveString1.toString();        
         fin.close();
                
         if (temp.equals(pt.toString()))
        	 {
        	   startEmailEnter();
             }
         else
        	 Toast.makeText(this, "Wrong recovery mail!Enter the correct one...", Toast.LENGTH_LONG).show();
	  }catch(Exception e){
			Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
	  }
	}
	public void startEmailEnter(){
		Intent LaunchMail=new Intent(this,EmailEnter.class);
		startActivity(LaunchMail);
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
