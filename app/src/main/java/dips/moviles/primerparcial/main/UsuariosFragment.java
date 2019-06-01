package dips.moviles.primerparcial.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import dips.moviles.primerparcial.DB.DatabaseHelper;
import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.Usuarios.UsuarioContract.UserEntry;
import dips.moviles.primerparcial.Usuarios.UsuariosCursorAdapter;
import dips.moviles.primerparcial.add_edit.AddEditUsuarioActivity;
import dips.moviles.primerparcial.detalle_usuario.DetalleUsuarioActivity;

public class UsuariosFragment extends Fragment {

    private DatabaseHelper mUsuariosDbHelper;

    private ListView mUsuariosList;
    private UsuariosCursorAdapter mUsuariosAdapter;
    private FloatingActionButton mAddButton;

    public static final int REQUEST_UPDATE_DELETE_USER = 2;

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
        mUsuariosList = (ListView) root.findViewById(R.id.usuarios_list);
        mUsuariosAdapter = new UsuariosCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mUsuariosList.setAdapter(mUsuariosAdapter);

        // Eventos
        mUsuariosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mUsuariosAdapter.getItem(i);
                String currentUserId = currentItem.getString(
                        currentItem.getColumnIndex(UserEntry.ID));

                showDetailScreen(currentUserId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });



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
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditUsuarioActivity.REQUEST_ADD_USER:
                    showSuccessfullSavedMessage();
                    loadUsuarios();
                    break;
                case REQUEST_UPDATE_DELETE_USER:
                    loadUsuarios();
                    break;
            }
        }
    }
    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditUsuarioActivity.class);
        startActivityForResult(intent, AddEditUsuarioActivity.REQUEST_ADD_USER);
    }

    private void showDetailScreen(String userId) {
        Intent intent = new Intent(getActivity(), DetalleUsuarioActivity.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID,userId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_USER);
    }


    private class UsuariosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUsuariosDbHelper.getAllUser();
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
