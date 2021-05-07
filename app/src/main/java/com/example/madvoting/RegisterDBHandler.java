package com.example.madvoting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RegisterDB";
    private static final String TABLE_REGISTER = "RegisterTB";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AADHAR = "aadharno";
    private static final String KEY_VOTER = "voterid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STATE = "state";
    private static final String KEY_DISTRICT = "district";
    private static final String KEY_PARTY = "party";
    private static final String KEY_VOTED = "voted";
    public String temp = "empty";
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbhelper;

    public RegisterDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating table
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_REGISTER_TABLE = "CREATE TABLE " + TABLE_REGISTER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_AADHAR + " TEXT UNIQUE," + KEY_VOTER + " TEXT UNIQUE," + KEY_USERNAME + " TEXT UNIQUE," + KEY_PASSWORD + " TEXT," + KEY_STATE + " TEXT," + KEY_DISTRICT + " TEXT," + KEY_PARTY + " TEXT," + KEY_VOTED + " TEXT"+")";
        db.execSQL(CREATE_REGISTER_TABLE);
    }

    //Upgrading database
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion)
    {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_REGISTER;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //Adding new row
    public void addRegister(RegisterDB registerDB)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, registerDB.get_name());
        contentValues.put(KEY_AADHAR, registerDB.get_aadharno());
        contentValues.put(KEY_VOTER, registerDB.get_voterid());
        contentValues.put(KEY_PASSWORD, registerDB.get_password());
        contentValues.put(KEY_USERNAME, registerDB.get_username());
        contentValues.put(KEY_STATE, "T");
        contentValues.put(KEY_DISTRICT, "T");
        contentValues.put(KEY_PARTY, "T");
        contentValues.put(KEY_VOTED, "no");

        //Adding
        db.insert(TABLE_REGISTER, null, contentValues);
        db.close();
    }

    //check if he is registered or not
    public boolean checkUser(String username, String password) throws SQLException
    {
        db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?", new String[]{username,password});
        if (mcursor!=null)
        {
            if (mcursor.getCount()>0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkUserRegistered(String username) throws SQLException
    {
        db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        if (mcursor!=null)
        {
            if (mcursor.getCount()>0)
            {
                return true;
            }
        }
        return false;
    }

    public int retrieveid(String username)
    {
        db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        if (mcursor!=null)
        {
            int temp = mcursor.getPosition()+1;
            db.close();
            return temp;

        }
        else
        {
            db.close();
            return -1;

        }

    }

    public void retrievename(String username)
    {
        db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        while(mcursor.moveToNext())
        {
            temp = mcursor.getString(mcursor.getColumnIndex(KEY_NAME));
        }
        db.close();
    }

    public boolean checkvoted(String username)
    {
        String voted = "F";
        db = this.getReadableDatabase();
        Cursor mcursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + KEY_USERNAME + "=?", new String[]{username});
        while(mcursor.moveToNext())
        {
            voted = mcursor.getString(mcursor.getColumnIndex(KEY_VOTED));
        }
        db.close();
        if(voted.equals("no"))
        {
            return false;
        }
        else {
            return true;
        }
    }

    public void updateaftervote(int id, String state, String district, String party, String voted)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATE, state);
        contentValues.put(KEY_DISTRICT, district);
        contentValues.put(KEY_PARTY, party);
        contentValues.put(KEY_VOTED, "yes");
        db.update(TABLE_REGISTER, contentValues, "id="+id, null);
        db.close();
    }

    //code to get the list of all rows
    public List<RegisterDB> getAllRegister()
    {
        List<RegisterDB> registerList = new ArrayList<RegisterDB>();
        //Select all
        String selectQuery = "SELECT * FROM " + TABLE_REGISTER;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                RegisterDB registerDB = new RegisterDB();
                registerDB.setId(Integer.parseInt(cursor.getString(0)));
                registerDB.set_name(cursor.getString(1));
                registerDB.set_aadharno(cursor.getString(2));
                registerDB.set_voterid(cursor.getString(3));
                registerDB.set_username(cursor.getString(4));
                registerDB.set_password(cursor.getString(5));
                registerList.add(registerDB);
            }
            while (cursor.moveToNext());
        }
        return registerList;
    }
}
