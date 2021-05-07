package com.example.madvoting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    Button register;
    private static int TIME_OUT=4000;
    private Handler handler = new Handler();
    EditText name,ano,voterid,uname,pass,cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button)findViewById(R.id.register);
        name = (EditText)findViewById(R.id.name);
        ano = (EditText)findViewById(R.id.ano);
        voterid = (EditText)findViewById(R.id.voterid);
        uname = (EditText)findViewById(R.id.uname);
        pass = (EditText)findViewById(R.id.pass);
        cpass = (EditText)findViewById(R.id.cpass);
        final RegisterDBHandler dbHandler = new RegisterDBHandler(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().equals(cpass.getText().toString())) {
                    if(!(dbHandler.checkUserRegistered(uname.getText().toString()))) {
                        TransferToDB();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "ALREADY REGISTERED", Toast.LENGTH_LONG).show();
                        name.setText("");
                        ano.setText("");
                        voterid.setText("");
                        uname.setText("");
                        pass.setText("");
                        cpass.setText("");
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"PASSWORD DONT MATCH", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void TransferToDB()
    {
        RegisterDBHandler dbHandler = new RegisterDBHandler(this);
        dbHandler.addRegister(new RegisterDB(name.getText().toString(),ano.getText().toString(), voterid.getText().toString(),uname.getText().toString(),pass.getText().toString()));
        Toast.makeText(getApplicationContext(),"ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        },TIME_OUT);
    }
}
