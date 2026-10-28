package br.gov.sp.cps.bandodados;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes que representam os dados do DB e da Tabela:
    public static final String DATABASE_NAME = "dados.db";
    public static final String TABLE_NAME = "pessoas";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "IDADE";

    // Construtor da Classe que chama o construtor da Superclasse
    // passando o contexto, o nome e a versao do DB:
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    // Metodo chama automaticamente ao criar o DB.
    // Cria a tabela "pessoas" com as colunas "ID, NOME, IDADE"
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, IDADE INTEGER)");
    }

    // Metodo chamado quando o db é atualizado para uma nova versao.
    // Nesse caso, ele recria a tabela "pessoas"
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // METODO GRAVAR:
    public boolean inserirDados(String nome, int idade){
        // Obter o db no modo escrita:
        SQLiteDatabase db = this.getWritableDatabase();

        // Armazena os valores para serem inseridos:
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, idade);

        // Insere os dados e armazena o resultado na tabela:
        long resultado = db.insert(TABLE_NAME, null, contentValues);

        // Fecha o db:
        db.close();

        // Retorna true se a inserção foi bem-sucedida:
        return resultado != -1;
    }

    // METODO CONSULTAR:
    public Cursor obterIdadePorNome(String nome){
        // Obter o db no modo Leitura:
        SQLiteDatabase db = this.getReadableDatabase();

        // Especificar a coluna IDADE para retorno:
        String[] columns = {COL_3};

        // Define a condição de Seleção (filtra pelo nome):
        String selection = COL_2 + " = ?";

        // Argumento da condição:
        String[] selectionArgs = {nome};

        // Execução da Consulta no db:
        return db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    // METODO ATUALIZAR:
    public boolean atualizarDados(String nome, int novaIdade){
        // Obter o db em modo Escrita:
        SQLiteDatabase db = this.getWritableDatabase();

        // Armazena o novo valor para a coluna IDADE:
        ContentValues valores = new ContentValues();
        valores.put(COL_3, novaIdade);

        // Atualiza a Tabela com a nova idade:
        int linhasAfetadas = db.update(
                TABLE_NAME,
                valores,
                COL_2 + " = ?",
                new String[]{nome}
        );

        // Fecha o db:
        db.close();

        // retorna True se pelo menos uma linha foi afetada:
        return linhasAfetadas > 0;
    }

    // METODO DELETAR
    public boolean deletaDados(String nome){
        // Obter o db em modo escrita
        SQLiteDatabase db = this.getWritableDatabase();

        // Deleta o registro pelo nome:
        int linhasAfetas = db.delete(
                TABLE_NAME,
                COL_2 + " = ?",
                new String[]{nome}
        );

        // Fecha o db:
        db.close();

        // Retorna true se pelo menos uma linha foi afetada:
        return linhasAfetas > 0;
    }


}
