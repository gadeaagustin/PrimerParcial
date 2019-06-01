package dips.moviles.primerparcial.Usuarios;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import dips.moviles.primerparcial.Usuarios.UsuarioContract.UserEntry;

public class Usuario {
    private String id;
    private String nombre;
    private String edad;
    private String genero;
    private String email;
    private String password;
    private String avatarUri;


    public Usuario(){
        this.id = UUID.randomUUID().toString();
        this.nombre = "";
        this.edad="";
        this.genero="";
        this.email = "";
        this.password = "";
        this.avatarUri = "";
    }

    public Usuario(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(UserEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(UserEntry.NAME));
        edad = cursor.getString(cursor.getColumnIndex(UserEntry.AGE));
        genero = cursor.getString(cursor.getColumnIndex(UserEntry.GENDER));
        email = cursor.getString(cursor.getColumnIndex(UserEntry.EMAIL));
        password = cursor.getString(cursor.getColumnIndex(UserEntry.PASSWORD));
        avatarUri = cursor.getString(cursor.getColumnIndex(UserEntry.AVATAR_URI));
    }

    public Usuario(String name,String edad,String genero, String email, String pass, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.nombre = name;
        this.edad = edad;
        this.genero = genero;
        this.email = email;
        this.password = pass;
        this.avatarUri = avatarUri;
    }

    public  ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserEntry.ID, id);
        values.put(UserEntry.NAME, nombre);
        values.put(UserEntry.AGE, edad);
        values.put(UserEntry.GENDER, genero);
        values.put(UserEntry.EMAIL, email);
        values.put(UserEntry.PASSWORD, password);
        values.put(UserEntry.AVATAR_URI, avatarUri);
        return values;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() { return edad; }

    public void setEdad(String edad) { this.edad = edad; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }
}
