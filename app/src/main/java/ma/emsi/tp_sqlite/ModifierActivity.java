package ma.emsi.tp_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.HashMap;

import ma.emsi.tp_sqlite.bean.Salle;
import ma.emsi.tp_sqlite.service.SalleService;

public class ModifierActivity extends AppCompatActivity {

    private EditText code;
    private EditText libelle;
    private Button modifier;
    private HashMap data;
    private Intent i;
    private Serializable s;
    private SalleService salleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        code = (EditText) findViewById(R.id.code);
        libelle = (EditText) findViewById(R.id.libelle);
        modifier = (Button) findViewById(R.id.edit);

        i = getIntent();
        s = getIntent().getSerializableExtra("data");
        if (s != null) {
            data = (HashMap) s;
            code.setText("" + data.get("code"));
            libelle.setText("" + data.get("libelle"));
        } else {
            Toast.makeText(this, " Impossible", Toast.LENGTH_LONG).show();
        }

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salleService = new SalleService(getApplicationContext());
                salleService.update(new Salle(Integer.parseInt(data.get("id").toString()), code.getText().toString(), libelle.getText().toString()));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("item","Salle");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
