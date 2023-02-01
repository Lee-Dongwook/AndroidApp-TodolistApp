package com.example.todolistapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Lee_db";


    public DBHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTERGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int Old, int New)
    {
        onCreate(db);
    }

    public ArrayList<Todoitem> getTodoList()
    {
        ArrayList<Todoitem> todoItems = new ArrayList <>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeData DESC", null);
        if(cursor.getCount() != 0)
        {
            while(cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                Todoitem todoItem = new Todoitem();
                todoItem.setId(id);
                todoItem.setTitle(title);
                todoItem.setContent(content);
                todoItem.setWriteDate(writeDate);
                todoItems.add(todoItem);
            }
        }
        cursor.close();

        return todoItems;
    }

    public void Insert(String _title, String _content, String _writeDate)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES('" + _title + "','"+_content+"','" +_writeDate +"');");
    }

    public void Update(String _title, String _content, String _writeDate, int _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title ='" + _title + "', content='"+_content +"' , writeDate='" +_writeDate+"'WHERE id='" + _id+ "'");
    }

    public void Delete(int _id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE id = '" +_id + "'");
    }

}
