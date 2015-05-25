package com.example.pocket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmailEnter extends Activity {

	EditText mail;
	Editable pmail;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email_enter);
		tv=(TextView)findViewById(R.id.EmailText);
		tv.setText("enter your email here.Your username and password will be sent to this email in case you forget!");
        mail=(EditText)findViewById(R.id.MailInput);
        pmail=mail.getText();
		
		Button doneb=(Button)findViewById(R.id.EmailDone);
        doneb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			    startMailPocket();	
			}
		});
	}

	public void startMailPocket(){
		
		try{	
			File rFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recoverymail.pkt");
			System.out.println(rFile.getAbsolutePath());
            FileOutputStream fOut = new FileOutputStream(rFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(pmail.toString());
            myOutWriter.close();
            fOut.close();
		   }catch(Exception e){
				Toast.makeText(this, "exception occured", Toast.LENGTH_LONG).show();
		}
		Intent LaunchPocket=new Intent(this,Pocket.class);
		startActivity(LaunchPocket);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email_enter, menu);
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
