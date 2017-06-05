package xyz.behtotm.ejemplobd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signIn;
    private Button signUp;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogged();

        signIn = (Button)findViewById(R.id.activity_main_signin);
        signIn.setOnClickListener(this);
        signUp = (Button)findViewById(R.id.activity_main_signup);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.activity_main_signin:

                Intent intentSignIn = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intentSignIn);
                finish();
                break;

            case R.id.activity_main_signup:

                Intent intentSignUp = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intentSignUp);
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

    public void isLogged(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent homeActivity = new Intent(MainActivity.this,FormActivity.class);
                    startActivity(homeActivity);
                    finish();

                }
            }
        };
    }
}
