package com.example.sushilsingh.glaco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sushilsingh.glaco.Common.Common;
import com.example.sushilsingh.glaco.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtRoll, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtRoll = (MaterialEditText)findViewById(R.id.edtRoll);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Initialize Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Common.isNetworkAvailable(getBaseContext())) {

                    if (edtRoll.getText().toString().trim().length() == 0)
                        Toast.makeText(SignIn.this, "Roll No. can't be left blank", Toast.LENGTH_SHORT).show();
                    else if (edtPassword.getText().toString().trim().length() == 0)
                        Toast.makeText(SignIn.this, "Password can't be left blank", Toast.LENGTH_SHORT).show();
                    else {
                        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                        mDialog.setMessage("Please Wait...");
                        mDialog.show();

                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //Check if user doesn't exist in database
                                if (dataSnapshot.child(edtRoll.getText().toString()).exists()) {
                                    //Get_User_Info
                                    mDialog.dismiss();
                                    User user = dataSnapshot.child(edtRoll.getText().toString()).getValue(User.class);
                                    user.setRoll(edtRoll.getText().toString());  //set Roll
                                    if (user.getPassword().equals(edtPassword.getText().toString())) {
                                        Intent homeIntent = new Intent(SignIn.this, Home.class);
                                        Common.currentUser = user;
                                        startActivity(homeIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignIn.this, "Wrong Password !", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(SignIn.this, "User doesn't exist in Database !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
                else
                {
                    Toast.makeText(SignIn.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
