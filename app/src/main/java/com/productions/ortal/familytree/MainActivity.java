package com.productions.ortal.familytree;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.productions.ortal.familytree.managers.FamilyManager;
import com.productions.ortal.familytree.models.Person;

public class MainActivity extends AppCompatActivity implements AddPersonFragment.Listener {
    private static final String FRAGMENT_ADD_PERSON = "add_person";
    private FloatingActionButton mFab;
    private FamilyManager mFamilyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFamilyManager = new FamilyManager(this);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setVisibility(View.GONE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddPersonFragment.newInstance(),
                                FRAGMENT_ADD_PERSON)
                        .commit();
            }
        });


        String x = mFamilyManager.getPersons();

        Toast.makeText(this, x, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addPerson(Person person) {
        mFab.setVisibility(View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_ADD_PERSON))
                .commit();

        mFamilyManager.addFamilyMember(person);
    }
}
