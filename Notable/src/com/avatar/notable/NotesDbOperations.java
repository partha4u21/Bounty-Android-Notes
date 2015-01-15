package com.avatar.notable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class NotesDbOperations extends SQLiteOpenHelper {

		// All Static variables
		// Database Version
		private static final int DATABASE_VERSION = 1;

		// Database Name3
		private static final String DATABASE_NAME = "NotesManager";

		// Contacts table name
		private static final String TABLE_MASTER = "NotesMasters";

		// Contacts Table Columns names
		
		
		private String ID="id";
		private static final String TITLE = "_title";
		private static final String BODY = "note_body";
		
		
		
		static NotesDbMaster[] recent=new NotesDbMaster[5];

//create the database here ===================================================================================		
		public NotesDbOperations(Context context) 
			{super(context, DATABASE_NAME, null, DATABASE_VERSION);}

		//Creating Tables
		@Override
		public void onCreate(SQLiteDatabase db) {
			System.out.println("Creating new table");
			String CREATE_MASTER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTER + "("+ TITLE + " STRING PRIMARY KEY," + BODY + " STRING UNIQUE NOT NULL"+")";
			db.execSQL(CREATE_MASTER_TABLE);
			
		}
		
		
		

		/**
		 * All CRUD(Create, Read, Update, Delete) Operations
		 */

		// Adding new note ==========================================================================================
	   public boolean addNote(NotesDbMaster mastertable) {
		   	boolean flag=true;
			SQLiteDatabase db = this.getWritableDatabase();
			System.out.println("adding process");
			ContentValues values = new ContentValues();
			//values.put(KEY_ID, mastertable.getId());
			//String INSERT_TABLE="INSERT INTO "+ TABLE_MASTER +" VALUES ("+mastertable.getNoteTitle()+","+mastertable.getNoteBody()+")";
			
			//db.execSQL(INSERT_TABLE);
			//values.put(ID,mastertable.getId());
			values.put(TITLE, mastertable.getNoteTitle());
			values.put(BODY, mastertable.getNoteBody()); 
			//values.put(ID,mastertable.getId());*/
			System.out.println(" values= "+mastertable.getNoteBody());
						
			
			//Closing database connection
			
			
			
			// Inserting Row
			try{ db.insertOrThrow(TABLE_MASTER,null,values);}
            catch(Exception e) {System.out.println("exception : "+e.toString());db.close();return false;}
			db.close();
			return flag;
			}
		
	   

		
		
		
	   // Getting All NOTES in the list ========================================================================
		public List<NotesDbMaster> getAllNotes() {
			SQLiteDatabase db = this.getReadableDatabase();
			List<NotesDbMaster> noteList = new ArrayList<NotesDbMaster>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_MASTER;
			Cursor cursor = db.rawQuery(selectQuery, null);
			
			// looping through all rows and adding to list
			try{
			if (cursor.moveToFirst()) {
				do {
					NotesDbMaster masterTable = new NotesDbMaster();
					masterTable.setNoteTitle(cursor.getString(0));
					masterTable.setNoteBody(cursor.getString(1));
					//masterTable.setId(cursor.getInt(2));
					noteList.add(masterTable);
					
				
				} while (cursor.moveToNext());
			}
			
			}
			
			catch(Exception e) {System.out.println(e.toString());}
			cursor.close();
			db.close();
			
			return noteList;
			
			
			// return process list
			
		}
		
		
		

// Updating single NOTE ============================================================================
		public int updateNote(NotesDbMaster masterTable) {
			SQLiteDatabase db = this.getWritableDatabase();
			System.out.println("updating note");
			ContentValues values = new ContentValues();
			//values.put(KEY_ID, masterTable.getId());
			//values.put(ID, masterTable.getId());
			values.put(TITLE, masterTable.getNoteTitle());
			values.put(BODY, masterTable.getNoteBody());
			
			db.close();
			// updating row
			return db.update(TABLE_MASTER, values, TITLE + " = ?",new String[]{masterTable.getNoteTitle()});
			
			}

		
			
		
// Deleting single NOTE ============================================================================= 
		public void deleteNote(NotesDbMaster masterTable) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_MASTER, TITLE + " = ?",
					new String[] {masterTable.getNoteTitle()});
			db.close();
		}


		

// upgrade the database =================================================================================		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
			
		}
		
		
//Cursor check - test function
		public void cursorTest(String Appname)
		{SQLiteDatabase db = this.getReadableDatabase();
		String sql="select * from "+TABLE_MASTER+" where "+TITLE+"="+Appname;
		Cursor mCursor = db.rawQuery(sql,null);
		db.close();
		System.out.println("String test : "+mCursor.toString());
		}
		
		
		
	
		
	}

