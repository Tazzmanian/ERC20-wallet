package wallet.erc20.tazzmanian.erc20wallet;

import android.database.sqlite.SQLiteDatabase;

public class ServerManager {

    static final String TableName = "Servers";
    static final String ColumnName = "Name";
    static final String ColumnActive = "Active";
    static final String ColumnHost = "Host";
    static final String ColumnPort = "Port";
    static final String ColumnID = "ID";
    static SQLiteDatabase db;

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(" + ColumnID +" integer PRIMARY KEY AUTOINCREMENT, "
            + ColumnActive + " text, "
            + ColumnName + " text, "
            + ColumnHost + " text, "
            + ColumnPort + " text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    private static ServerManager INSTANCE;

    private ServerManager() {}

    private ServerManager(SQLiteDatabase db) {
        this.db = db;
    }

    public static ServerManager getInstance(SQLiteDatabase db) {
        if(INSTANCE == null) {
            INSTANCE = new ServerManager(db);
        }
        return INSTANCE;
    }

    public static ServerManager getInstance() {
        return INSTANCE;
    }


    public void createTable() {
        db.execSQL(CreateTable);
    }

    public void updateTable() {
        db.execSQL(DropTable);
    }


}
