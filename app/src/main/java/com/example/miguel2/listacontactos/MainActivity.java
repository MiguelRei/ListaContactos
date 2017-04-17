package com.example.miguel2.listacontactos;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etLogin, etPassword;
        final String login1 = "MiguelRei";
        final String email1 = "miguelasrei@gmail.com";
        final String Password1 = "Bovinfor";

        etLogin = (EditText) findViewById(R.id.etLogin);
        etLogin.requestFocus();//Para começar já selecionado
        etPassword  = (EditText) findViewById(R.id.etPassword);

        Button Login = (Button) findViewById(R.id.btFazLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("----->","");

                if (etLogin.getText().toString().equals(login1) || etLogin.getText().toString().equals(email1)) {
                    if (etPassword.getText().toString().equals(Password1)) {

                        Intent it = new Intent(MainActivity.this, InserirLivroActivity.class);
                        it.putExtra("user",etLogin.getText().toString());
                        startActivity(it);

                        Toast.makeText(MainActivity.this, "Bem-vindo à BOVINFOR", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Password errada", Toast.LENGTH_LONG).show();
                        etPassword.setText("");
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Usuário não existente", Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                }


            }
        });
    }

    //Serve para atualizar a lista automatiamente sempre que se abre esta atividade
    /*
    @Override
    protected void onResume(){
        super.onResume();

        DbHelper dbHelper = new DbHelper(this);
        //List<Livro> listaLivros = dbHelper.selectTodosOsLivros();

        //Passar estes dados para a listView:
        //ArrayAdapter<Livro> adp = new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_1, listaLivros);
        //ListaLivros.setAdapter(adp);
    }
    */

    public void viewAll(){

        Cursor res = myDb.getAllData();

        if (res.getCount() == 0) {
            //Significa que não há dados na BD
            return;
        }
        StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()) {
                buffer.append("Id: "+res.getString(0)+"\n");

            }



    }
}






















