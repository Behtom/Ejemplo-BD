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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.activity_signin_email);
        password = (EditText)findViewById(R.id.activity_signin_password);
        signIn = (Button)findViewById(R.id.activity_signin_button);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

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
        Intent main = new Intent(SignInActivity.this,MainActivity.class);
        startActivity(main);
        finish();
    }

    private void firebaseAuthWithEmail(){

        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "El inicio de sesión de tu cuenta falló. Inténtalo más tarde.", Toast.LENGTH_LONG).show();
                            task.getException().printStackTrace();
                        } else {
                            Intent form = new Intent(SignInActivity.this,FormActivity.class);
                            startActivity(form);
                            finish();
                        }
                    }
                });
    }
}
