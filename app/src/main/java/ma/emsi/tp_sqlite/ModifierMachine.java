package ma.emsi.tp_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ma.emsi.tp_sqlite.bean.Machine;
import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.service.MachineService;
import ma.emsi.tp_sqlite.service.SalleService;

public class ModifierMachine extends AppCompatActivity {

    private EditText marque;
    private EditText reference;
    private Spinner spinner;
    private Button modifier;
    private HashMap data;
    private Intent i;
    private Serializable s;
    private MachineService machineService;
    private SalleService salleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_machine);

        marque = (EditText) findViewById(R.id.marque);
        reference = (EditText) findViewById(R.id.reference);
        modifier = (Button) findViewById(R.id.modifier);
        spinner = (Spinner) findViewById(R.id.Spinner);
        salleService = new SalleService(getApplicationContext());

        ArrayAdapter<String> adapter;
        List<String> liste = new ArrayList<String>();
        for (Salle salle : salleService.findAll()) {
            liste.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, liste);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        i = getIntent();
        s = getIntent().getSerializableExtra("data");
        if (s != null) {
            data = (HashMap) s;
            marque.setText("" + data.get("marque"));
            reference.setText("" + data.get("reference"));
            for (int i = 0; i < spinner.getCount(); i++) {
                String item = spinner.getItemAtPosition(i).toString();
                if (item.equals(data.get("idSalle").toString())) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            Toast.makeText(this, " Impossible", Toast.LENGTH_LONG).show();
        }

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salleService = new SalleService(getApplicationContext());
                machineService = new MachineService(getApplicationContext());
                int id = salleService.findByCode(spinner.getSelectedItem().toString()).getId();
                machineService.update(new Machine(Integer.parseInt(data.get("id").toString()), marque.getText().toString(), reference.getText().toString(), salleService.findById(id)));
                Log.d("Marque : ", marque.getText().toString());
                Log.d("Reference : ", reference.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("item", "Machine");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
}
