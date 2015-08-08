package com.mipocket.pocket;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

public class Settings extends ListActivity {
	//settings options
	static final String[] settings_list=new String[]{
		"Change username and password",
		"Change recovery mail"
		
	};
	Switch sw1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		sw1=(Switch)findViewById(R.id.pswrdon1);
		sw1.setTextSize(18);
		
		//file handling of switch state	->password protection	
        try{
			
			File setfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/psswrd.pkt");
		
			System.out.println(setfile.getAbsolutePath());
			if(!setfile.exists()){
				FileOutputStream outp=new FileOutputStream(setfile);
				outp.write(0);
				outp.close();
			}else{
				FileInputStream inp=new FileInputStream(setfile);
				int n=inp.read();
				inp.close();
				if(n==1)
				  {
					sw1.setChecked(true);
				  }else{
					  sw1.setChecked(false);
				  }
			}
		 }catch(Exception e){
			System.err.println(e.toString());
		}
		
		
		//for triggering password protection
		sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			   try{
				   if(isChecked==true)
					{   
						
						File setfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/psswrd.pkt");
						FileOutputStream outp;
						
						outp = new FileOutputStream(setfile);
			               			
		                outp.write(1);		
		                outp.close();
		                startPassword();
		               
					}
					else
					{
						File setfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/psswrd.pkt");
						FileOutputStream outp;
						
						outp = new FileOutputStream(setfile);
						
		                outp.write(0);		
		                outp.close();
					}
				  } catch (Exception e) {
						System.err.println(e.toString());
					}	
			   
				
				
				
			}
		});
		
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1,settings_list);

	        // Assign adapter to List
	        setListAdapter(adapter); 
	}

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
                        
        super.onListItemClick(l, v, position, id);
                
         // ListView Clicked item index
         int itemPosition     = position;
         startSettingsOptions(itemPosition);
	  }       
	
	public void startSettingsOptions(int pos){
		switch(pos){
		case 0:
			     Intent launchUP= new Intent(this,UpdatePass.class);
			     startActivity(launchUP);
			     break;
		case 1:  
			    
			     Intent launchEM=new Intent(this,ChangeMail.class);
		         startActivity(launchEM);
		         break;
		}
	}
	
	public void startPassword(){
		Intent launch=new Intent(this,PasswordEnter.class);
		startActivity(launch);
		finish();
	}
}
