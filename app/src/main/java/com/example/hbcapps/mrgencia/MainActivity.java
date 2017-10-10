package com.example.hbcapps.mrgencia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button llamar;

    private final int codigo_llamada = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llamar = (Button) findViewById(R.id.btnAyuda);
        final String numero = "+56958889732";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, codigo_llamada);
        } else {
            OlderVersions(numero);
        }

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void OlderVersions(String numero) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "No has aceptado los permisos necesarios para la aplicación", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para manejar la respuesta despues de solicitar permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Se diferencian los permisos mediante codigos, en este caso el de telefono es 100
        switch (requestCode) {
            case codigo_llamada:
                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobar si ha sido aceptado o denegado la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //Concedio su permiso
                        String numero = "+56958889732";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intent);
                    }
                    else{
                        //No concedio su permiso
                        Toast.makeText(MainActivity.this, "No has aceptado los permisos para el correcto funcionamiento de la app. ", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    //Comprueba si el usuario ha aceptado el permiso que se le pasa por parametro
    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
