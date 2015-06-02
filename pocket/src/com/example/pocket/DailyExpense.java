package com.example.pocket;

import java.util.ArrayList;

import com.example.pocket.DATA_TABLE.TableInfo;


import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DailyExpense extends ListActivity {
	
	ListView lv;
	//
	DBoperations DOP;
	MoneydailyAdapter myAdapter;
	ArrayList<MymDiary> diaries;
	Context ctx=this;
	private class MymDiary{
	
		
		public MymDiary(String n, float v,String r){
		
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
		setContentView(R.layout.activity_daily_expense);
		
		    DOP=new DBoperations(this);
		    
		    myAdapter = new MoneydailyAdapter(this);
		    this.setListAdapter(myAdapter);
		    
		    Button addbtn=(Button)findViewById(R.id.dbn);
		    addbtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    startbtn1();	
				}
			});
		    
		    Button prevbtn=(Button)findViewById(R.id.gotobn);
		    prevbtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    startbtn2();	
				}
			});
		    
		    
	}

	public void startbtn1()
	{
		Intent launchActivity=new Intent(this,AddDaily.class);
		startActivity(launchActivity);
		finish();
	}
	
	public void startbtn2()
	{
		Intent launchActivity=new Intent(this,PreviousDays.class);
		startActivity(launchActivity);
	}
	
    private class MoneydailyAdapter extends BaseAdapter implements ListAdapter{
		private LayoutInflater mInflater;
		 ArrayList<MymDiary> diaries;
		 
		@SuppressWarnings("unused")
		private ArrayList<String> list = new ArrayList<String>(); 
		 
		@SuppressWarnings("unused")
		private Context context; 
		 
		 //for button
		 @SuppressWarnings("unused")
		public MoneydailyAdapter(ArrayList<String> list, Context context) { 
			    this.list = list; 
			    this.context = context; 
			}
		//
		 
		public MoneydailyAdapter(Context context) {
		   mInflater = LayoutInflater.from(context);
		   diaries = new ArrayList<MymDiary>();
		   getdata();
		  
		}
		@SuppressWarnings("deprecation")
		public void getdata(){
			
			
		   float tempSum=0;
		   
		   Cursor CR=DOP.getInfoDaily(DOP);
		   startManagingCursor(CR);
	        if(CR.moveToFirst()){
	        	
	          do
	           {
	        	String name = CR.getString(CR.getColumnIndex(TableInfo.OCCATION));
	        	float debt = CR.getFloat(CR.getColumnIndex(TableInfo.MONEY));
	        	tempSum+=debt;
	        	String dttm = CR.getString(CR.getColumnIndex(TableInfo.DATETIME));
	        	MymDiary temp = new MymDiary(name,debt,dttm);
	        	diaries.add(temp);
	        	
	         }while(CR.moveToNext());
	        
	          DOP.deleteSum(DOP);
	          DOP.putSum(DOP, tempSum);
	          
	          
	          //SAMPLE VALUES FOR TESTING
		   //   DOP.putDaysTotal(DOP, tempSum);
		      
		      
		      
		      
		   }
	   }
	@Override
	public int getCount() {return diaries.size();}
	public MymDiary getItem(int i) {return diaries.get(i);}
	public long getItemId(int i) {return i;}
	@SuppressLint("InflateParams")
	
	
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
	final ViewHolder holder;
	
	View v = arg1;
	if ((v == null) || (v.getTag() == null)) {
	     v = mInflater.inflate(R.layout.daily_expense_format, null);
	     holder = new ViewHolder();
	     holder.mName = (TextView)v.findViewById(R.id.occatn);
	     holder.mny=(TextView)v.findViewById(R.id.price);
	     holder.mDate = (TextView)v.findViewById(R.id.dtext);
	     holder.deleteBtn = (Button)v.findViewById(R.id.dlybn1);
		
	     v.setTag(holder);
	}
	else {
		holder = (ViewHolder) v.getTag();
	}
	
	 
	 
	 holder.deleteBtn.setOnClickListener(new View.OnClickListener(){
	       
			@Override
	        public void onClick(View v) { 
	            
	            diaries.remove(arg0);
	            
	          DBoperations DB = new DBoperations(ctx);
	          
	          
	          
	          float tmpval1= Float.valueOf(holder.mdiary.val);
	          Cursor cr=DB.getSum(DB);
	          startManagingCursor(cr);
		      cr.moveToFirst();
		      float tmpval2 = cr.getFloat(cr.getColumnIndex(TableInfo.SUM));
		      tmpval2= tmpval2-tmpval1;
		      DB.deleteSum(DB);
		      DB.putSum(DB, tmpval2);
		     
	          @SuppressWarnings("unused")
			int k=  DB.deleteDaily(DB,holder.mdiary.recorddate);
	            notifyDataSetChanged();
	            Toast.makeText(ctx, "transaction deleted ", Toast.LENGTH_LONG).show();
	        }
	    });
	   

	
	  holder.mdiary = getItem(arg0);
	  holder.mName.setText(holder.mdiary.name+"        ");
	  holder.mny.setText("  "+holder.mdiary.val);
	  holder.mDate.setText(holder.mdiary.recorddate);
	  v.setTag(holder);
	  return v;	
    }
    public class ViewHolder {
	MymDiary mdiary;
	TextView mName;
	TextView mny;
	TextView mDate;
	Button deleteBtn;
	
	
    }
}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daily_expense, menu);
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
