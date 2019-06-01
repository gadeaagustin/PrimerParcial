package dips.moviles.primerparcial.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dips.moviles.primerparcial.Usuarios.Usuario;
import dips.moviles.primerparcial.Usuarios.UsuarioContract.UserEntry;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserEntry.ID + " TEXT NOT NULL,"
                + UserEntry.NAME + " TEXT NOT NULL,"
                + UserEntry.AGE + " TEXT NOT NULL,"
                + UserEntry.GENDER + " TEXT NOT NULL,"
                + UserEntry.EMAIL + " TEXT NOT NULL,"
                + UserEntry.PASSWORD + " TEXT NOT NULL,"
                + UserEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + UserEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockUsuario(sqLiteDatabase, new Usuario("Carlos Perez","25","Hombre", "carlos_perez@gmail.com",
                "","carlos_perez.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Julian Cardozo","23","Hombre", "julian.c@gmail.com",
                "","julian_cardozo.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Luciana Ramirez","28","Mujer", "lu_ramirez@hotmail.com",
                "","luciana_ramirez.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Marina Acosta","21","Otro", "marina_acosta@gmail.com",
                "","marina_acosta.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Paula Genesini","25","Mujer", "p_genesini@hotmail.com",
                "","paula_genesini.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Rodrigo Benavidez","30","Hombre", "rodri.b@gmail.com",
                "","rodrigo_benavidez.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Sol Romero","22","Mujer", "sol_romero@gmail.com",
                "","sol_romero.jpg"));
        mockUsuario(sqLiteDatabase, new Usuario("Tomas Barros","22","Hombre", "barros.tomas@gmail.com",
                "","tomas_barros.jpg"));

    }

    public long mockUsuario(SQLiteDatabase db, Usuario user) {
        return db.insert(
                UserEntry.TABLE_NAME,
                null,
                user.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveUser(Usuario user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                UserEntry.TABLE_NAME,
                null,
                user.toContentValues());

    }

    public Cursor getAllUser() {
        return getReadableDatabase()
                .query(
                        UserEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getUsersByGender(String UserGender) {
        Cursor c = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                UserEntry.GENDER + " LIKE ?",
                new String[]{UserGender},
                null,
                null,
                null);
        return c;
    }

    public Cursor getUserById(String UserId) {
        Cursor c = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                UserEntry.ID + " LIKE ?",
                new String[]{UserId},
                null,
                null,
                null);
        return c;
    }

    public int deleteUser(String UserId) {
        return getWritableDatabase().delete(
                UserEntry.TABLE_NAME,
                UserEntry.ID + " LIKE ?",
                new String[]{UserId});
    }

    public int updateUser(Usuario user, String UserId) {
        return getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                user.toContentValues(),
                UserEntry.ID + " LIKE ?",
                new String[]{UserId}
        );
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                UserEntry.ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = UserEntry.EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(UserEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                UserEntry.ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = UserEntry.EMAIL + " = ?" + " AND " + UserEntry.PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(UserEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}