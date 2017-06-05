package xyz.behtotm.ejemplobd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private Button save;
    private DatabaseReference reference;
    private EditText nombre;
    private EditText apellidos;
    private EditText email;
    private EditText telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        reference = FirebaseDatabase.getInstance().getReference();

        nombre = (EditText)findViewById(R.id.activity_form_name);
        apellidos = (EditText)findViewById(R.id.activity_form_lastname);
        email = (EditText)findViewById(R.id.activity_form_email);
        telefono = (EditText)findViewById(R.id.activity_form_phone);
        save = (Button)findViewById(R.id.activity_form_save);
        save.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menu_show:

                Intent showData = new Intent(FormActivity.this,ShowDataActivity.class);
                startActivity(showData);
                finish();
                break;

            case R.id.menu_logout:

                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            apellidos.setText("Ingresa tus apellidos");
            email.setText("Ingresa tu correo electrónico");
            telefono.setText("Ingresa tu telefono");
        } else if(nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
        } else if(!nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            apellidos.setText("Ingresa tus apellidos");
        } else if(!nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            email.setText("Ingresa tu correo electrónico");
        } else if(!nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            telefono.setText("Ingresa tu telefono");
        } else if(nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            apellidos.setText("Ingresa tus apellidos");
        } else if(nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            email.setText("Ingresa tu correo electrónico");
        } else if(nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            telefono.setText("Ingresa tu telefono");
        } else if(nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            apellidos.setText("Ingresa tus apellidos");
            email.setText("Ingresa tu correo electrónico");
        } else if(nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            apellidos.setText("Ingresa tus apellidos");
            telefono.setText("Ingresa tu telefono");
        } else if(nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            nombre.setText("Ingresa tu nombre");
            email.setText("Ingresa tu correo electrónico");
            telefono.setText("Ingresa tu telefono");
        } else if(!nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            apellidos.setText("Ingresa tus apellidos");
            email.setText("Ingresa tu correo electrónico");
            telefono.setText("Ingresa tu telefono");
        } else if(!nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            apellidos.setText("Ingresa tus apellidos");
            email.setText("Ingresa tu correo electrónico");
        } else if(!nombre.getText().toString().trim().equals("") && apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            apellidos.setText("Ingresa tus apellidos");
            telefono.setText("Ingresa tu telefono");
        } else if(!nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && email.getText().toString().trim().equals("") && telefono.getText().toString().trim().equals("")) {
            email.setText("Ingresa tu correo electrónico");
            telefono.setText("Ingresa tu telefono");
        } else if(!nombre.getText().toString().trim().equals("") && !apellidos.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !telefono.getText().toString().trim().equals("")) {
            saveData();
        }
    }

    public void saveData(){

        EjemploBD db = new EjemploBD(FormActivity.this);
        int id = db.agregar(nombre.getText().toString(), apellidos.getText().toString(), email.getText().toString(), telefono.getText().toString());

        HashMap<String,Object> values = new HashMap<>();
        values.put("nombre",nombre.getText().toString());
        values.put("apellidos",apellidos.getText().toString());
        values.put("email",email.getText().toString());
        values.put("telefono",telefono.getText().toString());

        reference.child("" + id).updateChildren(values)
                .addOnCompleteListener(FormActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FormActivity.this, "Tus datos se han guardado exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logout(){
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(FormActivity.this);
        alertDialogBuilderUserInput.setMessage("¿Estás seguro que deseas cerrar sesión?");

        alertDialogBuilderUserInput.setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogBox, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(FormActivity.this,MainActivity.class);
                        startActivity(logout);
                        finish();
                        dialogBox.dismiss();
                    }
                })

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialogBox, int id) {

                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
