package com.example.miguel2.listacontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel2 on 23/03/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Biblioteca.db";
    public static final String TABLE_NAME = "biblioteca_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "AUTOR";
    public static final String COL_4 = "NUMERO_PAGINAS";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //executa o query
        db.execSQL("create table "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOME TEXT,AUTOR TEXT,NUMERO_PAGINAS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String autor, int pags){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,autor);
        contentValues.put(COL_4,pags);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id, String name, String autor, String pags){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,autor);
        contentValues.put(COL_4,pags);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id} );
    }

}
/*
public class DbHelper extends SQLiteOpenHelper{

    private static final String NOME_BASE = "MinhaBiblioteca";
    private static final int VERSAO_BASE = 1; //Identifica a versão desta app;

    public DbHelper(Context context){
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    @Override
    //O método onCreate serve para criar uma nova tabela
    public void onCreate(SQLiteDatabase db) {

        String sqlCreateTabelaLivro = "CREATE TABLE livro("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulo TEXT,"
                + "autor TEXT,"
                + "paginas INTEGER"
                + ")";

        db.execSQL(sqlCreateTabelaLivro); //Cria a tabela

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String sqlDropTabelaLivros = "DROP TABLE livro";
            db.execSQL(sqlDropTabelaLivros);

            onCreate(db); //Chama o método OnCreate e cria a tabela livro.
    }

    public boolean insertLivro(Livro livro){ //método que insere um livro na tabela
        SQLiteDatabase db = getWritableDatabase(); //Write porque é para escrever
        ContentValues cv = new ContentValues();

        cv.put("nome",livro.getNome());
        cv.put("autor",livro.getAutor());
        cv.put("paginas",livro.getPaginas());


        long i = db.insert("livro", null , cv);
        db.close();

        if(i==-1)
            return false;
        else
            return true;

    }


    //método que retorna a lista de livros que estão na base de dados
    public List<Livro> selectTodosOsLivros(){

        List<Livro> listLivros = new ArrayList<Livro>();

        SQLiteDatabase db = getReadableDatabase();

        String sqlSelectTodosLivros = "SELECT * FROM livro";

        Cursor c = db.rawQuery(sqlSelectTodosLivros, null);

        if(c.moveToFirst()){ //Se houver algum livro na BD, vai buscar o 1º livro da base de dados
            do {
                Livro livro = new Livro();
                livro.setId(c.getInt(0)); //0 porque o ID está declarado na 1ª coluna da tabela livro
                livro.setNome(c.getString(1));
                livro.setAutor(c.getString(2));
                livro.setPaginas(c.getInt(3));

                listLivros.add(livro); // adiciona o 1º livro da BD à lista que mais tarde vai ser retornada
            }while(c.moveToNext()); //Enquanto o cursor tiver um proximo livro, vai sempre adicioná-lo à lista de livros que vai ser retornada
        }
        db.close();
        return listLivros;

    }
}
*/
//https://www.youtube.com/watch?v=T0ClYrJukPA