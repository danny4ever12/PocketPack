package com.mipocket.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import com.mipocket.pocket.DATA_TABLE.TableInfo;
import com.mipocket.pocket.R.drawable;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PasswordEnter extends Activity {

	EditText userName,Pass;

	String temp1,temp2,Tname="",Tpss="";
	DBoperations DB;
	
	String recipient="";
	String usr="",psswrd="";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//file handling of password state		
        try{
			
			File setfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/psswrd.pkt");
		
			System.out.println(setfile.getAbsolutePath());
			if(!setfile.exists()){
				FileOutputStream outp=new FileOutputStream(setfile);
				outp.write(0);
				outp.close();
				startPocket();
			}else{
				FileInputStream inp=new FileInputStream(setfile);
				int n=inp.read();
				inp.close();
				if(n==1)
				  {
					
				  }else{
					  startPocket();
				  }
			}
		 }catch(Exception e){
			System.err.println(e.toString()+"file handling error");
		}
		setContentView(R.layout.activity_password_enter);
		userName=(EditText)findViewById(R.id.enterName);
		Pass=(EditText)findViewById(R.id.enterPass);
        ImageButton startButton=(ImageButton)findViewById(R.id.ok);
        Button mailbtn=(Button)findViewById(R.id.forgtPsswrd);
        
        DB=new DBoperations(this);
        //for menu back button
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
       //checking for username and password match 		
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
	        	  Toast.makeText(this,"user name and password set" , Toast.LENGTH_LONG).show();
	        	  startEmail();
	        	
			  }
			else
			  {
				
		         if ((name.equals(Tname))&&(pss.equals(Tpss)))
		             {
                        	   startPocket();
                        	  
		             }          
                  else
                	  //invalid username or password
                        	 startWrongWay();
				
			  }
		 
	        }
		
	
	
	@SuppressWarnings("deprecation")
	public void startMailSent()
	{
		 
		
		//fetching recovery mail
		try{	
			File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recoverymail.pkt");
			System.out.println(setFile.getAbsolutePath());
			if(setFile.exists())
			   {
				 FileInputStream fin=new FileInputStream(setFile);
                 InputStreamReader isr = new InputStreamReader(fin);
                 BufferedReader bufferedReader = new BufferedReader(isr);
                 String receiveString = "";
                 receiveString = bufferedReader.readLine();   
                
                 recipient = receiveString.toString();  
                 Log.e("danny",recipient);
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
		
			 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
		 
					// set title
					alertDialogBuilder.setTitle("Confirm");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("username and password will be sent to your mail")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								 try {   
									 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

					            	 StrictMode.setThreadPolicy(policy); 
					                 GMailSender sender = new GMailSender("mipocketapp@gmail.com", "He@ven12!");
					                 sender.sendMail("mi pocket",   
					                         "your username is "+usr+"\npassword is "+psswrd+"",
					                         "mipocketapp@gmail.com",
					                         recipient);
					                 gotoYes();
					             } catch (Exception e) {
					            	

					            	 gotoNo();
					            	 
					                 Log.e("SendMail", e.getMessage(), e);   
					             } 
							      	
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
			
			
			
			
			
			
		 
		
		}  
	 } 
		   
	public void gotoYes()
	{
		Toast.makeText(this, "mail sent", Toast.LENGTH_SHORT).show();
	}
	public void gotoNo()
	{
		Toast.makeText(this," mail not sent ", Toast.LENGTH_SHORT).show();
	}
	
		public void startEmail()
	{
			
		try{
					
			 File setfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recoverymail.pkt");
				
			 System.out.println(setfile.getAbsolutePath());
			 if(!setfile.exists())
			  {	
		         Intent LaunchEmail=new Intent(this,EmailEnter.class);
		         startActivity(LaunchEmail);
		         finish();
			  }
			 else
			 
				 startPocket();
		}catch(Exception e){
			 Intent LaunchEmail=new Intent(this,EmailEnter.class);
	         startActivity(LaunchEmail);
	         finish();
		}
			 
	}
	public void startPocket(){
		Intent LaunchPocket=new Intent(this,Pocket.class);
		startActivity(LaunchPocket);
		finish();
	}

	public void startWrongWay()
	{   
		Intent LaunchError=new Intent(this,Error.class);
		startActivity(LaunchError);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
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
        
        default:
            return super.onOptionsItemSelected(item);
        }
    
	}	


	

}
