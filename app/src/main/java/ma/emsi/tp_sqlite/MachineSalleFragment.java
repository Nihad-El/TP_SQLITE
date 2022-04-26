package ma.emsi.tp_sqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.tp_sqlite.adapter.ListeMachinesSallesAdapter;
import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.service.MachineService;
import ma.emsi.tp_sqlite.service.SalleService;

public class MachineSalleFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View machineSalleView;
    private Spinner spinner;
    private SalleService salleService;
    private MachineService machineService;
    private ListView liste;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        machineSalleView = inflater.inflate(R.layout.machine_salle_fragment, container, false);

        salleService = new SalleService(getContext());
        spinner = (Spinner) machineSalleView.findViewById(R.id.spinner);
        liste = (ListView) machineSalleView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();
        for (Salle salle : salleService.findAll()) {
            list.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return machineSalleView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        machineService = new MachineService(getContext());
        int id = salleService.findByCode(spinner.getSelectedItem().toString()).getId();
        ListeMachinesSallesAdapter as = new ListeMachinesSallesAdapter(getContext(), machineService.findMachines(id));
        liste.setAdapter(as);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
