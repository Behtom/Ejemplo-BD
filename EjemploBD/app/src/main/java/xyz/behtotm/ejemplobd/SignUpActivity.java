package xyz.behtotm.ejemplobd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUp;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.activity_signup_email);
        password = (EditText)findViewById(R.id.activity_signup_password);
        signUp = (Button)findViewById(R.id.activity_signup_button);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(email.getText().toString().trim().equals("") && password.getText().toString().trim().equals("")) {
            email.setError("Por favor ingresa tu correo electrónico.");
            password.setError("Por favor ingresa tu contraseña.");
        } else if(email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
            email.setError("Por favor ingresa tu correo electrónico.");
        } else if(!email.getText().toString().trim().equals("") && password.getText().toString().trim().equals("")) {
            password.setError("Por favor ingresa tu contraseña.");
        } else if(!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
            firebaseAuthWithEmail();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent main = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(main);
        finish();
    }

    public void firebaseAuthWithEmail(){

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,"El registro de tu usuario falló. Inténtalo más tarde.",Toast.LENGTH_LONG).show();
                            task.getException().printStackTrace();
                        } else {
                            try {
                                Toast.makeText(SignUpActivity.this, "El registro de tu usuario se realizó exitosamente.", Toast.LENGTH_LONG).show();

                                Intent main = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(main);
                                finish();
                            } catch (Exception e) {

                                Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
