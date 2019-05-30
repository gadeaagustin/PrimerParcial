package dips.moviles.primerparcial.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dips.moviles.primerparcial.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UsuariosFragment fragment = (UsuariosFragment)
                getSupportFragmentManager().findFragmentById(R.id.usuarios_container);

        if (fragment == null) {
            fragment = UsuariosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.usuarios_container, fragment)
                    .commit();
        }
    }

}
