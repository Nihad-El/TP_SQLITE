package ma.emsi.tp_sqlite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);


                    Intent intent = new Intent(MActivity.this, MainActivity.class);
                    startActivity(intent);
                    MActivity.this.finish();

    }
}
