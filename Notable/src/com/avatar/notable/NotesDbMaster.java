package com.avatar.notable;


// program to handle the database , databasewrapper

public class NotesDbMaster {
	
	//private variables
	int _id;
	String note_title="TITLE";
	String note_body;
		
	// Empty constructor
	public NotesDbMaster(){
		
	}
	
	// constructor
	public NotesDbMaster(String note_name, String note_desc,int id)
	{	
		this._id=id;
		this.note_title = note_name;
		this.note_body = note_desc;
				
	}
	
		
	//get the id of the note list
	public int getId()
	{return this._id;}
	
	
	//set the id
	public void setId(int id)
	{this._id=id;}
	
	// getting proc_name
	public String getNoteTitle(){
		return this.note_title;
	}
	
	// setting proc_name
	public void setNoteTitle(String note_name){
		this.note_title = note_name;
	}
	
	// getting desc
	public String getNoteBody(){
		return this.note_body;
	}
	
	// setting desc
	public void setNoteBody(String notedesc){
		this.note_body = notedesc;
	}
	
}
	
	