package ma.emsi.tp_sqlite.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 6;
    // Database Name
    private static final String DATABASE_NAME = "GSalleMachine";
    // SQL statement to create Salle table
    String CREATE_Salle_TABLE = "CREATE TABLE salle ( " +
            "idS INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "code TEXT, " +
            "libelle TEXT )";
    // SQL statement to create Machine table
    String CREATE_Machine_TABLE = "CREATE TABLE machine ( " +
            "idM INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "marque TEXT, " +
            "reference TEXT, "+
            "idSalle INTEGER)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create salle table
        db.execSQL(CREATE_Salle_TABLE);
        // create machine table
        db.execSQL(CREATE_Machine_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older salle table if existed
        db.execSQL("DROP TABLE IF EXISTS salle");
        // Drop older machine table if existed
        db.execSQL("DROP TABLE IF EXISTS machine");
        // create fresh database
        this.onCreate(db);
    }
}