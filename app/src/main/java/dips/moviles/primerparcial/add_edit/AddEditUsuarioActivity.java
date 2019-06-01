package dips.moviles.primerparcial.add_edit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.add_edit.AddEditUsuarioFragment;
import dips.moviles.primerparcial.main.MainActivity;

public class AddEditUsuarioActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_USER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String lawyerId = getIntent().getStringExtra(MainActivity.EXTRA_USER_ID);

        setTitle(lawyerId == null ? "Añadir abogado" : "Editar abogado");

        AddEditUsuarioFragment addEditUsuarioFragment = (AddEditUsuarioFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_user_container);
        if (addEditUsuarioFragment == null) {
            addEditUsuarioFragment = AddEditUsuarioFragment.newInstance(lawyerId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_user_container, addEditUsuarioFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
