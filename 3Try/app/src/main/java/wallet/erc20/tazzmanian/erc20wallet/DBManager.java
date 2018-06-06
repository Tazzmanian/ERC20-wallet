package wallet.erc20.tazzmanian.erc20wallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
    static final String DBName = "ERC20Wallet";
    static final int DBVersion = 7;
    static AccountManager am;
    static TransactionsManager tm;
    static ContractManager cm;
    static SQLiteDatabase sqlDB;

    private static DBManager INSTANCE;

    private DBManager() {}
    private DBManager(Context context) {
        DatabaseHelperUser db = new DatabaseHelperUser(context);
        sqlDB = db.getWritableDatabase();
        am = AccountManager.getInstance(sqlDB);
        tm = TransactionsManager.getInstance(sqlDB);
        cm = ContractManager.getInstance(sqlDB);
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
            am = AccountManager.getInstance(db);
            tm = TransactionsManager.getInstance(db);
            cm = ContractManager.getInstance(db);
            am.createTable();
            tm.createTable();
            cm.createTable();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            am = AccountManager.getInstance(db);
            tm = TransactionsManager.getInstance(db);
            cm = ContractManager.getInstance(db);
            am.updateTable();
            tm.updateTable();
            cm.updateTable();
            onCreate(db);
        }
    }

}
