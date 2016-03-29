package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.ContentValues;
import java.util.ArrayList;
import android.app.Activity;
import android.provider.ContactsContract;
import android.widget.Toast;

public class ContactsManagerActivity extends AppCompatActivity {
    private Button save = null;
    private Button cancel= null;
    private EditText nameEditText,phone_numberEditText,emailEditText,addressEditText,possitionEditText,company_nameEditText,web_siteEditText,imEditText;
    private Button show_additional_fieldsButton = null;
    private LinearLayout show_additional_fieldsLinearLayout = null;
    private showButtonListener show_hide_listener = new showButtonListener();
    private saveButtonListener saveButtonListener = new saveButtonListener();
    private cancelButtonListener cancelButtonListener = new cancelButtonListener();

    private class showButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (show_additional_fieldsLinearLayout.getVisibility()){
                case View.VISIBLE:
                    show_additional_fieldsLinearLayout.setVisibility(View.INVISIBLE);
                    show_additional_fieldsButton.setText(getResources().getString(R.string.show_additional_fields));
                    break;
                case View.INVISIBLE:
                    show_additional_fieldsLinearLayout.setVisibility(View.VISIBLE);
                    show_additional_fieldsButton.setText(getResources().getString(R.string.hide_additional_fields));
                    break;
            }

        }
    }

    private  class saveButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            startActivityForResult(intent, 1);
            String name = nameEditText.getText().toString();
            String phoneNumber = phone_numberEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String jobTitle = possitionEditText.getText().toString();
            String company = company_nameEditText.getText().toString();
            String website = web_siteEditText.getText().toString();
            String im = imEditText.getText().toString();

            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phoneNumber != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            startActivityForResult(intent, 1);

        }
    }

    private  class cancelButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            setResult(Activity.RESULT_CANCELED, new Intent());
            finish();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(saveButtonListener);
        cancel= (Button)findViewById(R.id.cancelButton);
        cancel.setOnClickListener(cancelButtonListener);
        show_additional_fieldsButton = (Button)findViewById(R.id.show_hide_additional_fieldsButton);
        show_additional_fieldsButton.setOnClickListener(show_hide_listener);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        phone_numberEditText = (EditText)findViewById(R.id.phone_numberEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        addressEditText = (EditText)findViewById(R.id.addressEditText);
        possitionEditText = (EditText)findViewById(R.id.possitionEditText);
        company_nameEditText = (EditText)findViewById(R.id.company_nameEditText);
        web_siteEditText= (EditText)findViewById(R.id.web_siteEditText);
        imEditText = (EditText)findViewById(R.id.imEditText);

        show_additional_fieldsLinearLayout = (LinearLayout)findViewById(R.id.additional_fieldsLinearLayout);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phone_numberEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_number_error), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_manager, menu);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 1:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
