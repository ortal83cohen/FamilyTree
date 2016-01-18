package com.productions.ortal.familytree;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.productions.ortal.familytree.models.Person;


public class AddPersonFragment extends Fragment {
    private static final String REFERENCE = "reference";
    View mView;
    private Listener mListener;
    private int mReference;

    public AddPersonFragment() {
    }

    public static AddPersonFragment newInstance(int Reference) {
        AddPersonFragment fragment = new AddPersonFragment();
        Bundle args = new Bundle();
        args.putInt(REFERENCE, Reference);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            mReference = savedInstanceState.getInt(REFERENCE);
        } else {
            mReference = getArguments().getInt(REFERENCE);
        }
        mView = inflater.inflate(R.layout.fragment_add_person, container, false);


        Spinner relative = (Spinner) mView.findViewById(R.id.relative);
        if (mReference != 0) { //if there is reference
            String[] list = new String[]{"son", "couple"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            relative.setAdapter(adapter);
        } else {
            relative.setVisibility(View.GONE);
        }
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(REFERENCE, mReference);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_person, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //on click on save button
        if (id == R.id.submit) {
            AppCompatEditText firstName = (AppCompatEditText) mView.findViewById(R.id.first_name);
            AppCompatEditText lastName = (AppCompatEditText) mView.findViewById(R.id.last_name);
            Spinner relative = (Spinner) mView.findViewById(R.id.relative);//todo
            mListener.addPerson(new Person(firstName.getText().toString(), lastName.getText().toString()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface Listener {
        void addPerson(Person person);
    }
}
