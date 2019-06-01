package dips.moviles.primerparcial.detalle_usuario;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dips.moviles.primerparcial.DB.DatabaseHelper;
import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.Usuarios.Usuario;
import dips.moviles.primerparcial.add_edit.AddEditUsuarioActivity;
import dips.moviles.primerparcial.main.MainActivity;
import dips.moviles.primerparcial.main.UsuariosFragment;

public class DetalleUsuarioFragment extends Fragment {
    private static final String ARG_USER_ID = "userId";

    private String mUserId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mEdad;
    private TextView mEmail;
    private TextView mGenero;

    private DatabaseHelper mUserDbHelper;


    public DetalleUsuarioFragment() {
        // Required empty public constructor
    }

    public static DetalleUsuarioFragment newInstance(String userId) {
        DetalleUsuarioFragment fragment = new DetalleUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUserId = getArguments().getString(ARG_USER_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_usuario, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mEdad = (TextView) root.findViewById(R.id.tv_edad);
        mEmail = (TextView) root.findViewById(R.id.tv_email);
        mGenero = (TextView) root.findViewById(R.id.tv_genero);

        mUserDbHelper = new DatabaseHelper(getActivity());

        loadLUser();

        return root;
    }

    private void loadLUser() {
        new GetUserByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteLawyerTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UsuariosFragment.REQUEST_UPDATE_DELETE_USER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showUser(Usuario usuario) {
        mCollapsingView.setTitle(usuario.getNombre());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + usuario.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mEdad.setText(usuario.getEdad());
        mGenero.setText(usuario.getGenero());
        mEmail.setText(usuario.getEmail());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditUsuarioActivity.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID, mUserId);
        startActivityForResult(intent, UsuariosFragment.REQUEST_UPDATE_DELETE_USER);
    }

    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetUserByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mUserDbHelper.getUserById(mUserId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showUser(new Usuario(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mUserDbHelper.deleteUser(mUserId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

}
