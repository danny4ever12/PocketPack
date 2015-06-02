package com.example.pocket;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
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
	String retDTM,retMSND;
	Float getval;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update__debt);
		
		Intent k=getIntent();
	    retDTM=k.getStringExtra("DTM");
	    retMSND=k.getStringExtra("MSND");
	    getval=Float.valueOf(retMSND);
	    
	
	   
	   
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

	public void startUpdate()
	{
		    float gval=Float.valueOf(give.getText().toString());
			float bval=Float.valueOf(borrow.getText().toString());
			float rval= gval-bval;
			getval=getval+rval;
			
			
			DBoperations DB=new DBoperations(ctx);
			int k=DB.updateDebt(DB, retDTM, getval);
			Toast.makeText(this, "debt list updated", Toast.LENGTH_LONG).show();
			finish();
			
	}
	
	// String positiveOrNegative(float number){
	  //      return (((int)(number/0.0))>>31 == 0)? "positive" : "negative";
	   // }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update__debt, menu);
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
