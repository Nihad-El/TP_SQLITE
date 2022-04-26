package ma.emsi.tp_sqlite.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.dao.IDao;
import ma.emsi.tp_sqlite.util.MySQLiteHelper;


public class SalleService implements IDao<Salle> {

    private MySQLiteHelper helper = null;


    private static final String TABLE_SALLE = "salle";

    private static final String KEY_ID = "idS";
    private static final String KEY_CODE = "code";
    private static final String KEY_LIBELLE = "libelle";
    private static final String[] COLUMNS = {KEY_ID, KEY_CODE, KEY_LIBELLE};

    public SalleService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    @Override
    public void add(Salle o) {

        Log.d("Create", o.toString());
        SQLiteDatabase db = this.helper.getWritableDatabase();
        // Creer ContentValues pour ajouter key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, o.getCode());
        values.put(KEY_LIBELLE, o.getLibelle());

        db.insert(TABLE_SALLE,
                null,
                values);

        db.close();
    }

    @Override
    public Salle findById(int id) {
        // Reference by readable DB
        SQLiteDatabase db = this.helper.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_SALLE,
                        COLUMNS,
                        " idS = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();
        Salle salle = new Salle();
        salle.setId(Integer.parseInt(cursor.getString(0)));
        salle.setCode(cursor.getString(1));
        salle.setLibelle(cursor.getString(2));

        return salle;
    }

    public Salle findByCode(String code) {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.helper.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_SALLE,
                        COLUMNS,
                        " code = ?",
                        new String[]{code},
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();


        Salle salle = new Salle();
        salle.setId(Integer.parseInt(cursor.getString(0)));
        salle.setCode(cursor.getString(1));
        salle.setLibelle(cursor.getString(2));

        return salle;
    }

    @Override
    public List<Salle> findAll() {
        List<Salle> salles = new LinkedList<>();

        String query = "SELECT  * FROM " + TABLE_SALLE;

        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Salle salle = null;
        if (cursor.moveToFirst()) {
            do {
                salle = new Salle();
                salle.setId(Integer.parseInt(cursor.getString(0)));
                salle.setCode(cursor.getString(1));
                salle.setLibelle(cursor.getString(2));

                salles.add(salle);
                Log.d("findAllSalle()", salle.toString());
            } while (cursor.moveToNext());
        }

        return salles;
    }


    @Override
    public void update(Salle o) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", o.getCode());
        Log.d("code", " " + o.getCode());
        values.put("libelle", o.getLibelle());
        Log.d("code", " " + o.getLibelle());
        Log.d("code", " " + o.getId());
        // 3. updating row
        int i = db.update(TABLE_SALLE,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(o.getId())});

        db.close();
    }

    @Override
    public void delete(Salle o) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        db.delete(TABLE_SALLE,
                KEY_ID + " = ?",
                new String[]{String.valueOf(o.getId())});

        db.close();

        Log.d("deletesalle", o.toString());
    }
}