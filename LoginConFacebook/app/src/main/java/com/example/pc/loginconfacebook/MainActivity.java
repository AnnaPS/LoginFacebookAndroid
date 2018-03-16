package com.example.pc.loginconfacebook;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginFB;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseEscuchador;
    private ProgressBar progreso;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFB = findViewById(R.id.login_button);
        progreso = findViewById(R.id.progressBar);

        callbackManager = CallbackManager.Factory.create();

        loginFB.setReadPermissions(Arrays.asList("email"));
        loginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //inicio de sesion
                obtenerAccessToken(loginResult.getAccessToken());
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseEscuchador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //este metodo se ejecuta cuando se muestra algun cambio en la autenticacion

                //comprobamos si tenemos un usuario valido
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //si el usuario existe
                    irAPrincipal();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        //el escuchador comienza a escuchar
        firebaseAuth.addAuthStateListener(firebaseEscuchador);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //paramos el escuchador
        firebaseAuth.removeAuthStateListener(firebaseEscuchador);
    }

    private void obtenerAccessToken(AccessToken accessToken) {

        progreso.setVisibility(View.VISIBLE);
        loginFB.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        //una vez se tiene la credencial que nos aporta el token podremos inicar sesion en firebase
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //este metodo se ejecuta cuando se termina tod el proceso
                //pueden ocurrir errores, se muestra un mensaje al usuario
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error de login", Toast.LENGTH_SHORT).show();
                }
                loginFB.setVisibility(View.VISIBLE);
                progreso.setVisibility(View.GONE);

            }
        });

    }

    private void irAPrincipal() {
        Intent i = new Intent(this, PantallaPrincipal.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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
