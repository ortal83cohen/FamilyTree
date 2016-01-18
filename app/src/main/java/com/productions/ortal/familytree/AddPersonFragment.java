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

import com.productions.ortal.familytree.models.Person;


public class AddPersonFragment extends Fragment {
    View mView;
    private Listener mListener;

    public AddPersonFragment() {
    }

    public static AddPersonFragment newInstance() {
        AddPersonFragment fragment = new AddPersonFragment();
        Bundle args = new Bundle();
//        args.putParcelable(ARG_REQUEST, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return mView = inflater.inflate(R.layout.fragment_add_person, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.submit) {
            AppCompatEditText firstName = (AppCompatEditText) mView.findViewById(R.id.first_name);
            AppCompatEditText lastName = (AppCompatEditText) mView.findViewById(R.id.last_name);
            mListener.addPerson(new Person(firstName.getText().toString(), lastName.getText().toString()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface Listener {
        void addPerson(Person person);
    }
}
