package com.example.madvoting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button Registerbt,login;
    private int TIME_OUT = 4000;
    EditText username, password;
    String transname;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.login);
        Registerbt = (Button)findViewById(R.id.regbt);
        final Intent votingintent = new Intent(Login.this, Voting.class);
        final Bundle votingextras = new Bundle();
        final Intent intent = new Intent(Login.this, Register.class);
        final RegisterDBHandler dbHandler = new RegisterDBHandler(this);
        username = (EditText)findViewById(R.id.uname);
        password = (EditText)findViewById(R.id.pass);
        Registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((dbHandler.checkvoted(username.getText().toString())))
                {
                    Toast.makeText(getApplicationContext(), "YOU ALREADY VOTED", Toast.LENGTH_LONG).show();
                }
                else if (dbHandler.checkUser(username.getText().toString(), password.getText().toString()))
                {
                    dbHandler.retrievename(username.getText().toString());
                    transname = dbHandler.temp;
                    votingextras.putString("Name", transname);
                    votingextras.putString("UserName", username.getText().toString());
                    Toast.makeText(getApplicationContext(), "LOGIN SUCCESSFUL" + transname, Toast.LENGTH_LONG).show();
                    votingintent.putExtras(votingextras);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(votingintent);
                        }
                    },TIME_OUT);

                } else
                {
                    Toast.makeText(getApplicationContext(), "NOT SUCCESSFUL", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
