package com.andrezamoreira.listadetarefas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(@Nullable Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    // TODO: cria a tabela
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "%s TEXT, %s TEXT)", TaskContract.TABLE, TaskContract.Columns.TAREFA, TaskContract.Columns.PRAZO);

        sqLiteDatabase.execSQL(sqlQuery);
    }

    // TODO: atualiza a tabela
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
    }
}
