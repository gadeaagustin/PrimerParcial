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
    public static final String DATABASE_NAME = "Usuarios.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserEntry.ID + " TEXT NOT NULL,"
                + UserEntry.NAME + " TEXT NOT NULL,"
                + UserEntry.EMAIL + " TEXT NOT NULL,"
                + UserEntry.PASSWORD + " TEXT NOT NULL,"
                + UserEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + UserEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockUsuario(sqLiteDatabase, new Usuario("Carlos Perez", "carlos_perez@gmail.com",
                "","carlos_perez.jpg"));

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

    public long saveLawyer(Usuario user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                UserEntry.TABLE_NAME,
                null,
                user.toContentValues());

    }

    public Cursor getAllLawyers() {
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

    public Cursor getLawyerById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                UserEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public int deleteLawyer(String lawyerId) {
        return getWritableDatabase().delete(
                UserEntry.TABLE_NAME,
                UserEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    public int updateLawyer(Usuario user, String lawyerId) {
        return getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                user.toContentValues(),
                UserEntry.ID + " LIKE ?",
                new String[]{lawyerId}
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