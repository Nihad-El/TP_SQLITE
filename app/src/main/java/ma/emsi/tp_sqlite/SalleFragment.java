package ma.emsi.tp_sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import ma.emsi.tp_sqlite.adapter.SalleAdapter;
import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.service.SalleService;

public class SalleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View salleView;
    private RecyclerView recyclerView;
    private EditText code;
    private EditText libelle;
    private Button add;

    private ListView list;
    private View v;
    private SalleService ad;

    SalleAdapter as;
    SalleService db = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        salleView = inflater.inflate(R.layout.salle_fragment, container, false);

        code = (EditText) salleView.findViewById(R.id.code);
        libelle = (EditText) salleView.findViewById(R.id.libelle);
        add = (Button) salleView.findViewById(R.id.add);

        list = (ListView) salleView.findViewById(R.id.listView);
        ad = new SalleService(getContext());

        as = new SalleAdapter(getContext(), ad.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new SalleService(getContext());
                db.add(new Salle(code.getText().toString(), libelle.getText().toString()));
                as = new SalleAdapter(getContext(), ad.findAll());
                list.setAdapter(as);
                list.setOnItemClickListener(SalleFragment.this);
                as.notifyDataSetChanged();
                Toast.makeText(getContext(), " Salle crée avec succès ", Toast.LENGTH_LONG).show();
            }
        });
        return salleView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(this, ((TextView) view.findViewById(R.id.ids)).getText().toString() + " " + ((TextView) view.findViewById(R.id.code)).getText() + " " + ((TextView) view.findViewById(R.id.libelle)).getText(), Toast.LENGTH_LONG).show();
        v = view;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialogBuilder1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                        ad.delete(ad.findById(id));
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("item", "Salle");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                Intent intent = new Intent(getContext(), ModifierActivity.class);
                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                Salle salle = ad.findById(id);
                HashMap<String, String> data = new HashMap<>();
                data.put("id", salle.getId() + "");
                data.put("code", salle.getCode());
                data.put("libelle", salle.getLibelle());
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("Machines List", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), ListeMachinesSalles.class);
                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                Salle salle = ad.findById(id);
                HashMap<String, String> data = new HashMap<>();
                data.put("id", salle.getId() + "");
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog1 = alertDialogBuilder.create();
        alertDialog1.show();
    }
}
