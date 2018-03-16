package com.example.pc.loginconfacebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.IllegalFormatCodePointException;

public class PantallaPrincipal extends AppCompatActivity {
    EditText nombre, uid, email;
    private boolean salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        nombre = findViewById(R.id.edtNombre);
        email = findViewById(R.id.edtEmail);
        uid = findViewById(R.id.edtUID);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            //toda esta informacion la saca de facebook
            String nomb=user.getDisplayName();
            String mail=user.getEmail();
            Uri fotoURL=user.getPhotoUrl();
            String uidUser=user.getUid();


            nombre.setText(nomb);
            email.setText(mail);
            uid.setText(uidUser);
        }else{
            irALogin();
        }
    }

    private void irALogin() {
        if(!salir){
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }


    public void onClickCerrarSesion(View view) {
        //cierra sesion en firebase
        FirebaseAuth.getInstance().signOut();
        //cierra sesion en facebook
        LoginManager.getInstance().logOut();
        irALogin();
    }


}
