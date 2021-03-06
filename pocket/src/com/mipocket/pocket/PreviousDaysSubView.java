package com.mipocket.pocket;


import java.util.ArrayList;

import com.mipocket.pocket.DATA_TABLE.TableInfo;
import com.mipocket.pocket.R.drawable;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

//for indicating expenses of a particular day
public class PreviousDaysSubView extends ListActivity {

    ListView lv;
	
	DBoperations DOP;
	MoneytabsubAdapter myAdapter;
	ArrayList<MyDiary> diaries;
	Context ctx=this;
	String retDTM;

	private class MyDiary{
	
		
		public MyDiary(String n, float v,String r){
		
		name=n;
		val=String.valueOf(v);
		recorddate=r;
		}
		public String name;
		public String val;
		public String recorddate;
		
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_days_sub_view);
	    
		 Intent k=getIntent();
	     retDTM=k.getStringExtra("DTM");
		 
	     DOP=new DBoperations(this);
		    myAdapter = new MoneytabsubAdapter(this);
		    this.setListAdapter(myAdapter);
		    
		    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
			{
				ActionBar actionbar=getActionBar();
				actionbar.setDisplayHomeAsUpEnabled(true);
				actionbar.setHomeAsUpIndicator(drawable.action_previous);
			}
			
	
	
	}
 //for listview
	 private class MoneytabsubAdapter extends BaseAdapter implements ListAdapter{
			private LayoutInflater mInflater;
			 ArrayList<MyDiary> diaries;
			 @SuppressWarnings("unused")
			private ArrayList<String> list = new ArrayList<String>(); 
			 @SuppressWarnings("unused")
			private Context context; 
			 
			 //for button
			 @SuppressWarnings("unused")
			public MoneytabsubAdapter(ArrayList<String> list, Context context) { 
				    this.list = list; 
				    this.context = context; 
				}
			//
			 
			public MoneytabsubAdapter(Context context) {
			   mInflater = LayoutInflater.from(context);
			   diaries = new ArrayList<MyDiary>();
			   getdata();
			  
			}
			@SuppressWarnings("deprecation")
			public void getdata(){
				
				
			   
			   
			   Cursor CR=DOP.getInfoDaily(DOP);
			   startManagingCursor(CR);
		        if(CR.moveToFirst()){
		        	
		          do
		           {
		        	String dttm = CR.getString(CR.getColumnIndex(TableInfo.DATETIME));
		        	if(dttm.contains(retDTM))
		        	{
		        	   float debt = CR.getFloat(CR.getColumnIndex(TableInfo.MONEY));
		        	   String name = CR.getString(CR.getColumnIndex(TableInfo.OCCATION));
		        	   MyDiary temp = new MyDiary(name,debt,dttm);
	 	        	   diaries.add(temp);
		        	}
		         }while(CR.moveToNext());
				
			   }
		   }
		@Override
		public int getCount() {return diaries.size();}
		public MyDiary getItem(int i) {return diaries.get(i);}
		public long getItemId(int i) {return i;}
		@SuppressLint("InflateParams")
		
		
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		
		View v = arg1;
		if ((v == null) || (v.getTag() == null)) {
		     v = mInflater.inflate(R.layout.previous_days_sub_format, null);
		     holder = new ViewHolder();
		     holder.mName = (TextView)v.findViewById(R.id.name);
		     holder.mny=(TextView)v.findViewById(R.id.debt);
		     holder.mDate = (TextView)v.findViewById(R.id.datetext);
		     v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		
		
		holder.mdiary = getItem(arg0);
		holder.mName.setText(holder.mdiary.name+"        ");
		holder.mny.setText(holder.mdiary.val);
		holder.mDate.setText(holder.mdiary.recorddate);
		v.setTag(holder);
		return v;	
	}
public class ViewHolder {
		MyDiary mdiary;
		TextView mName;
		TextView mny;
		TextView mDate;
					
	}
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
