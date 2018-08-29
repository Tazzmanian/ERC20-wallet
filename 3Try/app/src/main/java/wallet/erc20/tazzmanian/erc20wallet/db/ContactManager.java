package wallet.erc20.tazzmanian.erc20wallet.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import wallet.erc20.tazzmanian.erc20wallet.accounts.AccountItems;
import wallet.erc20.tazzmanian.erc20wallet.addressbook.ContactItem;
import wallet.erc20.tazzmanian.erc20wallet.servers.ServerItems;

public class ContactManager {

    static final String TableName = "Contacts";
    static final String ColumnName = "Name";
    static final String ColumnHash = "Hash";
    static final String ColumnID = "ID";
    static SQLiteDatabase db;

    static final String CreateTable = "Create table IF NOT EXISTS " + TableName +
            "(" + ColumnID +" integer PRIMARY KEY AUTOINCREMENT, " + ColumnName +
            " text, " + ColumnHash + " text);";

    static final String DropTable = "DROP table IF EXISTS " + TableName + ";";

    private static ContactManager INSTANCE;

    private ContactManager() {}

    private ContactManager(SQLiteDatabase db) {
        this.db = db;
    }

    public static ContactManager getInstance(SQLiteDatabase db) {
        if(INSTANCE == null) {
            INSTANCE = new ContactManager(db);
        }
        return INSTANCE;
    }

    public static ContactManager getInstance() {
        return INSTANCE;
    }


    public void createTable() {
        db.execSQL(CreateTable);
    }

    public void updateTable() {
        db.execSQL(DropTable);
    }

    public ArrayList<ContactItem> getAll() {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);

        ArrayList<ContactItem> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int id = cursor.getColumnIndex
                    (ColumnID);
            int name = cursor.getColumnIndex
                    (ColumnName);
            int hash = cursor.getColumnIndex
                    (ColumnHash);

            //add row to list
            do {
                long thisId = cursor.getLong(id);
                String thisName = cursor.getString(name);
                String thisHash = cursor.getString(hash);

                list.add(new ContactItem(thisId, thisHash, thisName));
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    public boolean exists(String hash) {
        Cursor cursor = db.query(TableName, new String[] {ColumnID}, ColumnHash + " LIKE ?", new String[] {hash}, null, null, null);

        if(cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insert(String name, String address) {
        ContentValues values = new ContentValues();
        values.put(ColumnName, name);
        values.put(ColumnHash, address);

        if( exists(address)) {
            return 0;
        }

        return db.insert(TableName, "", values);
    }

    public void update(String name, String hash, Long id) {
        ContentValues values = new ContentValues();
        values.put(ColumnHash, hash);
        values.put(ColumnName, name);
        db.update(TableName, values, ColumnID + " = ?", new String[]{id.toString()});
    }

    public void delete(Long id) {
        db.delete(TableName,ColumnID + " = ?", new String[] {id.toString()});
    }

    public ContactItem getItemById(Long id) {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);

        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int cid = cursor.getColumnIndex
                    (ColumnID);
            int cname = cursor.getColumnIndex
                    (ColumnName);
            int chash = cursor.getColumnIndex
                    (ColumnHash);

            String thisName = cursor.getString(cname);
            String thisHash = cursor.getString(chash);

            cursor.close();

            return new ContactItem(id, thisHash, thisName);
        }

        return null;
    }
}
