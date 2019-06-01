package dips.moviles.primerparcial.add_edit;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import dips.moviles.primerparcial.DB.DatabaseHelper;
import dips.moviles.primerparcial.R;
import dips.moviles.primerparcial.Helpers.InputValidation;
import dips.moviles.primerparcial.Usuarios.Usuario;

public class AddEditUsuarioFragment  extends Fragment {
    private static final String ARG_USER_ID = "arg_user_id";

    private String mUserId;

    private DatabaseHelper mUserDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutAge;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextAge;
    private RadioGroup RadioGroupGender;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private String genero ="Hombre";
    private InputValidation inputValidation;
    private Usuario user;

    public AddEditUsuarioFragment() {
        // Required empty public constructor
    }

    public static AddEditUsuarioFragment newInstance(String userId) {
        AddEditUsuarioFragment fragment = new AddEditUsuarioFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addedit_usuario, container, false);


        // Referencias UI
        initViews();
        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditLawyer();
            }
        });

        mUserDbHelper = new DatabaseHelper(getActivity());

        // Carga de datos
        if (mUserId != null) {
            loadLawyer();
        }

        return root;
    }

    private void initViews(){
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        textInputLayoutName = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutName);
        textInputLayoutAge = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutAge);
        textInputLayoutEmail = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutEmail);
        textInputLayoutEmail = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) getActivity().findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = (TextInputEditText) getActivity().findViewById(R.id.textInputEditTextName);
        textInputEditTextAge = (TextInputEditText) getActivity().findViewById(R.id.textInputEditTextAge);
        textInputEditTextEmail = (TextInputEditText) getActivity().findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) getActivity().findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) getActivity().findViewById(R.id.textInputEditTextConfirmPassword);

        RadioGroupGender = (RadioGroup) getActivity().findViewById(R.id.RadioGroupGender);
    }

    private void initListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditLawyer();
            }
        });
        RadioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.RadioHombre) genero = "Hombre";
                if(i == R.id.RadioMujer) genero = "Mujer";
                if(i == R.id.RadioOtro) genero = "Otro";
            }
        });
    }



    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    private void addEditLawyer() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextAge, textInputLayoutAge, getString(R.string.error_message_age))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!mUserDbHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setNombre(textInputEditTextName.getText().toString().trim());
            user.setEdad(textInputEditTextAge.getText().toString().trim());
            user.setGenero(genero);
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            user.setAvatarUri("");

            new AddEditUserTask().execute(user);

        }
    }

    private void showUsersScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showUser(Usuario usuario) {
        textInputEditTextName.setText(usuario.getNombre());
        textInputEditTextAge.setText(usuario.getEdad());
        textInputEditTextEmail.setText(usuario.getEmail());
        textInputEditTextPassword.setText(usuario.getPassword());
        String genero = usuario.getGenero();
        if (genero =="Hombre"){
            RadioButton hombre = (RadioButton) RadioGroupGender.getChildAt(0);
            hombre.setChecked(true);

        }else if (genero == "Mujer"){
            RadioButton mujer = (RadioButton) RadioGroupGender.getChildAt(1);
            mujer.setChecked(true);
        }else{
            RadioButton otro = (RadioButton) RadioGroupGender.getChildAt(2);
            otro.setChecked(true);
        }

    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

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
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditUserTask extends AsyncTask<Usuario, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Usuario... lawyers) {
            if (mUserId != null) {
                return mUserDbHelper.updateUser(lawyers[0], mUserId) > 0;

            } else {
                return mUserDbHelper.saveUser(lawyers[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showUsersScreen(result);
        }

    }

}
