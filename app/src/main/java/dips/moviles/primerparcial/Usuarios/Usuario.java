package dips.moviles.primerparcial.Usuarios;

import android.content.ContentValues;

import java.util.UUID;

import dips.moviles.primerparcial.Usuarios.UsuarioContract.UserEntry;

public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String avatarUri;

    public Usuario(){
        this.id = UUID.randomUUID().toString();
        this.nombre = "";
        this.email = "";
        this.password = "";
        this.avatarUri = "";
    }

    public Usuario(String name, String email, String pass, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.nombre = name;
        this.email = email;
        this.password = pass;
        this.avatarUri = avatarUri;
    }

    public  ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserEntry.ID, id);
        values.put(UserEntry.NAME, nombre);
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
