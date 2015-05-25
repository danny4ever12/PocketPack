package com.example.pocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePass extends ActionBarActivity {

	EditText et1,et2,et3,et4;
	Editable pet1,pet2,pet3,pet4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pass);
		et1=(EditText)findViewById(R.id.CurrentNm);
		et2=(EditText)findViewById(R.id.CurrentPss);
		et3=(EditText)findViewById(R.id.NewUserEdit);
		et4=(EditText)findViewById(R.id.NewPss);
		pet1=et1.getText();
		pet2=et2.getText();
		pet3=et3.getText();
		pet4=et4.getText();
		
		Button go=(Button)findViewById(R.id.ChangePassStart);
		go.setOnClickListener(new View.OnClickListener() {
			
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
			  File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/code.pkt");
			  System.out.println(setFile.getAbsolutePath());
			
			  FileInputStream fin=new FileInputStream(setFile);
              InputStreamReader isr = new InputStreamReader(fin);
              BufferedReader bufferedReader = new BufferedReader(isr);
              String receiveString1 = "";
              String receiveString2 = "";
                 
              receiveString1 = bufferedReader.readLine();     
              String temp1 = receiveString1.toString();        
              receiveString2 = bufferedReader.readLine();         
              String temp2=receiveString2.toString();
              fin.close();
                    
             if ((temp1.equals(pet1.toString()))&&(temp2.equals(pet2.toString())))
                 {
            	   startUpdateValues();
                 }
             else
            	 Toast.makeText(this, "Wrong username or password! please try again ", Toast.LENGTH_LONG).show();
	

          }catch(Exception e){
  	          Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
          }	
	}
	
	public void startUpdateValues()
	{
		try{	
			  File setFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/code.pkt");
			  System.out.println(setFile.getAbsolutePath());
			  
			  FileOutputStream fOut = new FileOutputStream(setFile);
              OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
              myOutWriter.write(pet3.toString());
              myOutWriter.append("\n");
              myOutWriter.append(pet4.toString());
              myOutWriter.close();
              fOut.close();
              Toast.makeText(this, "Username and password updated successfully!", Toast.LENGTH_LONG).show();
              this.finish();
              
		 }catch(Exception e){
				Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
			}	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_pass, menu);
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
