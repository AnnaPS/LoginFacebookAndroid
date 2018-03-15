package com.example.pc.loginconfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.IllegalFormatCodePointException;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        if (AccessToken.getCurrentAccessToken()==null){
            //si no hay sesion iniciada vamos a la pantalla de login (mainActivity)
            irALogin();
        }
    }

    private void irALogin() {
        Intent i =new Intent (this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity (i);
    }


    public void onClickCerrarSesion(View view) {
        LoginManager.getInstance().logOut();
        irALogin();
    }
}
