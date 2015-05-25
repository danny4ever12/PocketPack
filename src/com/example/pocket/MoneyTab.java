package com.example.pocket;

import java.util.ArrayList;

import com.example.pocket.DATA_TABLE.TableInfo;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MoneyTab extends ListActivity implements android.widget.CompoundButton.OnCheckedChangeListener{
   //for checkbox
	ListView lv;
	//
	DBoperations DOP;
	MoneytabAdapter myAdapter;
	ArrayList<MyDiary> diaries;
	
	private class MyDiary{
		boolean selected = false;
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
			Log.e("control ","danny");
		}
		public String getRecorddate() {
			return recorddate;
		}
		public void setRecorddate(String recorddate) {
			this.recorddate = recorddate;
		}
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
		setContentView(R.layout.activity_money_tab);
		
	    DOP=new DBoperations(this);
	    myAdapter = new MoneytabAdapter(this);
	    this.setListAdapter(myAdapter);
	    //for chkbox
	    lv=(ListView)findViewById(android.R.id.list);
	    //
	}
	
	
	 
	//just copied...must edit
		private class MoneytabAdapter extends BaseAdapter {
			private LayoutInflater mInflater;
			 ArrayList<MyDiary> diaries;
			private Context context;
			public MoneytabAdapter(Context context) {
			   mInflater = LayoutInflater.from(context);
			   diaries = new ArrayList<MyDiary>();
			   getdata();
			   //chkbx
			   this.diaries=diaries;
			}
			@SuppressWarnings("deprecation")
			public void getdata(){
				
				//sample given....must edit
			   
			   
			   Cursor CR=DOP.getInformation(DOP);
			   startManagingCursor(CR);
		        if(CR.moveToFirst()){
		        	
		          do
		           {
		        	String name = CR.getString(CR.getColumnIndex(TableInfo.USER_NAME));
		        	float debt = CR.getFloat(CR.getColumnIndex(TableInfo.MONEY));
		        	String dttm = CR.getString(CR.getColumnIndex(TableInfo.DATETIME));
		        	MyDiary temp = new MyDiary(name,debt,dttm);
		        	diaries.add(temp);
		        	
		         }while(CR.moveToNext());
				
			   }
		   }
		@Override
		public int getCount() {return diaries.size();}
		public MyDiary getItem(int i) {return diaries.get(i);}
		public long getItemId(int i) {return i;}
		@SuppressLint("InflateParams")
		public View getView(int arg0, View arg1, ViewGroup arg2) {
		final ViewHolder holder;
		View v = arg1;
		if ((v == null) || (v.getTag() == null)) {
		     v = mInflater.inflate(R.layout.money_tab_format, null);
		     holder = new ViewHolder();
		     holder.mName = (TextView)v.findViewById(R.id.name);
		     holder.mny=(TextView)v.findViewById(R.id.debt);
		     holder.mDate = (TextView)v.findViewById(R.id.datetext);
		     holder.chkBox=(CheckBox)v.findViewById(R.id.CB1);
		     holder.chkBox.setOnCheckedChangeListener((MoneyTab) context);
		     v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		//for chkbox
		MyDiary p=diaries.get(arg0);
		holder.chkBox.setChecked(p.isSelected());
		holder.chkBox.setTag(p);
		//
		holder.mdiary = getItem(arg0);
		holder.mName.setText(holder.mdiary.name+"        ");
		holder.mny.setText("  "+holder.mdiary.val);
		holder.mDate.setText(holder.mdiary.recorddate);
		v.setTag(holder);
		return v;	
	}
 public class ViewHolder {
		MyDiary mdiary;
		TextView mName;
		TextView mny;
		TextView mDate;
		CheckBox chkBox;
	}
}
					
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			int pos=lv.getPositionForView(buttonView);
			if(pos!=ListView.INVALID_POSITION){
				MyDiary p=diaries.get(pos);
				p.setSelected(isChecked);
				p.getRecorddate();
				Log.e("value obtained",""+p.getRecorddate()+"");
				Toast.makeText(this, "make mu ds"+p.getRecorddate()+"", Toast.LENGTH_LONG).show();
					
				
			}
			
		}
		
			
			

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.money_tab, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_money_tab,
					container, false);
			return rootView;
		}
	}



	
}
