package com.productions.ortal.familytree;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.productions.ortal.familytree.customViews.PersonView;
import com.productions.ortal.familytree.managers.FamilyManager;
import com.productions.ortal.familytree.models.Person;
import com.productions.ortal.familytree.models.Relation;

import java.util.HashMap;
import java.util.Map;

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

        if (!drawTree()) { //if the tree is empty show the "add button"
            mFab.setVisibility(View.VISIBLE);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFab.setVisibility(View.GONE);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, AddPersonFragment.newInstance(0),
                                    FRAGMENT_ADD_PERSON)
                            .commit();
                }
            });
        }

    }

    private boolean drawTree() {
        HashMap<String, Person> persons = mFamilyManager.getPersons();
        HashMap<String, Relation> relations = mFamilyManager.getRelations();

        if (persons.size() == 0) { // there is no data
            return false;
        }
        LinearLayout treeView = (LinearLayout) findViewById(R.id.tree_view);

        treeView.removeAllViews();
// for each person draw costume view
        for (Map.Entry<String, Person> person : persons.entrySet()) {
            final int id = person.getValue().getId();
            Relation relation = relations.get(String.valueOf(id));
            PersonView personView;
            if(relation != null) {//if there are relations
                Person toPerson = persons.get(relation.getPerson1Id());
                personView = new PersonView(this, person.getValue(), relation.getRelative(), toPerson);
            }else {
                personView = new PersonView(this, person.getValue(),"", null);
            }
            personView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, AddPersonFragment.newInstance(id),
                                    FRAGMENT_ADD_PERSON)
                            .commit();
                }
            });
            treeView.addView(personView);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addPerson(Person person, String relative, int referenceToPerson) {

        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag(FRAGMENT_ADD_PERSON))
                .commit();

        mFamilyManager.addFamilyMember(person, relative, referenceToPerson);
        drawTree();
    }

}
