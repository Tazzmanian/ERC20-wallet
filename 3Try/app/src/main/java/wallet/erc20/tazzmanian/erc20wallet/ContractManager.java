package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContractManager {

    static final String TableName = "Contracts";
    static final String ColumnHash = "Hash";
    static final String ColumnName = "Name";

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, " + ColumnHash +
            " text, " + ColumnName + " text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    private static SQLiteDatabase db;

    private static ContractManager INSTANCE;

    private ContractManager() {}

    private ContractManager(SQLiteDatabase db) {
        this.db = db;
    }

    public static ContractManager getInstance(SQLiteDatabase db) {
        if(INSTANCE == null) {
            INSTANCE = new ContractManager(db);
        }
        return INSTANCE;
    }

    public void createTable() {
        db.execSQL(CreateTable);
    }

    public void updateTable() {
        db.execSQL(DropTable);
    }
}
