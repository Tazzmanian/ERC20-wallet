package wallet.erc20.tazzmanian.erc20wallet.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.util.ArrayList;

import wallet.erc20.tazzmanian.erc20wallet.contracts.ContractItems;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerItems;

public class ContractManager {

    static final String TableName = "Contracts";
    static final String ColumnHash = "Hash";
    static final String ColumnName = "TokenName";
    static final String ColumnSymbol = "Symbol";
    static final String ColumnTotalSupply = "TotalSupply";
    static final String ColumnDecimals = "Decimals";
    static final String ColumnID = "ID";

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, " +
            ColumnHash + " text, " + ColumnSymbol + " text, " +
            ColumnTotalSupply + " text, " + ColumnDecimals + " text, "
            + ColumnName + " text);";

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

    public long insert(String hash, String tokenName, String tokenSymbol, String totalSupply, String decimals) {
        ContentValues values = new ContentValues();
        values.put(ColumnDecimals, decimals);
        values.put(ColumnHash, hash);
        values.put(ColumnName, tokenName);
        values.put(ColumnSymbol, tokenSymbol);
        values.put(ColumnTotalSupply, totalSupply);

        return db.insert(TableName, "", values);
    }

    public ArrayList<ContractItems> getAll() {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);

        ArrayList<ContractItems> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int id = cursor.getColumnIndex
                    (ColumnID);
            int name = cursor.getColumnIndex
                    (ColumnName);
            int decimals = cursor.getColumnIndex
                    (ColumnDecimals);
            int hash = cursor.getColumnIndex
                    (ColumnHash);
            int symbol = cursor.getColumnIndex
                    (ColumnSymbol);
            int totalSupply = cursor.getColumnIndex
                    (ColumnTotalSupply);


            //add row to list
            do {
                long thisId = cursor.getLong(id);
                String thisDecimals = cursor.getString(decimals);
                String thisName = cursor.getString(name).equals("null") ? "NA" : cursor.getString(name);
                String thisHash = cursor.getString(hash);
                String thisSymbol = cursor.getString(symbol).equals("null") ? "NA" : cursor.getString(symbol);
                String thisTotalSypply = cursor.getString(totalSupply);

                thisTotalSypply = thisTotalSypply.equals("null") ? "0" : thisTotalSypply;
                thisDecimals = thisDecimals.equals("null") ? "0" : thisDecimals;

                list.add(new ContractItems(thisHash, thisId, thisSymbol, thisName, new BigInteger(thisTotalSypply), new BigInteger(thisDecimals)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }

    public void delete(Long id) {
        db.delete(TableName,ColumnID + " = ?", new String[] {id.toString()});
    }
}
