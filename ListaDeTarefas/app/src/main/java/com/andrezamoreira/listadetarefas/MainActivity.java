package com.andrezamoreira.listadetarefas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andrezamoreira.listadetarefas.database.TaskContract;
import com.andrezamoreira.listadetarefas.database.TaskDBHelper;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;
    private ListView listaTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new TaskDBHelper(this);
        listaTarefas = findViewById(R.id.listaTarefasListView);

        // quando segurar um item
        // TODO: apaga item
        listaTarefas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textoTarefa = view.findViewById(R.id.tarefaTextView);
                String tarefa = textoTarefa.getText().toString();

                String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                        TaskContract.TABLE, TaskContract.Columns.TAREFA, tarefa);

                SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                sqLiteDatabase.execSQL(sql);

                Toast.makeText(MainActivity.this, "Tarefa excluída com sucesso!", Toast.LENGTH_LONG).show();

                updateUI();

                return false;
            }
        });

        final Button addTarefa = findViewById(R.id.addTarefasButton);
        // adicionando ação ao botão
        addTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarefa();
            }
        });

        updateUI();
    }

    //region AddTarefa
    private void addTarefa(){
        // alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // TODO: chama o layout do alert
        View alertView = getLayoutInflater().inflate(R.layout.alert_tarefa, null);

        // sempre que estiver passando um componente como List deve-se declarar como final
        final EditText textoTarefa = alertView.findViewById(R.id.tarefaText);
        final EditText textoPrazo = alertView.findViewById(R.id.prazoText);

        // TODO: chamar o layout customizado do alerta
        builder.setView(alertView);

        // botão salvar
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // salvar tarefa
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.TAREFA, textoTarefa.getText().toString());
                values.put(TaskContract.Columns.PRAZO, textoPrazo.getText().toString());

                // TODO: inserindo valores no banco de dados
                db.insertWithOnConflict(TaskContract.TABLE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);

                // mostrar na lista
                updateUI();
            }
        });

        // botão cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: cancelar alerta
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }
    //endregion

    //region UpdateUI
    private void updateUI(){
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TaskContract.TABLE, new String[]{TaskContract.Columns._ID,
        TaskContract.Columns.TAREFA, TaskContract.Columns.PRAZO},
                null, null, null, null, null);

        // TODO: pega os dados do banco de dados e coloca na lista
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.cell_tarefa,
                cursor,
                new String[]{TaskContract.Columns.TAREFA, TaskContract.Columns.PRAZO},
                new int[]{R.id.tarefaTextView, R.id.prazoTextView},
                0);

        listaTarefas.setAdapter(listAdapter);
    }
    //endregion


}
