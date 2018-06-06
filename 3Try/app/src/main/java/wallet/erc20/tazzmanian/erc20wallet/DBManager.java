package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
    static final String DBName = "ERC20Wallet";
    static final int DBVersion = 6;
    static AccountManager am;
    static TransactionsManager tm;
    static ContractManager cm;
    static SQLiteDatabase sqlDB;

    private static DBManager INSTANCE;

    private DBManager() {}
    private DBManager(Context context) {
        am = new AccountManager();
        tm = new TransactionsManager();
        cm = new ContractManager();
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();
    }

    public static DBManager getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new DBManager(context);
        }
        return INSTANCE;
    }

    static class DatabaseHelperUser extends SQLiteOpenHelper {
        Context context;

        DatabaseHelperUser(Context context) {
            super(context, DBName, null, DBVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            am.createTable(db);
            tm.createTable(db);
            cm.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            am.updateTable(db);
            tm.updateTable(db);
            cm.updateTable(db);
            onCreate(db);
        }
    }

}
