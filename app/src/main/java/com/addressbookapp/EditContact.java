package com.addressbookapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.addressbookapp.R;

import java.util.HashMap;
import java.util.HashSet;

public class EditContact extends ActionBarActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText emailAddress;
    private EditText homeAddress;

    private DBTools dbTools = new DBTools(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        homeAddress = (EditText) findViewById(R.id.homeAddress);

        fillInfo();
    }


    private void fillInfo() {
        Intent mainActivityIntent = getIntent();

        String contactId = mainActivityIntent.getStringExtra("contactId");
        HashMap<String, String> contactMap = dbTools.getContactInfo(contactId);
        if (contactMap.size() > 0) {
            firstName.setText(contactMap.get("firstName"));
            lastName.setText(contactMap.get("lastName"));
            phoneNumber.setText(contactMap.get("phoneNumber"));
            emailAddress.setText(contactMap.get("emailAddress"));
            homeAddress.setText(contactMap.get("homeAddress"));
        }
    }

    public void editContact(View view) {
        HashMap<String, String> queryValuesMap = new HashMap<String, String>();

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        homeAddress = (EditText) findViewById(R.id.homeAddress);

        Intent mainActivityIntent = getIntent();
        String contactId = mainActivityIntent.getStringExtra("contactId");

        queryValuesMap.put("contactId", contactId);
        queryValuesMap.put("firstName", firstName.getText().toString());
        queryValuesMap.put("lastName", lastName.getText().toString());
        queryValuesMap.put("phoneNumber", phoneNumber.getText().toString());
        queryValuesMap.put("emailAddress", emailAddress.getText().toString());
        queryValuesMap.put("homeAddress", homeAddress.getText().toString());
        queryValuesMap.put("imageId", Integer.toString(R.drawable.ic_launcher));

        dbTools.updateContact(queryValuesMap);

        this.callMainActivity(view);
    }


    public void removeContact(View view) {
        Intent editContactIntent = getIntent();
        String contactId = editContactIntent.getStringExtra("contactId");

        dbTools.deleteContact(contactId);
        this.callMainActivity(view);
    }


    private void callMainActivity(View view) {
        Intent mainActivityIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_contact, menu);
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
}
