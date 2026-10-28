package br.gov.sp.cps.bandodados;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextView textResultado;
    TextInputEditText textNome, textIdade;
    Button btnGravar, btnConsultar, btnAtualizar, btnDeletar;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        textResultado   = findViewById(R.id.textResultado);
        textNome        = findViewById(R.id.textNome);
        textIdade       = findViewById(R.id.textIdade);
        btnAtualizar    = findViewById(R.id.btnAtualizar);
        btnConsultar    = findViewById(R.id.btnConsultar);
        btnGravar       = findViewById(R.id.btnGravar);
        btnDeletar      = findViewById(R.id.btnDeletar);

        // GRAVAR:
        btnGravar.setOnClickListener(v -> {
            // Entrada de dados
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            // Validação dos dados:
            if(nome.isEmpty() || idade.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Preencha todos os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Caso seja sucesso - try / Caso seja insucesso - catch:
            try{
                // Converte idade de String para Numerico (int):
                int numIdade = Integer.parseInt(idade);

                // Chama o db e o metodo Inserir dados:
                if(dbHelper.inserirDados(nome, numIdade)){
                    // Caso a gravação foi realizada com sucesso:
                    Toast.makeText(MainActivity.this,
                            "Dados Inseridos com Sucesso",
                            Toast.LENGTH_LONG).show();
                }else{
                    // Caso a gravação não foi concluida:
                    Toast.makeText(MainActivity.this,
                            "Erro ao inserir Dados",
                            Toast.LENGTH_LONG).show();
                }
            }catch(NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Erro ao inserir Dados",
                        Toast.LENGTH_LONG).show();
            }
        });

        // CONSULTAR:
        btnConsultar.setOnClickListener(v -> {
            // Entrada de dados:
            String nome = textNome.getText().toString();

            // Validação de dados:
            if(nome.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Digite o nome",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Consulta o nome do DB:
            Cursor cursor = dbHelper.obterIdadePorNome(nome);
            if(cursor != null && cursor.moveToFirst()){
                String idade = cursor.getString(0);
                textResultado.setText("Idade: " + idade);
            }else{
                Toast.makeText(MainActivity.this,
                        "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }
        });

        // ATUALIZAR:
        btnAtualizar.setOnClickListener(v -> {
            // Entrada de dados
            String nome = textNome.getText().toString();
            String idade = textIdade.getText().toString();

            // Validação dos dados:
            if(nome.isEmpty() || idade.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Preencha todos os dados",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Caso seja sucesso - try / Caso seja insucesso - catch:
            try{
                // Converte idade de String para Numerico (int):
                int numIdade = Integer.parseInt(idade);

                // Chama o db e o metodo Inserir dados:
                if(dbHelper.atualizarDados(nome, numIdade)){
                    // Caso a atualização foi realizada com sucesso:
                    Toast.makeText(MainActivity.this,
                            "Dados Atualizados com Sucesso",
                            Toast.LENGTH_LONG).show();
                }else{
                    // Caso a atualização não foi concluida:
                    Toast.makeText(MainActivity.this,
                            "Nome não encontrado",
                            Toast.LENGTH_LONG).show();
                }
            }catch(NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Idade Inválida",
                        Toast.LENGTH_LONG).show();
            }
        });

        // DELETAR:
        btnDeletar.setOnClickListener(v -> {
            // Entrada de dados
            String nome = textNome.getText().toString();

            // Validação dos dados:
            if(nome.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Digite o nome",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // Deletando os dados pelo nome:
            if(dbHelper.deletaDados(nome)){
                Toast.makeText(MainActivity.this,
                        "Dados Deletado com Sucesso",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,
                        "Nome não encontrado",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}