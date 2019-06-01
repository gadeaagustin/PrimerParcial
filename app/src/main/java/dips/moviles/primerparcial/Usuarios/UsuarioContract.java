package dips.moviles.primerparcial.Usuarios;

import android.provider.BaseColumns;

public class UsuarioContract {
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME ="user";

        public static final String ID = "id";
        public static final String NAME = "nombre";
        public static final String AGE = "edad";
        public static final String GENDER = "genero";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String AVATAR_URI = "AvatarUri";
    }
}
