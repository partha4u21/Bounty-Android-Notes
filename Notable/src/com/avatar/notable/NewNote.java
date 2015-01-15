package com.avatar.notable;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

public class NewNote extends Activity {
	NotesDbOperations db=new NotesDbOperations(this);
	List<NotesDbMaster> list=new ArrayList<NotesDbMaster>();
	//int index=list.size()+1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{super.onCreate(savedInstanceState);
	setContentView(R.layout.newnote);
	//EditText textTitle=(EditText)findViewById(R.id.NoteTitle);
	//EditText textBody=(EditText)findViewById(R.id.NoteSubject);
	
	
	}
	
	@Override
    public void onBackPressed() {
        String title=((EditText)findViewById(R.id.NoteTitle)).getText().toString();
        String body=((EditText)findViewById(R.id.NoteSubject)).getText().toString();
        //System.out.println("index value is "+index);
        
        (findViewById(R.id.NoteSubject)).requestFocus();
        if((findViewById(R.id.NoteSubject)).requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        
        
        NotesDbMaster mastertable=new NotesDbMaster();
        
        if(body.length()>0) 
        	{if(title.length()==0) mastertable.setNoteTitle("Title");
        	mastertable.setNoteBody(body);
        	//mastertable.setId(index);
        	if(db.addNote(mastertable)) System.out.println("adding failed ");
        	else System.out.println("added successfully");}
        
        db.close();
        SharedPreferences.Editor editor = getSharedPreferences("Notes", MODE_PRIVATE).edit();
        editor.putString("noteTitle",title.toString());
        editor.putString("noteBody",body.toString());
        //editor.putString("noteId",Integer.toString(index));
        editor.commit();
        
        
        
        super.onBackPressed();
    }
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}










