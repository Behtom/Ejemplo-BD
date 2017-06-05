package xyz.behtotm.ejemplobd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity implements View.OnClickListener {

    private Button search;
    private List<String> id = new ArrayList<>();
    private Spinner dropdown;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        fetchId();

        ArrayAdapter adapter = new ArrayAdapter(ShowDataActivity.this, android.R.layout.simple_spinner_item, id);

        data = (TextView)findViewById(R.id.activity_showdata_data);
        dropdown = (Spinner)findViewById(R.id.activity_showdata_spinner);
        dropdown.setAdapter(adapter);
        search = (Button)findViewById(R.id.activity_showdata_search);
        search.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menu_add:

                Intent addData = new Intent(ShowDataActivity.this,FormActivity.class);
                startActivity(addData);
                finish();
                break;

            case R.id.menu_show:

                Intent showData = new Intent(ShowDataActivity.this,ShowDataActivity.class);
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
        try {
            String item = dropdown.getSelectedItem().toString();
            EjemploBD db = new EjemploBD(ShowDataActivity.this);
            Toast.makeText(this, db.obtener(4), Toast.LENGTH_SHORT).show();
            data.setText(db.obtener(4));
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "No has seleccionado ninguna opción.", Toast.LENGTH_SHORT).show();
            EjemploBD db = new EjemploBD(ShowDataActivity.this);
            data.setText(db.obtener(4));
            Toast.makeText(this, db.obtener(4), Toast.LENGTH_SHORT).show();
        }

    }

    public void fetchId(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query idQuery = reference.orderByKey();
        idQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data : dataSnapshot.getChildren())
                        id.add(data.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void logout(){
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ShowDataActivity.this);
        alertDialogBuilderUserInput.setMessage("¿Estás seguro que deseas cerrar sesión?");

        alertDialogBuilderUserInput.setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogBox, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(ShowDataActivity.this,MainActivity.class);
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
