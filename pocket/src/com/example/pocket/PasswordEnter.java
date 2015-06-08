package com.example.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.example.pocket.DATA_TABLE.TableInfo;
import com.example.pocket.R.drawable;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
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

	String temp1,temp2,Tname="",Tpss="";
	DBoperations DB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_enter);
		userName=(EditText)findViewById(R.id.enterName);
		Pass=(EditText)findViewById(R.id.enterPass);
        Button startButton=(Button)findViewById(R.id.ok);
        Button mailbtn=(Button)findViewById(R.id.forgtPsswrd);
        
        DB=new DBoperations(this);
        
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		
        
        
        
        
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
	
	@SuppressWarnings("deprecation")
	public void startApp()
	{
		
		Tname=userName.getText().toString();
        Tpss=Pass.getText().toString();
		String name="",pss="";
		Cursor CR=DB.getUSRPSS(DB);
		startManagingCursor(CR);
	   try{ 
		CR.moveToFirst();
	        	
	    name = CR.getString(CR.getColumnIndex(TableInfo.NAME));
	    pss = CR.getString(CR.getColumnIndex(TableInfo.PASSWORD));
	    
	   }catch(Exception e){
		   name="";pss="";
		   DB.putNamePass(DB, name, pss);
	   }
				        
	          if(name.equals("")||pss.equals(""))
	          {
	        	  @SuppressWarnings("unused")
				int k=DB.deleteUSRPSS(DB);
	        	  DB.putNamePass(DB, Tname, Tpss);
	        		  startEmail();
			  }
			else
			  {
				
		         if ((name.equals(Tname))&&(pss.equals(Tpss)))
                             
                        	   startPocket();
                             
                         else
                        	 startWrongWay();
				
			  }
		 
	        }
		
	
	
	@SuppressWarnings("deprecation")
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
		
		Cursor CR=DB.getUSRPSS(DB);
		   startManagingCursor(CR);
	    try{
		    CR.moveToFirst();
	        usr = CR.getString(CR.getColumnIndex(TableInfo.NAME));
	        psswrd = CR.getString(CR.getColumnIndex(TableInfo.PASSWORD));
	       }catch(Exception e){
	    	 String T1="",T2="";
			 DB.putNamePass(DB, T1, T2);
	       }
	    
		
		if(usr.equals("")||psswrd.equals(""))
		{
	          Toast.makeText(this, "user name and password not provided", Toast.LENGTH_LONG).show();
	          
        }	
		else{	
		
		 
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
		switch (item.getItemId()) {
        case android.R.id.home:
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        case R.id.action_settings:
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
			View rootView = inflater.inflate(R.layout.fragment_password_enter,
					container, false);
			return rootView;
		}
	}

}
