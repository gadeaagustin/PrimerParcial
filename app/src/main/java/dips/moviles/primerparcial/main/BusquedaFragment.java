package dips.moviles.primerparcial.main;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import dips.moviles.primerparcial.DB.DatabaseHelper;
import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.Usuarios.UsuariosCursorAdapter;

public class BusquedaFragment extends Fragment {
    private DatabaseHelper mUsuariosDbHelper;

    private ListView mBusquedaList;
    private UsuariosCursorAdapter mUsuariosAdapter;
    private RadioGroup RadioGroupGender;
    private String genero;

    public static BusquedaFragment newInstance() {
        return new BusquedaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_busqueda, container, false);

        // Referencias UI
        mBusquedaList = (ListView) root.findViewById(R.id.busqueda_list);
        mUsuariosAdapter = new UsuariosCursorAdapter(getActivity(), null);
        RadioGroupGender = (RadioGroup) root.findViewById(R.id.RadioGroupGender);

        // Setup
        mBusquedaList.setAdapter(mUsuariosAdapter);

        // Instancia de helper
        mUsuariosDbHelper = new DatabaseHelper(getActivity());

        RadioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.RadioHombre){
                    genero = "Hombre";
                    new UsuariosBusquedaTask().execute();
                }
                if (i == R.id.RadioMujer){
                    genero = "Mujer";
                    new UsuariosBusquedaTask().execute();
                }
                if (i == R.id.RadioOtro){
                    genero = "Otro";
                    new UsuariosBusquedaTask().execute();
                }
            }
        });
        // Carga de datos
        //loadUsuarios();

        return root;
    }


    private class UsuariosBusquedaTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUsuariosDbHelper.getUsersByGender(genero);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mUsuariosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
