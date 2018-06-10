package wallet.erc20.tazzmanian.erc20wallet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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


    public ArrayList<ServerItems> getAll() {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);

        ArrayList<ServerItems> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int id = cursor.getColumnIndex
                    (ColumnID);
            int active = cursor.getColumnIndex
                    (ColumnActive);
            int name = cursor.getColumnIndex
                    (ColumnName);
            int host = cursor.getColumnIndex
                    (ColumnHost);
            int port = cursor.getColumnIndex
                    (ColumnPort);

            //add row to list
            do {
                long thisId = cursor.getLong(id);
                boolean thisActive = cursor.getLong(active) == 0 ? false : true;
                String thisName = cursor.getString(name);
                String thisHost = cursor.getString(host);
                String thisPort = cursor.getString(port);

                list.add(new ServerItems(thisName, thisActive, thisHost, thisPort, thisId));
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }

    public long insert(String name, String host, String port) {
        ContentValues values = new ContentValues();
        values.put(ColumnActive, 0);
        values.put(ColumnName, name);
        values.put(ColumnHost, host);
        values.put(ColumnPort, port);

        Cursor cursor = db.rawQuery("Select * from " + TableName + " where " +
                ColumnActive + " == 1;", null);
        if(cursor.getCount() == 0) {
            values.put(ColumnActive, 1);
        }

//        cursor = db.rawQuery("Select * from " + TableName + " where " +
//                ColumnName + " LIKE " + name + ";", null);
//
//        if(cursor.getCount() > 0) {
//            return 0;
//        }

        return db.insert(TableName, "", values);
    }

    public void updateDefault(Long id) {
        ContentValues values = new ContentValues();
        values.put(ColumnActive, 0);
        db.update(TableName, values, ColumnActive + " = ?", new String[] {Integer.toString(1)});
        values.clear();
        values.put(ColumnActive, 1);
        db.update(TableName, values, ColumnID + " = ?", new String[] {id.toString()});
    }

    public void deleteAccount(Long id) {
        db.delete(TableName,ColumnID + " = ?", new String[] {id.toString()});
    }

    public ServerItems getItemById(Long id) {
        Cursor cursor = db.rawQuery("Select * from " + TableName + ";", null);

        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int cid = cursor.getColumnIndex
                    (ColumnID);
            int cactive = cursor.getColumnIndex
                    (ColumnActive);
            int cname = cursor.getColumnIndex
                    (ColumnName);
            int chost = cursor.getColumnIndex
                    (ColumnHost);
            int cport = cursor.getColumnIndex
                    (ColumnPort);

            boolean thisActive = cursor.getLong(cactive) == 0 ? false : true;
            String thisName = cursor.getString(cname);
            String thisHost = cursor.getString(chost);
            String thisPort = cursor.getString(cport);

            cursor.close();

            return new ServerItems(thisName, thisActive, thisHost, thisPort, id);
        }

        return null;
    }

    public void update(String name, String host, String port, Long id) {
        ContentValues values = new ContentValues();
        values.put(ColumnPort, port);
        values.put(ColumnHost, host);
        values.put(ColumnName, name);
        db.update(TableName, values, ColumnID + " = ?", new String[]{id.toString()});
    }

    public ServerItems getActive() {
        Cursor cursor = db.rawQuery("Select * from " + TableName + " WHERE " +  ColumnID + " = 1;", null);

        if (cursor != null && cursor.moveToFirst()) {
            //get columns
            int cid = cursor.getColumnIndex
                    (ColumnID);
            int cactive = cursor.getColumnIndex
                    (ColumnActive);
            int cname = cursor.getColumnIndex
                    (ColumnName);
            int chost = cursor.getColumnIndex
                    (ColumnHost);
            int cport = cursor.getColumnIndex
                    (ColumnPort);

            boolean thisActive = cursor.getLong(cactive) == 0 ? false : true;
            String thisName = cursor.getString(cname);
            String thisHost = cursor.getString(chost);
            String thisPort = cursor.getString(cport);

            cursor.close();

            return new ServerItems(thisName, thisActive, thisHost, thisPort, cid);
        }

        return null;
    }
}
