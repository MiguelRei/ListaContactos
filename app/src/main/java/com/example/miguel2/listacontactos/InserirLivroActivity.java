package com.example.miguel2.listacontactos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InserirLivroActivity extends Activity implements OnClickListener{

    DbHelper myDb;
    EditText etNome, etAutor, etPaginas,tvnID;
    TextView name;
    Button btGeraRegisto;
    Button btVerBib;
    Button btlogout;
    Button btUpdate;
    Button btDelete;

    public String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        myDb = new DbHelper(this);

        etNome = (EditText) findViewById(R.id.etNome);
        etAutor  = (EditText) findViewById(R.id.etAutor);
        etPaginas  = (EditText) findViewById(R.id.etPaginas);
        name = (TextView) findViewById(R.id.tvname);
        tvnID = (EditText) findViewById(R.id.tvnID);
        btGeraRegisto = (Button) findViewById(R.id.btInsereLivro);
        btVerBib = (Button) findViewById(R.id.btVerBib);
        btlogout = (Button) findViewById(R.id.btLogOut);
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btApagar);

        //Receber o nome de usuário que foi validado na actividade de LogIn
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        //Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        name.setText(user);

        //btGeraRegisto.setOnClickListener(this);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();

        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(InserirLivroActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });

    }

    public void DeleteData(){
        btDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(tvnID.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText(InserirLivroActivity.this, "Dados apagados", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(InserirLivroActivity.this, "Dados não apagados", Toast.LENGTH_LONG).show();

                        etNome.setText("");
                        etAutor.setText("");
                        etPaginas.setText("");
                        tvnID.setText("");
                    }
        });
    }

    public void UpdateData(){
        btUpdate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isUpdate = myDb.updateData(tvnID.getText().toString(),
                                                            etNome.getText().toString(),
                                                            etAutor.getText().toString(),
                                                            etPaginas.getText().toString());
                        if (isUpdate == true) {
                            Toast.makeText(InserirLivroActivity.this, "Dados foram atulizados", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(InserirLivroActivity.this, "Dados não atualizados", Toast.LENGTH_LONG).show();

                        etNome.setText("");
                        etAutor.setText("");
                        etPaginas.setText("");
                        tvnID.setText("");
                    }

        });
    }

    public void AddData(){
        btGeraRegisto.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDb.insertData(   etNome.getText().toString(),
                                                                etAutor.getText().toString(),
                                                                Integer.parseInt(etPaginas.getText().toString()) );

                        if (isInserted) //Os dados foram inseridos
                            Toast.makeText(InserirLivroActivity.this, "Dados foram inseridos", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(InserirLivroActivity.this, "ERRO", Toast.LENGTH_LONG).show();

                        etNome.setText("");
                        etAutor.setText("");
                        etPaginas.setText("");
                        //Intent it = new Intent(InserirLivroActivity.this, MainActivity.class);
                        //startActivity(it);
                        //finish(); //Fecha a actividade em questão


                    }
                }
        );

    }



    public void viewAll(){
        btVerBib.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0){
                            showMessage("Error","BD vazia");
                            //show message
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            buffer.append("ID = "+ res.getString(0)+"\n");
                            buffer.append("NOME = "+ res.getString(1)+"\n");
                            buffer.append("AUTOR = "+ res.getString(2)+"\n");
                            buffer.append("NÚMERO DE PÁGINAS = "+ res.getString(3)+"\n\n");
                        }
                        //Show all data
                        showMessage("Dados",buffer.toString());
                    }
                }

        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onClick(View v) {

    }


        /*
        Livro livro = new Livro();
        livro.setNome(etNome.getText().toString());
        livro.setAutor(etAutor.getText().toString());
        livro.setPaginas( Integer.parseInt(etPaginas.getText().toString()));
        DbHelper dbHelper = new DbHelper(this);

        boolean t;
        t = dbHelper.insertLivro(livro);

        if (t==true)
            Toast.makeText(this, "Data  inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data  not inserted", Toast.LENGTH_SHORT).show();

        etNome.setText("");
        etAutor.setText("");
        etPaginas.setText("");


*/


}
