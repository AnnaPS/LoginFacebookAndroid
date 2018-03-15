package com.example.pc.loginconfacebook;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFB = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginFB.setReadPermissions(Arrays.asList("email"));
        loginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //inicio de sesion
                irAPrincipal();
            }

            @Override
            public void onCancel() {
            //proceso cancelado
                Toast.makeText(MainActivity.this, "CANCELADO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
            //error
                Toast.makeText(MainActivity.this, "ERROR DE LOGIN", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void irAPrincipal() {
        Intent i =new Intent (this, PantallaPrincipal.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity (i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
//cerrar sesion
    public void onClickFB(View view) {

        irAPrincipal();

    }
}
