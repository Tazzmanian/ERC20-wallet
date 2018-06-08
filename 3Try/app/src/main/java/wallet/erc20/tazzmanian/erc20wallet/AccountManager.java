package wallet.erc20.tazzmanian.erc20wallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountManager {

    static final String TableName = "Accounts";
    static final String ColumnMnemonics = "Mnemonics";
    static final String ColumnActive = "Active";
    static final String ColumnPublicHash = "Hash";
    static SQLiteDatabase db;

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, " + ColumnActive +
            " text, " + ColumnMnemonics + " text, " + ColumnPublicHash + " text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    private static AccountManager INSTANCE;

    private AccountManager() {}

    private AccountManager(SQLiteDatabase db) {
        this.db = db;
    }

    public static AccountManager getInstance(SQLiteDatabase db) {
        if(INSTANCE == null) {
            INSTANCE = new AccountManager(db);
        }
        return INSTANCE;
    }

    public static AccountManager getInstance() {
        return INSTANCE;
    }


    public void createTable() {
        db.execSQL(CreateTable);
    }

    public void updateTable() {
        db.execSQL(DropTable);
    }

    public int count() {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);
        System.out.println("TTT: " + cursor.getCount());
        return cursor.getCount();
    }

    public String getActiveMnemonicsAccount() {
        if(count() == 0) {
            return "";
        }

        Cursor cursor = db.rawQuery("Select " + ColumnMnemonics + " from " + TableName + " where " +
                ColumnActive + " == 1;", null);

        if(cursor.getCount() == 0) {
            cursor = db.rawQuery("Select " + ColumnMnemonics + " from " + TableName + ";", null);
            cursor.moveToFirst();
            // update default active 1
            return cursor.getString(0);
        }

        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String getActiveHashAccount() {
        if(count() == 0) {
            return "";
        }

        Cursor cursor = db.rawQuery("Select " + ColumnPublicHash + " from " + TableName + " where " +
                ColumnActive + " == 1;", null);

        if(cursor.getCount() == 0) {
            cursor = db.rawQuery("Select " + ColumnPublicHash + " from " + TableName + ";", null);
            cursor.moveToFirst();
            // update default active 1
            return cursor.getString(0);
        }

        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public long insert(String mnemonics, String address) {
        ContentValues values = new ContentValues();
        values.put(ColumnActive, 0);
        values.put(ColumnMnemonics, mnemonics);
        values.put(ColumnPublicHash, address);

        Cursor cursor = db.rawQuery("Select " + ColumnMnemonics + " from " + TableName + " where " +
                ColumnActive + " == 1;", null);
        if(cursor.getCount() == 0) {
            values.put(ColumnActive, 1);
        }

        return db.insert(TableName, "", values);
    }
}
