package com.example.pocket;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Settings extends ListActivity {
	
	static final String[] settings_list=new String[]{
		"Change username and password",
		"Change recovery mail"
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		
		
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
}
