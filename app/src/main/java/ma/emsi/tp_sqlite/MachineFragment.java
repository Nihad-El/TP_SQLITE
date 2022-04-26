package ma.emsi.tp_sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ma.emsi.tp_sqlite.adapter.MachineAdapter;
import ma.emsi.tp_sqlite.adapter.SalleAdapter;
import ma.emsi.tp_sqlite.bean.Machine;
import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.service.MachineService;
import ma.emsi.tp_sqlite.service.SalleService;

public class MachineFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View machineView;

    private EditText marque;
    private EditText reference;
    private Button create;
    private Button listeMachines;
    private Button menu;
    private Spinner spinner;
    private SalleService salleService;

    private ListView list;
    private MachineService machineService;
    private View v;
    ArrayAdapter<String> adapter;
    MachineAdapter as;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        machineView = inflater.inflate(R.layout.machine_fragment, container, false);

        salleService = new SalleService(getContext());
        marque = (EditText) machineView.findViewById(R.id.marque);
        reference = (EditText) machineView.findViewById(R.id.reference);
        create = (Button) machineView.findViewById(R.id.btnCreate);
        spinner = (Spinner) machineView.findViewById(R.id.Spinner);


        List<String> liste = new ArrayList<String>();
        for (Salle salle : salleService.findAll()) {
            liste.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, liste);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineService machineService = new MachineService(getContext());
                salleService = new SalleService(getContext());
                Salle salle = salleService.findByCode(spinner.getSelectedItem().toString());
                machineService.add(new Machine(marque.getText().toString(), reference.getText().toString(), salle));
                as = new MachineAdapter(getContext(), machineService.findAll());
                list.setAdapter(as);
                list.setOnItemClickListener(MachineFragment.this);
                as.notifyDataSetChanged();
                Toast.makeText(getContext(), " Machine crée avec succès  ", Toast.LENGTH_LONG).show();
            }
        });

        list = (ListView) machineView.findViewById(R.id.listView);
        machineService = new MachineService(getContext());
        as = new MachineAdapter(getContext(), machineService.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(this);

        return machineView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        v = view;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogBuilder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                        machineService.delete(machineService.findById(id));
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("item","Machine");
                        startActivity(intent);
                    }
                });
                alertDialogBuilder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder1.create();
                alertDialog.show();
            }
        });

        alertDialogBuilder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getContext(), ModifierMachine.class);
                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                Machine machine = machineService.findById(id);
                HashMap<String, String> data = new HashMap<>();
                data.put("id", machine.getId() + "");
                data.put("idSalle", machine.getSalle().getCode() + "");
                data.put("marque", machine.getMarque());
                data.put("reference", machine.getRefernce());
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog1 = alertDialogBuilder.create();
        alertDialog1.show();
    }
}
