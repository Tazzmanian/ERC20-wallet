package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountManager {

    static final String TableName = "Accounts";
    static final String ColumnMnemonics = "Mnemonics";
    static final String ColumnActive = "Active";

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, " + ColumnActive +
            " text, " + ColumnMnemonics + " text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    public void createTable(SQLiteDatabase db) {
        db.execSQL(CreateTable);
    }

    public void updateTable(SQLiteDatabase db) {
        db.execSQL(DropTable);
    }
}
