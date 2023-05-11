package com.luminate.crudattempt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private TextView login_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.EditTextusername);
        editTextPassword = findViewById(R.id.EditTextPassword);
        editTextConfirmPassword = findViewById(R.id.EditTextConfirmPassword);
        registerBtn = findViewById(R.id.idBtnRegister);
        login_tv = findViewById(R.id.loginTV);
        mAuth = FirebaseAuth.getInstance();


        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmpass = editTextConfirmPassword.getText().toString();

                if (!password.equals(confirmpass))
                {
                    Toast.makeText(RegisterActivity.this, "Please make sure the both password are the same", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(username)&& TextUtils.isEmpty(confirmpass) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this, "Please fill all the credential needed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "New User Have Been Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Failed To register new User", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}