package com.example.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordEnter extends Activity {

	EditText userName,Pass;
	Editable PuserName,Ppass;
	String temp1,temp2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_enter);
		userName=(EditText)findViewById(R.id.enterName);
		Pass=(EditText)findViewById(R.id.enterPass);
        Button startButton=(Button)findViewById(R.id.ok);
        Button mailbtn=(Button)findViewById(R.id.forgtPsswrd);
        
        PuserName=userName.getText();
        Ppass=Pass.getText();
        
        
        startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startApp();
			}
		});
		
        mailbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    startMailSent();	
			}
		});
	}
	
	public void startApp()
	{
		try{	
			File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/code.pkt");
			System.out.println(setFile.getAbsolutePath());
			if(!setFile.exists()){
				
                  //WRITING DATA TO FILE
                        FileOutputStream fOut = new FileOutputStream(setFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append(PuserName.toString());
                        myOutWriter.append("\n");
                        myOutWriter.append(Ppass.toString());
                        myOutWriter.close();
                        fOut.close();
				        startEmail();
			  }
			else
			  {
				
		
                   //READING DATA FROM FILE
                         FileInputStream fin=new FileInputStream(setFile);
                         InputStreamReader isr = new InputStreamReader(fin);
                         BufferedReader bufferedReader = new BufferedReader(isr);
                         String receiveString1 = "";
                         String receiveString2 = "";
                                
     
                         receiveString1 = bufferedReader.readLine();     
                         temp1 = receiveString1.toString();        
                         receiveString2 = bufferedReader.readLine();         
                         temp2=receiveString2.toString();
                         fin.close();
                                
                         if ((temp1.equals(PuserName.toString()))&&(temp2.equals(Ppass.toString())))
                             {
                        	   startPocket();
                             }
                         else
                        	 startWrongWay();
				
			  }
		   }catch(Exception e){
				Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
			}	
			
		
	}
	
	public void startMailSent()
	{
		String recipient="";
		String usr="",psswrd="";
		
		//fetching recovery mail
		try{	
			File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recoverymail.pkt");
			System.out.println(setFile.getAbsolutePath());
			if(!setFile.exists())
			   {
				 FileInputStream fin=new FileInputStream(setFile);
                 InputStreamReader isr = new InputStreamReader(fin);
                 BufferedReader bufferedReader = new BufferedReader(isr);
                 String receiveString = "";
                 receiveString = bufferedReader.readLine();     
                 recipient = receiveString.toString();        
                 fin.close();
				
			   }
			}catch(Exception e){
				Toast.makeText(this, "You have not provided a valid email address to sent backup information! ", Toast.LENGTH_LONG).show();
			}
		
		//fetching username and password
		try{	
			  File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/code.pkt");
			  System.out.println(setFile.getAbsolutePath());
			
			  FileInputStream fin=new FileInputStream(setFile);
            InputStreamReader isr = new InputStreamReader(fin);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String receiveString1 = "";
            String receiveString2 = "";
               
            receiveString1 = bufferedReader.readLine();     
            usr = receiveString1.toString();        
            receiveString2 = bufferedReader.readLine();         
            psswrd=receiveString2.toString();
            fin.close();
		}catch(Exception e){
	          Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
        }	
			
		
		 
		   String subject="Pocket app";
		  // String body="your user";
		   Intent i = new Intent(Intent.ACTION_SEND);
		   i.setType("message/rfc822");
		   i.putExtra(Intent.EXTRA_EMAIL  , recipient);
		   i.putExtra(Intent.EXTRA_SUBJECT, subject);
		   i.putExtra(Intent.EXTRA_TEXT   , "your username is "+usr+"\npassword is "+psswrd);
		   try {
		      startActivity(Intent.createChooser(i, "send mail"));
		    } catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(PasswordEnter.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		    }
		   
		   
		   
	}
	
	public void startEmail()
	{
		Intent LaunchEmail=new Intent(this,EmailEnter.class);
		startActivity(LaunchEmail);
	}
	public void startPocket(){
		Intent LaunchPocket=new Intent(this,Pocket.class);
		startActivity(LaunchPocket);
	}

	public void startWrongWay()
	{   
		Intent LaunchError=new Intent(this,Error.class);
		startActivity(LaunchError);
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_enter, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_password_enter,
					container, false);
			return rootView;
		}
	}

}
