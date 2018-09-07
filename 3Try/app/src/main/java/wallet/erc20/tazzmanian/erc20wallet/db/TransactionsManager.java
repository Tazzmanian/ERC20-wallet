package wallet.erc20.tazzmanian.erc20wallet.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import wallet.erc20.tazzmanian.erc20wallet.transactions.TransactionItem;

public class TransactionsManager {
    static final String TableName = "Transactions";
    static final String ColumnFrom = "FromHash";
    static final String ColumnTo = "ToHash";
    static final String ColumnValue = "Value";
    static final String ColumnHash = "Hash";
    static final String ColumnCurrency = "Currency";
    static final String ColumnNetwork = "Network";
    static final String ColumnContract = "Contract";
    static final String ColumnNonce = "Nonce";
    static final String ColumnBlockNumber = "BlockNumber";

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(ID integer PRIMARY KEY AUTOINCREMENT, "
            + ColumnFrom + " text, "
            + ColumnTo + " text, "
            + ColumnValue + " text, "
            + ColumnHash + " text, "
            + ColumnNetwork + " text, "
            + ColumnContract + " text, "
            + ColumnNonce + " text, "
            + ColumnBlockNumber + " text, "
            + ColumnCurrency +" text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    private static SQLiteDatabase db;

    private static TransactionsManager INSTANCE;

    private TransactionsManager() {}

    private TransactionsManager(SQLiteDatabase db) {
        this.db = db;
    }

    public static TransactionsManager getInstance(SQLiteDatabase db) {
        if(INSTANCE == null) {
            INSTANCE = new TransactionsManager(db);
        }
        return INSTANCE;
    }

    public void createTable() {
        db.execSQL(CreateTable);
    }

    public void updateTable() {
        db.execSQL(DropTable);
    }

    public long insert(String from, String to, String value, String network, String currency, String txHash, String bn, String contract, String nonce ) {
        ContentValues values = new ContentValues();
        values.put(ColumnFrom, from);
        values.put(ColumnTo, to);
        values.put(ColumnValue, value);
        values.put(ColumnNetwork, network);
        values.put(ColumnBlockNumber, bn);
        values.put(ColumnContract, contract);
        values.put(ColumnCurrency, currency);
        values.put(ColumnHash, txHash);
        values.put(ColumnNonce, nonce);

        return db.insert(TableName, "", values);
    }

    public TransactionItem getLatest(String network, String address) {
        TransactionItem ti = new TransactionItem();

        String maxNonce = "max(" + ColumnNonce +")";

        Cursor cursor = db.rawQuery("Select ID," + maxNonce + " from " + TableName +
                " where " + ColumnNetwork + " is '" + network +
                "' and (" + ColumnFrom + " is '" + address +
                "' or " + ColumnTo + " is '" + address + "');", null);

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int max = cursor.getColumnIndex(maxNonce);
            long s = cursor.getLong(max);

            if(s == 0){
                cursor.close();
                return ti;
            }

            max = cursor.getColumnIndex("ID");
            long id = cursor.getLong(max);
            cursor = db.rawQuery("Select * from " + TableName + " where ID=" + id + ";" , null);
            cursor.moveToFirst();

            int nonce = cursor.getColumnIndex(ColumnNonce);
            int bn = cursor.getColumnIndex(ColumnBlockNumber);
            int from = cursor.getColumnIndex(ColumnFrom);
            int to = cursor.getColumnIndex(ColumnTo);
            int contract = cursor.getColumnIndex(ColumnContract);
            int value = cursor.getColumnIndex(ColumnValue);
            int hash = cursor.getColumnIndex(ColumnHash);
            int currency = cursor.getColumnIndex(ColumnCurrency);

            String nonceS = cursor.getString(nonce);
            String bnS = cursor.getString(bn);
            String fromS = cursor.getString(from);
            String toS = cursor.getString(to);
            String contractS = cursor.getString(contract);
            String valueS = cursor.getString(value);
            String hashS = cursor.getString(hash);
            String curS = cursor.getString(currency);

            ti.setNetwork(network);
            ti.setBlockNumber(bnS);
            ti.setNonce(nonceS);
            ti.setAmount(valueS);
            ti.setFrom(fromS);
            ti.setTo(toS);
            ti.setContract(contractS);
            ti.setTxHash(hashS);
            ti.setCurrency(contractS);
            ti.setCurrency(curS);

            return ti;

        } else if (cursor != null && cursor.getCount() == 0) {
            return ti;
        }

        return null;
    }

    public ArrayList<TransactionItem> getAll(String netw, String address) {
        Cursor cursor = db.rawQuery("Select * from " + TableName +
                " where " + ColumnNetwork + " is '" + netw +
                "' and (" + ColumnFrom + " is '" + address +
                "' or " + ColumnTo + " is '" + address + "');", null);

        ArrayList<TransactionItem> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int nonce = cursor.getColumnIndex(ColumnNonce);
            int bn = cursor.getColumnIndex(ColumnBlockNumber);
            int from = cursor.getColumnIndex(ColumnFrom);
            int to = cursor.getColumnIndex(ColumnTo);
            int contract = cursor.getColumnIndex(ColumnContract);
            int value = cursor.getColumnIndex(ColumnValue);
            int hash = cursor.getColumnIndex(ColumnHash);
            int currency = cursor.getColumnIndex(ColumnCurrency);
            int network = cursor.getColumnIndex(ColumnNetwork);

            //add row to list
            do {
                String nonceS = cursor.getString(nonce);
                String bnS = cursor.getString(bn);
                String fromS = cursor.getString(from);
                String toS = cursor.getString(to);
                String contractS = cursor.getString(contract);
                String valueS = cursor.getString(value);
                String hashS = cursor.getString(hash);
                String curS = cursor.getString(currency);
                String netS = cursor.getString(network);

                list.add(new TransactionItem(fromS, toS, netS, contractS, valueS, hashS, curS, nonceS, bnS));
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }
}
