package com.example.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.content.Intent;
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_mail, menu);
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
