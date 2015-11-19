package com.brainiac;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.brainiac.model.Alarme;
import com.brainiac.model.dao.AlarmeDAO;
import com.brainiac.model.sqlite.BrainiacDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;

    private AlarmeDAO alarmeDAO;

    private List<Alarme> listAlarmes;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, AlarmeActivity.class);

                Bundle bundle = new Bundle();
                it.setFlags(AlarmeActivity.INCLUIR);

                startActivity(it);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        listAlarmes = new ArrayList<>();
        mAdapter = new MainAdapter(listAlarmes);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbHelper = new BrainiacDbHelper(this);

        alarmeDAO = new AlarmeDAO(this.dbHelper);

        listAlarmes.clear();
        for(Alarme a : alarmeDAO.consultarTodos()) {
            listAlarmes.add(a);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
