package ma.emsi.tp_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.HashMap;

import ma.emsi.tp_sqlite.adapter.ListeMachinesSallesAdapter;
import ma.emsi.tp_sqlite.service.MachineService;

public class ListeMachinesSalles extends AppCompatActivity {

    private ListView list;
    private MachineService machineService;
    private View v;
    private HashMap data;
    private Intent i;
    private Serializable s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_machines_salles);
        list = (ListView) findViewById(R.id.listView);
        i = getIntent();
        s = getIntent().getSerializableExtra("data");
        data = (HashMap) s;
        machineService = new MachineService(this);
        ListeMachinesSallesAdapter as = new ListeMachinesSallesAdapter(this, machineService.findMachines(Integer.parseInt(data.get("id").toString())));
        list.setAdapter(as);
    }
}

