package ma.emsi.tp_sqlite.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ma.emsi.tp_sqlite.bean.Machine;
import ma.emsi.tp_sqlite.dao.IDao;
import ma.emsi.tp_sqlite.util.MySQLiteHelper;


public class MachineService implements IDao<Machine> {

    private MySQLiteHelper helper = null;
    private SalleService salleService = null;




    private static final String TABLE_MACHINE = "machine";

    // Salle_c
    private static final String KEY_ID = "idM";
    private static final String KEY_MARQUE = "marque";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_SALLE = "idSalle";

    private static final String[] COLUMNS = {KEY_ID, KEY_MARQUE, KEY_REFERENCE, KEY_SALLE};

    public MachineService(Context context) {
        this.helper = new MySQLiteHelper(context);
        this.salleService = new SalleService(context);
    }

    @Override
    public void add(Machine o) {
        Log.d("add", o.toString());

        SQLiteDatabase db = this.helper.getWritableDatabase();

        // ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_MARQUE, o.getMarque());
        values.put(KEY_REFERENCE, o.getRefernce());
        values.put(KEY_SALLE, o.getSalle().getId());


        db.insert(TABLE_MACHINE,
                null,
                values);


        db.close();

    }

    @Override
    public Machine findById(int id) {
        // Récupérer la reference avec readable DB
        SQLiteDatabase db = this.helper.getReadableDatabase();


        Cursor cursor =
                db.query(TABLE_MACHINE,
                        COLUMNS,
                        " idM = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();


        Machine machine = new Machine();
        machine.setId(Integer.parseInt(cursor.getString(0)));
        machine.setMarque(cursor.getString(1));
        machine.setRefernce(cursor.getString(2));
        machine.setSalle(salleService.findById(cursor.getInt(3)));


        Log.d("getMachine(" + id + ")", machine.toString());


        return machine;
    }
    // Lister les machines
    @Override
    public List<Machine> findAll() {
        List<Machine> machines = new LinkedList<>();

        String query = "SELECT  * FROM " + TABLE_MACHINE;

        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Machine machine = null;
        if (cursor.moveToFirst()) {
            do {
                machine = new Machine();
                machine.setId(Integer.parseInt(cursor.getString(0)));
                machine.setMarque(cursor.getString(1));
                machine.setRefernce(cursor.getString(2));
                machine.setSalle(salleService.findById(cursor.getInt(3)));

                machines.add(machine);
                Log.d("getAllMachine()", machine.toString());
            } while (cursor.moveToNext());
        }
        return machines;
    }

    // Lister les machines by id
    public List<Machine> findMachines(int id) {
        List<Machine> machines = new LinkedList<>();

        String query = "SELECT  * FROM " + TABLE_MACHINE + " where idSalle = " + id;

        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Machine machine = null;
        if (cursor.moveToFirst()) {
            do {
                machine = new Machine();
                machine.setId(Integer.parseInt(cursor.getString(0)));
                machine.setMarque(cursor.getString(1));
                machine.setRefernce(cursor.getString(2));
                machine.setSalle(salleService.findById(cursor.getInt(3)));

                machines.add(machine);
            } while (cursor.moveToNext());
        }
        return machines;
    }

    //Mise à jour
    @Override
    public void update(Machine o) {

        SQLiteDatabase db = this.helper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("marque", o.getMarque());
        values.put("reference", o.getRefernce());
        values.put(KEY_SALLE, o.getSalle().getId());


        int i = db.update(TABLE_MACHINE,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(o.getId())});
        Log.d("confirmation", "OK");

        db.close();

    }

    @Override
    public void delete(Machine o) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.helper.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MACHINE, //table name
                KEY_ID + " = ?",  // selections
                new String[]{String.valueOf(o.getId())}); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteMachine", o.toString());

    }

    public HashMap<String, Integer> machinesBySalle() {
        HashMap<String, Integer> stats = new HashMap<>();
        // 1. build the query
        String query = "SELECT libelle, count(*) FROM machine INNER JOIN salle on machine.idSalle = salle.idS group by idS;";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row
        if (cursor.moveToFirst()) {
            do {
                stats.put(cursor.getString(0), cursor.getInt(1));

            } while (cursor.moveToNext());
        }
        Log.d("machinesBySalle", stats.toString());
        return stats;
    }

}
