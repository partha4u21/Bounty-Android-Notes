package com.avatar.notable;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint({ "NewApi"}) public class MainActivity extends Activity {
	
	
	
	List<NotesDbMaster> list=new ArrayList<NotesDbMaster>();
	static String latest;
	static int idAutoincrement=0;
	static int idVar=0;
	static int lastNewNote;
	float yCoordinate;
	int lastViewAlign=0;  // 0 is left, 1 is right

	
	static float firstX=0,firstY=0,secondX=0,secondY=0;
	
	
	
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
		int i=list.size();
		if(i>=0)	
		{for(idVar=i-1;idVar>=0;idVar--)
			{System.out.println("list element : "+idVar+" : value :"+list.get(idVar).getNoteBody());
			String noteTitle=list.get(idVar).getNoteTitle().toString();
			String noteBody=list.get(idVar).getNoteBody().toString();
			//int noteId=list.get(i).getId();
			
			System.out.println("title : "+noteTitle);
			System.out.println("body : "+noteBody);
			
			noteMaker(noteTitle,noteBody,idVar);
			
			}
		}
		
		
		
		if(list.size()>0)
		//latest=list.get(list.size()-1).getNoteBody();
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
	      
	   
	   list=db.getAllNotes();
	   RelativeLayout notelay=(RelativeLayout) findViewById(R.id.notes);
	   notelay.removeAllViews();
	   
	   System.out.println("list size : "+list.size());
	   
	   SharedPreferences prefs = getSharedPreferences("Notes",MODE_PRIVATE); 
	   //flag=prefs.getString("noteFlag","false");	 
	   
	   String sharedTitle = prefs.getString("noteTitle","Title");
	   String sharedBody = prefs.getString("noteBody",null);
	   latest=sharedBody;

	   if(sharedTitle!=null) sharedTitle="";
	   int i=list.size(); 
	   if(sharedBody!=null)
	
		if(i>=0)	
		{for(idVar=i-1;idVar>=0;idVar--)
			{System.out.println("list element : "+idVar+" : value :"+list.get(idVar).getNoteBody());
			String noteTitle=list.get(idVar).getNoteTitle().toString();
			String noteBody=list.get(idVar).getNoteBody().toString();
			//int noteId=list.get(i).getId();
			
			System.out.println("title : "+noteTitle);
			System.out.println("body : "+noteBody);
			
			noteMaker(noteTitle,noteBody,idVar);
			
			}
		}
			
			
		
		
		
	   lastNewNote=idVar;
	   	   
	   
	  
	   db.close();
	   super.onResume();
	   
    	
    }
   
   
   void noteMaker(String titleParams,String bodyParams,int index)
   {	
	   System.out.println("notemaker started");
	   
	   
	   System.out.println("firstX : "+firstX+" : firstY : "+firstY);
	   
	   
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
	   	System.out.println("magiclength notewidth :"+noteWidth);
	   	}
	   else 
	   	{int width = display.getWidth();  // deprecated
	   	int height = display.getHeight();  // deprecated
	    noteWidth=(width/2)-30;
	    System.out.println("magiclength notewidth :"+noteWidth);}
	   
	   final TextView title = new TextView(getApplicationContext()),body=new TextView(getApplicationContext());
	   RelativeLayout notelay=(RelativeLayout) findViewById(R.id.notes);
	   registerForContextMenu(body);
	   
	   System.out.println("magiclength "+getLongestString(bodyParams)+" : "+getTextLength(getLongestString(bodyParams)));
	   
	   
	   
	  {
	   int noteLength=bodyParams.length();
	   /*if(noteLength>=30)
	   		{textFont=250/noteLength;
	   		 if(textFont<=20) textFont=30;
	   		}
	   else textFont=30;*/
	   
	   if(getTextLength(getLongestString(bodyParams))<=noteWidth)
	   		{textFont=(int)(getTextLength(getLongestString(bodyParams))*0.2);
	   		if(textFont<=20) {textFont=30;System.out.println("magiclength avatar : "+bodyParams+" : "+textFont);}
	   		}
	   else {textFont=20;System.out.println("magiclength avatar : "+bodyParams+" : "+textFont);}
	   
	   
	   if(bodyParams!=null)
	   {
	   RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(noteWidth,LayoutParams.WRAP_CONTENT);
	   
	   
	   
	   title.setText(bodyParams);
	   title.setTextColor(Color.parseColor("#000000"));
	   title.setTextSize(20);
	   body.setText(bodyParams);
	   body.setTextColor(Color.parseColor("#000000"));
	   body.setTextSize(textFont);
	  
	   
	   //notelay.addView(title, params);
	   body.setBackgroundColor(Color.parseColor("#FFFFFF"));
	   body.setPadding(5, 5,5,5);
	   
	   
	   
	  	   
	   //params.setMargins(5,5,5,5);
	   
	   
	   body.setId(index);
	   body.setY(yCoordinate+10);
	   
	   
	   
		   {if(index==list.size()-1) {params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);lastViewAlign=0; //0 for left
	   							firstX=0;firstY=0;
	   							
	   							System.out.println("notey : first :"+firstX+" : firstY : "+firstY);}
		   else if(index==list.size()-2) {params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); lastViewAlign=1; //1 for right
		   						secondX=noteWidth+10;
		   						
		   						secondY=0;
		   						System.out.println("notey : second :"+secondX+" : secondY : "+secondY);}
		   else if(index<=list.size()-3)
	   				{if(firstY<=secondY) 
		   					{//params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	   						lastViewAlign=0;
	   						params.leftMargin=0;
	   						
		   					params.topMargin=(int)firstY+10;
		   					firstY=firstY+10;
		   					System.out.println("notey : first : "+firstY);
		   					}
	   				else if(firstY>secondY) 
	   						{//params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	   						lastViewAlign=1;
	   						
	   						params.leftMargin=noteWidth+10;
	   						params.topMargin=(int)secondY+10;
	   						secondY=secondY+10;
		   					System.out.println("notey : second : "+secondY);
		   					}
	   				}
		   }
		   
	   
	   
	   body.setLayoutParams(params);
	   notelay.addView(body,params);
	   
	   //dont change the measure spec params from at_most and the notewidth. views may overlap in the main screen
	   body.measure(MeasureSpec.makeMeasureSpec(noteWidth,MeasureSpec.AT_MOST),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));  //this is a forced measure ; runnable was not used since sharing variables from runnable didnt work
	   if (lastViewAlign==0) {firstY=firstY+body.getMeasuredHeight();System.out.println("bodyYfirst :"+body.getMeasuredHeight()+" : "+bodyParams);}
	   else if(lastViewAlign==1) {secondY=secondY+body.getMeasuredHeight();System.out.println("bodyYsecond :"+body.getMeasuredHeight()+" : "+bodyParams);}

	   
	   	   
	   
       Animation animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
       Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
       body.startAnimation(animRotate);
       body.startAnimation(animSlide);
	   
	   
	   
	   }  
		   }
	   
	   } 
  
	  	  
   }
   
   
   @Override
   public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
   super.onCreateContextMenu(menu, v, menuInfo);
               // Create your context menu here
       menu.add(0, v.getId(), 0, "Delete");  
       
   }
   
   @Override
   public boolean onContextItemSelected(MenuItem item) {
	   
	   try{ //System.out.println("nullpointer "+item.);
	   AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	   TextView view = (TextView) info.targetView;
	   
	   ((ViewManager) this.getParent()).removeView(view);
	   //((ViewManager) this.getParent()).removeView(findViewById(view.getId()));
	   }
	   catch(Exception e) {Toast.makeText(getApplicationContext(),"Note delete failed !!",Toast.LENGTH_SHORT).show();}
	   return true;

   }
   
   
   
   int getTextLength(String text)
   {Paint paint = new Paint();
	float densityMultiplier = this.getResources().getDisplayMetrics().density;
   final float scaledPx = 20 * densityMultiplier;
   paint.setTextSize(scaledPx);
   int size = (int) paint.measureText(text);
   return size;}
   
   String getLongestString(String text)
   {String str=text+" ";
   int i=str.length(),j=0;
   String lngText="",finalText="";
   int lngTextLen=lngText.length(),finalTextLen=finalText.length();
   
   for(j=0;j<i;j++)
   		{if(str.charAt(j)!=' ')
   			{lngText=lngText+str.charAt(j);
   			lngTextLen=lngText.length();}
   		else
   			{
   			
   			if(lngTextLen>finalTextLen) {finalText=lngText;finalTextLen=lngTextLen;}
   			lngText="";
   			}
   		}
	System.out.println("final text :"+finalText);   
   return finalText;
   }
   
   
   
   
   
 

}




























