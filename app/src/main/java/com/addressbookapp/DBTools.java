package com.addressbookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by relly on 7/6/2014.
 */
public class DBTools extends SQLiteOpenHelper {

    public DBTools(Context context) {
        super(context, "contactbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE contacts (contactId INTEGER PRIMARY KEY, "+
                        "firstName TEXT, lastName TEXT, phoneNumber TEXT, emailAddress TEXT, " +
                        "homeAddress TEXT, imageId BIGINT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS contacts";
        db.execSQL(query);

        onCreate(db);
    }


    public void insertContact(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", queryValues.get("firstName"));
        values.put("lastName", queryValues.get("lastName"));
        values.put("phoneNumber", queryValues.get("phoneNumber"));
        values.put("emailAddress", queryValues.get("emailAddress"));
        values.put("homeAddress", queryValues.get("homeAddress"));
        values.put("imageId", queryValues.get("imageId"));

        db.insert("contacts", null, values);
        db.close();
    }


    public int updateContact(HashMap<String, String> queryValues) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", queryValues.get("firstName"));
        values.put("lastName", queryValues.get("lastName"));
        values.put("phoneNumber", queryValues.get("phoneNumber"));
        values.put("emailAddress", queryValues.get("emailAddress"));
        values.put("homeAddress", queryValues.get("homeAddress"));
        values.put("imageId", queryValues.get("imageId"));

        return db.update("contacts",
                            values,
                            "contactId" + " = ?",
                            new String[]{queryValues.get("contactId")});
    }


    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM contacts WHERE contactId='" + id + "'";
        db.execSQL(query);
    }


    public ArrayList<HashMap<String, String>> getAllContacts() {
        ArrayList<HashMap<String, String>> contactArrayList = new ArrayList<HashMap<String, String>>();

        String query = "SELECT contactid,firstname,lastname,phonenumber,emailaddress,homeaddress,imageid " +
                        "FROM contacts ORDER BY lastname";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contactMap = new HashMap<String, String>();
                contactMap.put("contactId", cursor.getString(0));
                contactMap.put("firstName", cursor.getString(1));
                contactMap.put("lastName", cursor.getString(2));
                contactMap.put("phoneNumber", cursor.getString(3));
                contactMap.put("emailAddress", cursor.getString(4));
                contactMap.put("homeAddress", cursor.getString(5));
                contactMap.put("imageId", cursor.getString(6));

                contactArrayList.add(contactMap);
            } while (cursor.moveToNext());
        }
        return contactArrayList;
    }


    public HashMap<String, String> getContactInfo(String id) {
        HashMap<String, String> contactMap = new HashMap<String, String>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT contactid, firstname, lastname, phonenumber, emailaddress, homeaddress, imageid " +
                        "FROM contacts WHERE contactid = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            //do {
                contactMap.put("contactId", cursor.getString(0));
                contactMap.put("firstName", cursor.getString(1));
                contactMap.put("lastName", cursor.getString(2));
                contactMap.put("phoneNumber", cursor.getString(3));
                contactMap.put("emailAddress", cursor.getString(4));
                contactMap.put("homeAddress", cursor.getString(5));
                contactMap.put("imageId", cursor.getString(6));
            //} while (cursor.moveToNext());
        }
        return contactMap;
    }
}
