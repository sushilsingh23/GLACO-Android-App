package com.example.sushilsingh.glaco;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sushilsingh.glaco.Common.Common;
import com.example.sushilsingh.glaco.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText edtRoll,edtName,edtPassword,edtConfirmPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtRoll = (MaterialEditText)findViewById(R.id.edtRoll);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtConfirmPassword= (MaterialEditText)findViewById(R.id.edtConfirmPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Initialize Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Common.isNetworkAvailable(getBaseContext())) {

                    if (edtRoll.getText().toString().trim().length() != 9)
                        Toast.makeText(SignUp.this, "Roll No. must have 9 characters!", Toast.LENGTH_SHORT).show();
                    else if (edtName.getText().toString().trim().length() < 3)
                        Toast.makeText(SignUp.this, "Name is too short!", Toast.LENGTH_SHORT).show();
                    else if (edtPassword.getText().toString().trim().length() < 8)
                        Toast.makeText(SignUp.this, "Password must have atleast 8 characters!", Toast.LENGTH_SHORT).show();
                    else if (!(edtPassword.getText().toString()).equals(edtConfirmPassword.getText().toString()))
                        Toast.makeText(SignUp.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    else {

                        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                        mDialog.setMessage("Please Wait...");
                        mDialog.show();

                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Check if userRoll already exist
                                if (dataSnapshot.child(edtRoll.getText().toString()).exists()) {
                                    mDialog.dismiss();
                                    Toast.makeText(SignUp.this, "User already registered!", Toast.LENGTH_SHORT).show();
                                } else {
                                    mDialog.dismiss();
                                    User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                    table_user.child(edtRoll.getText().toString()).setValue(user);
                                    Toast.makeText(SignUp.this, "Sign up Successfull!", Toast.LENGTH_SHORT).show();
                                    finish();
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
                    Toast.makeText(SignUp.this, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
