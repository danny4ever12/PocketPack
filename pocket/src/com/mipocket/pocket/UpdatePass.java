package com.mipocket.pocket;


import com.mipocket.pocket.DATA_TABLE.TableInfo;
import com.mipocket.pocket.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePass extends ActionBarActivity {

	EditText et1,et2,et3,et4;
	Editable pet1,pet2;
	String pet3,pet4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pass);
		et1=(EditText)findViewById(R.id.CurrentNm);
		et2=(EditText)findViewById(R.id.CurrentPss);
		et3=(EditText)findViewById(R.id.NewUserEdit);
		et4=(EditText)findViewById(R.id.NewPss);
		
		
	     //for menu back button
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
		{
			ActionBar actionbar=getActionBar();
			actionbar.setDisplayHomeAsUpEnabled(true);
			actionbar.setHomeAsUpIndicator(drawable.action_previous);
		}
		
		
		Button go=(Button)findViewById(R.id.ChangePassStart);
		go.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startChange();
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void startChange()
	{
		
		pet1=et1.getText();
		pet2=et2.getText();
		
		
			
		DBoperations DB=new DBoperations(this);
		Cursor CR=DB.getUSRPSS(DB);
		startManagingCursor(CR);
		String temp1 = "",temp2="";                
	    try{
	        CR.moveToFirst();
	        	
	         
	        	temp1 = CR.getString(CR.getColumnIndex(TableInfo.NAME));
	            temp2 = CR.getString(CR.getColumnIndex(TableInfo.PASSWORD));
	        
	       }catch(Exception e){
	    	   String T1="",T2="";
			   DB.putNamePass(DB, T1, T2);
	       }
                   
          
              
                    
             if ((temp1.equals(pet1.toString()))&&(temp2.equals(pet2.toString())))
                 {
            	   startUpdateValues();
                 }
             else
            	 Toast.makeText(this, "Wrong username or password! please try again ", Toast.LENGTH_LONG).show();
	

	        }	
	
	
	public void startUpdateValues()
	{
		pet3=et3.getText().toString();
		pet4=et4.getText().toString();
	  DBoperations DB=new DBoperations(this);		
	  @SuppressWarnings("unused")
	int k=DB.deleteUSRPSS(DB);
   	  DB.putNamePass(DB, pet3, pet4);
	  Toast.makeText(this, "Username and password updated successfully!", Toast.LENGTH_LONG).show();
      this.finish();
              
		 	    
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
