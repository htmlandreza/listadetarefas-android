package com.andrezamoreira.listadetarefas.database;

import android.provider.BaseColumns;

public class TaskContract {

    // TODO: configurações do banco e nome da tabela
    public static final String DB_NAME = "br.com.andrezamoreira.listadetarefas";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tarefas";

    // region Columns
    // TODO: colunas da tabela
    public class Columns {
        public static final String TAREFA = "tarefa";
        public static final String PRAZO = "prazo";
        public static final String _ID = BaseColumns._ID;
    }
    //endregion
}
