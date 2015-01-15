package com.avatar.notable;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


@SuppressLint({ "NewApi"}) public class MainActivity extends Activity {
	
	
	
	List<NotesDbMaster> list=new ArrayList<NotesDbMaster>();
	static String latest;
	static int idAutoincrement=0;
	static int idVar=0;
	static int lastNewNote;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesDbOperations db=new NotesDbOperations(this);
        list=db.getAllNotes();
        
        ImageView newnote= (ImageView) findViewById(R.id.newitem);
        SharedPreferences prefs = getSharedPreferences("Notes",MODE_PRIVATE);
        
        
        
 	    
        
        newnote.setOnClickListener(new View.OnClickListener() {
			
     	   
        	
        	
        @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		
				Intent i=new Intent(MainActivity.this,NewNote.class);
				startActivity(i);
				
			}
		} );
        
        
        
		//View noteView = new View(getApplicationContext());
		
		
		System.out.println("length : "+list.size());
		int i=list.size(),j=0;
		if(list.size()>0)	
		{for(j=i-1;j>=0;--j)
			{System.out.println("list element : "+j+" : value :"+list.get(j).getNoteBody());
			String noteTitle=list.get(j).getNoteTitle().toString();
			String noteBody=list.get(j).getNoteBody().toString();
			//int noteId=list.get(i).getId();
			TextView title = new TextView(getApplicationContext()),body=new TextView(getApplicationContext());
			
			
			
			System.out.println("title : "+noteTitle);
			System.out.println("body : "+noteBody);
			//System.out.println("String : "+noteId);
			
			
			
			if(idVar%2==0) 
				noteMaker(noteTitle,noteBody,0,1,idVar++);
			else 
				noteMaker(noteTitle,noteBody,0,0,idVar++);
			}
		}
		
		
		
		if(list.size()>0)
		latest=list.get(list.size()-1).getNoteBody();
        System.out.println("activity track: create");
        
       db.close(); 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
   @Override
    protected void onResume() {
	   NotesDbOperations db=new NotesDbOperations(this);
	   System.out.println("activity track: resume");
	   list=db.getAllNotes();
	   
	   SharedPreferences prefs = getSharedPreferences("Notes",MODE_PRIVATE); 
	   //flag=prefs.getString("noteFlag","false");	 
	   
	   String sharedTitle = prefs.getString("noteTitle","Title");
	   String sharedBody = prefs.getString("noteBody",null);
	   
	   if(sharedTitle!=null) sharedTitle="";
	    
	   if(sharedBody!=null && !sharedBody.equalsIgnoreCase(latest))
	   {if(idVar%2==0)
		   noteMaker(sharedTitle,sharedBody,1,1,idVar++);
	   else noteMaker(sharedTitle,sharedBody,1,0,idVar++);
	   lastNewNote=idVar;
	   latest=sharedBody;
	   }
	   
	  
	   db.close();
	   super.onResume();
	   
    	
    }
   
   
   private void noteMaker(String titleParams,String bodyParams,int flag,int layoutGravity,int positionParam)
   {	
	   System.out.println("notemaker started");
	   
	   
	  if(bodyParams.length()>0 )
	    
	  {
	  // System.out.println("empty : "+list.get(list.size()-1).getNoteBody().toString());
	   Display display = getWindowManager().getDefaultDisplay();
	   int textHeight;
	   int minHeight=100; 
	   int noteWidth=0;
	  
	   
	   int textFont; //variable to change the variable font size
	   Point size = new Point();
	   if(Integer.valueOf(android.os.Build.VERSION.SDK_INT)>=13)
	   	{display.getSize(size);
	   	int width = size.x;
	   	int height = size.y;
	   	noteWidth=(width/2)-30;
	   	}
	   else 
	   	{int width = display.getWidth();  // deprecated
	   	int height = display.getHeight();  // deprecated
	    noteWidth=(width/2)-30;
	   	}
	   
	   TextView title = new TextView(getApplicationContext()),body=new TextView(getApplicationContext());
	   RelativeLayout notelay;
	   if(flag==0)
		   notelay=(RelativeLayout) findViewById(R.id.existingnotes);
	   else notelay=(RelativeLayout) findViewById(R.id.newnote);
	   
	   
	  {
	   int noteLength=bodyParams.length();
	   if(noteLength>=30)
	   		{textFont=250/noteLength;
	   		 if(textFont<=20) textFont=30;
	   		}
	   else textFont=30;
	   
	   if(bodyParams!=null)
	   {
	   RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(noteWidth,LayoutParams.WRAP_CONTENT);
	   
	   if(layoutGravity==1) 
		   {params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);}
	   else {params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);}
	   
	   
	   
	   {
		   if(flag==0)
		   {
		   if (positionParam>=2 && positionParam%2==0) params.addRule(RelativeLayout.BELOW,positionParam+2);
	        else if(positionParam>=2 && positionParam%2!=0) params.addRule(RelativeLayout.BELOW,positionParam+2);
	        else if(positionParam==1 || positionParam==0) params.addRule(RelativeLayout.BELOW,positionParam+2);
		   } 
		   else if(flag==1)
		   {if(lastNewNote>=2 && positionParam%2==0) params.addRule(RelativeLayout.BELOW,lastNewNote+2);
	        else if(lastNewNote>=2 && positionParam%2!=0) params.addRule(RelativeLayout.BELOW,lastNewNote+2);
	        else if(lastNewNote==1 || lastNewNote==0) params.addRule(RelativeLayout.BELOW,lastNewNote+2);}
	   }
	   
	   //else params.addRule(RelativeLayout.ALIGN_PARENT_START);
	   
	  /* title.setText(bodyParams);
	   title.setTextColor(Color.parseColor("#000000"));
	   title.setTextSize(20);*/
	   body.setText(positionParam+":"+bodyParams);
	   body.setTextColor(Color.parseColor("#505050"));
	   body.setTextSize(textFont);
	  
	   
	   //notelay.addView(title, params);
	   body.setBackgroundColor(Color.parseColor("#50FFFFFF"));
	   body.setPadding(5, 5, 5, 5);
	   
	   
	   params.setMargins(0,5,0,5);
	  	   
	  
	   body.setLayoutParams(params);
	   body.setId(positionParam);
	   notelay.addView(body,params);
	   
       Animation animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
       Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
       body.startAnimation(animRotate);
       body.startAnimation(animSlide);
	   
	   
	   
	   }  
		   }
	   
	   } 
  
	  	  
   }
   
   
   
     
   
}




























