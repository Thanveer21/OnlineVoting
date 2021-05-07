package com.example.madvoting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Voting extends AppCompatActivity {

    Button submit;
    private int TIME_OUT=4000;
    Spinner state, district, party;
    TextView welcome;
    String selectedstate;
    String selecteddistrict;
    String selectedparty;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);
        Intent intent = getIntent();
        final Intent gobackintent = new Intent(Voting.this, Login.class);
        Bundle bundle = intent.getExtras();
        String fullname = bundle.getString("Name");
        final String username = bundle.getString("UserName");
        String welcometext = "WELCOME \n" + fullname + "\n";
        submit = (Button) findViewById(R.id.submit);
        state = (Spinner) findViewById(R.id.statespinner);
        district = (Spinner) findViewById(R.id.districtspinner);
        party = (Spinner) findViewById(R.id.partyspinner);
        welcome = (TextView) findViewById(R.id.user);
        welcome.setText(welcometext);
        final String[] states_list = getResources().getStringArray(R.array.States_list);
        final RegisterDBHandler dbHandler = new RegisterDBHandler(this);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedstate = adapterView.getItemAtPosition(i).toString();
                if (selectedstate.equals(states_list[0]))
                {
                    district.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.district_1)));
                    party.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.party_state_1)));
                }
                if (selectedstate.equals(states_list[1]))
                {
                    district.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.district_2)));
                    party.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.party_state_2)));
                }
                if (selectedstate.equals(states_list[2]))
                {
                    district.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.district_3)));
                    party.setAdapter(new ArrayAdapter<String>(Voting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.party_state_3)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selecteddistrict = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        party.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedparty = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = dbHandler.retrieveid(username);
                id = id + 1;
                dbHandler.updateaftervote( id,selectedstate,selecteddistrict,selectedparty," yes");
                Toast.makeText(getApplicationContext(), "VOTED SUCCESSFULLY" + id, Toast.LENGTH_LONG).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(gobackintent);
                    }
                },TIME_OUT);
            }
        });
    }
}