package com.mipocket.pocket;


import java.util.ArrayList;
import com.mipocket.pocket.DATA_TABLE.TableInfo;
import com.mipocket.pocket.R.drawable;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

	public class PreviousDays extends ListActivity {
		
		ListView lv;
		//
		DBoperations DOP;
		MoneyprevAdapter myAdapter;
		ArrayList<MypDiary> diaries;
		Context ctx=this;
		private class MypDiary{
		
			
			public MypDiary(String n, float v,String r){
			
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
			setContentView(R.layout.activity_previous_days);
			
			if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2) 
			{
				ActionBar actionbar=getActionBar();
				actionbar.setDisplayHomeAsUpEnabled(true);
				actionbar.setHomeAsUpIndicator(drawable.action_previous);
			}
			

		    DOP=new DBoperations(this);
		    myAdapter = new MoneyprevAdapter(this);
		    this.setListAdapter(myAdapter);
		}

		 private class MoneyprevAdapter extends BaseAdapter implements ListAdapter{
				private LayoutInflater mInflater;
				 ArrayList<MypDiary> diaries;
				 
				@SuppressWarnings("unused")
				private ArrayList<String> list = new ArrayList<String>(); 
				 
				@SuppressWarnings("unused")
				private Context context; 
				 
				 //for button
				 @SuppressWarnings("unused")
				public MoneyprevAdapter(ArrayList<String> list, Context context) { 
					    this.list = list; 
					    this.context = context; 
					}
				//
				 
				public MoneyprevAdapter(Context context) {
				   mInflater = LayoutInflater.from(context);
				   diaries = new ArrayList<MypDiary>();
				   getdata();
				  
				}
				@SuppressWarnings("deprecation")
				public void getdata(){
					
					
				   
				   
				   Cursor CR=DOP.getInfoPrevDay(DOP);
				   startManagingCursor(CR);
			        if(CR.moveToFirst()){
			        	
			          do
			           {
			        	String name = CR.getString(CR.getColumnIndex(TableInfo.DATE));
			        	float debt = CR.getFloat(CR.getColumnIndex(TableInfo.TOTAL));
			        	String dttm = CR.getString(CR.getColumnIndex(TableInfo.DAY));
			        	MypDiary temp = new MypDiary(name,debt,dttm);
			        	diaries.add(temp);
			        	
			         }while(CR.moveToNext());
					
				   }
			   }
			@Override
			public int getCount() {return diaries.size();}
			public MypDiary getItem(int i) {return diaries.get(i);}
			public long getItemId(int i) {return i;}
			@SuppressLint("InflateParams")
			
			
			public View getView(final int arg0, View arg1, ViewGroup arg2) {
			final ViewHolder holder;
			
			View v = arg1;
			if ((v == null) || (v.getTag() == null)) {
			     v = mInflater.inflate(R.layout.previous_days_format, null);
			     holder = new ViewHolder();
			     holder.mName = (TextView)v.findViewById(R.id.dt);
			     holder.mny=(TextView)v.findViewById(R.id.pr);
			     holder.mDate = (TextView)v.findViewById(R.id.pday);
			     holder.deleteBtn = (ImageButton)v.findViewById(R.id.prevbtn1);
				 holder.viewBn=(ImageButton)v.findViewById(R.id.viewbn);
			     v.setTag(holder);
			}
			else {
				holder = (ViewHolder) v.getTag();
			}
			
			
			 holder.viewBn.setOnClickListener(new View.OnClickListener(){
			        @Override
			        public void onClick(View v) { 
			            String temp=holder.mdiary.name;
			            
			            Intent launchUp=new Intent(ctx,PreviousDaysSubView.class);
			            launchUp.putExtra("DTM", temp);
			            
			            startActivity(launchUp);
			           
			        }
			    });

			
			
			 
			 holder.deleteBtn.setOnClickListener(new View.OnClickListener(){
			        @Override
			        public void onClick(View v) { 
			            
			            diaries.remove(arg0);
			            
			          DBoperations DB = new DBoperations(ctx);
			          @SuppressWarnings("unused")
					int k=  DB.deleteDailyTotal(DB,holder.mdiary.name);
			            
			            Toast.makeText(ctx, "record deleted ", Toast.LENGTH_LONG).show();
			            finish();
			            startActivity(getIntent());
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
			MypDiary mdiary;
			TextView mName;
			TextView mny;
			TextView mDate;
			ImageButton deleteBtn;
			ImageButton viewBn;
			
		    }
		}	
	
	
	
	
		
	public void startDeleteAll()
	{
		 final DBoperations DB = new DBoperations(ctx);
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
	 
				// set title
				alertDialogBuilder.setTitle("Confirm");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Click yes to delete all!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						@SuppressWarnings("deprecation")
						public void onClick(DialogInterface dialog,int id) {
				
							 
							 //deleting contents of daily expense
							    Cursor CR=DB.getInfoPrevDay(DB);
							    startManagingCursor(CR);
						        if(CR.moveToFirst()){
						        	
						          do
						           {
						        	  String shortdate = CR.getString(CR.getColumnIndex(TableInfo.DATE));
							          Cursor CRtemp=DB.getInfoDaily(DB);
						        	  startManagingCursor(CRtemp);
						        	  if(CRtemp.moveToFirst()){
								        	
								          do
								           {
								        	  String longdate = CRtemp.getString(CRtemp.getColumnIndex(TableInfo.DATETIME)); 
								              if(longdate.contains(shortdate))
								              {
								            	  @SuppressWarnings("unused")
												int z=DB.deleteDaily(DB, longdate);
								              }
								           }while(CRtemp.moveToNext());
						              }
						           }while(CR.moveToNext());  
						        	  
						       
							 //deleting contents of daily expence total
								@SuppressWarnings("unused")
								int k=  DB.deleteDailyTotalAll(DB);
						        Toast.makeText(ctx, "transaction deleted ", Toast.LENGTH_LONG).show();
						        finish();
						      	startActivity(getIntent());
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
	
	
	
	 public void goSettings()
	 {
		 Intent launch=new Intent(this,Settings.class);
	    	startActivity(launch);
	    	finish();
	 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.previous_days, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch (item.getItemId()) {
	      case R.menu.previous_days:
	      	  startDeleteAll();
	      	 
	      	  return true;  
	      
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
