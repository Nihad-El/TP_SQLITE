package ma.emsi.tp_sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        String item;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SalleFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_salles);
            } else {
                item = extras.getString("item");
                if (item.equals("Salle")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SalleFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_salles);
                }
                if (item.equals("Machine")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SalleFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_machines);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_salles:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SalleFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_machines);
                break;
            case R.id.nav_machines:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MachineFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_machines);
                break;
            case R.id.nav_machines_salles:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MachineSalleFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_machines_salles);
                break;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChartFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_machines_salles);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}