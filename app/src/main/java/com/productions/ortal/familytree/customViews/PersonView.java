package com.productions.ortal.familytree.customViews;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.productions.ortal.familytree.R;
import com.productions.ortal.familytree.models.Person;

/**
 * Created by user on 1/18/2016.
 */
public class PersonView extends LinearLayout {

    public PersonView(Context context, Person person) {
        super(context);
        LinearLayout view = (LinearLayout) View.inflate(context, R.layout.view_person, null);
        TextView firstName = (TextView) view.findViewById(R.id.first_name);
        TextView lastName = (TextView) view.findViewById(R.id.last_name);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        addView(view);

        setPadding(8, 8, 8, 8);
        setOrientation(VERTICAL);
        LayoutParams lp = new LayoutParams(((int) context.getResources().getDimension(R.dimen.person_box)), ((int) context.getResources().getDimension(R.dimen.person_box)));
        lp.setMargins(10, 10, 10, 10);
        setLayoutParams(lp);


    }
}
