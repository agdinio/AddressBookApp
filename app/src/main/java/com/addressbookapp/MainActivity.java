package com.addressbookapp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.AdapterView.*;


public class MainActivity extends ListActivity {

    private Intent intent;
    private TextView contactId;
    DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, String>> contactList = dbTools.getAllContacts();
        if (contactList.size() > 0) {

            ListView listView = getListView();
            listView.setOnItemClickListener(onItemClickListenerListView);

            ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.contact_enty,
                                                        new String[]{"imageId", "contactId","lastName","firstName"},
                                                        new int[]{R.id.imageId, R.id.contactId, R.id.lastName, R.id.firstName});

            setListAdapter(listAdapter);
        }
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


    private OnItemClickListener onItemClickListenerListView = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            contactId = (TextView) view.findViewById(R.id.contactId);
            String contactIdValue = contactId.getText().toString();

            Intent editContactIntent = new Intent(getApplication(), EditContact.class);
            editContactIntent.putExtra("contactId", contactIdValue);
            startActivity(editContactIntent);
        }
    };

    public void showAddContact(View view) {
        Intent addContactIntent = new Intent(getApplication(), NewContact.class);
        startActivity(addContactIntent);
    }
}
