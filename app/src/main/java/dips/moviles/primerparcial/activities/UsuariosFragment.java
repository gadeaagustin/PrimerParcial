package dips.moviles.primerparcial.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import dips.moviles.primerparcial.DB.DatabaseHelper;
import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.Usuarios.UsuariosCursorAdapter;

public class UsuariosFragment extends Fragment {

    private DatabaseHelper mUsuariosDbHelper;

    private ListView mUsuariosList;
    private UsuariosCursorAdapter mUsuariosAdapter;
    private FloatingActionButton mAddButton;

    public UsuariosFragment() {
        // Required empty public constructor
    }

    public static UsuariosFragment newInstance() {
        return new UsuariosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);

        // Referencias UI
        mUsuariosList = (ListView) root.findViewById(R.id.lawyers_list);
        mUsuariosAdapter = new UsuariosCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mUsuariosList.setAdapter(mUsuariosAdapter);

        // Instancia de helper
        mUsuariosDbHelper = new DatabaseHelper(getActivity());

        // Carga de datos
        loadUsuarios();

        return root;
    }

    private void loadUsuarios() {
        new UsuariosLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private class UsuariosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUsuariosDbHelper.getAllLawyers();
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
