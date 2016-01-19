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

    public PersonView(Context context, Person person, String relative, Person toPerson) {
        super(context);
        LinearLayout view = (LinearLayout) View.inflate(context, R.layout.view_person, null);
        TextView firstNameTextView = (TextView) view.findViewById(R.id.first_name);
        TextView lastNameTextView = (TextView) view.findViewById(R.id.last_name);
        TextView relativeTextView = (TextView) view.findViewById(R.id.relative);
        firstNameTextView.setText(person.getFirstName());
        lastNameTextView.setText(person.getLastName());
        if(relative!="") {
            relativeTextView.setText(relative + " of " + toPerson.getFirstName() + " " + toPerson.getLastName());
        }else {
            relativeTextView.setVisibility(GONE);
        }
        addView(view);

        setPadding(8, 8, 8, 8);
        setOrientation(VERTICAL);
        LayoutParams lp = new LayoutParams(((int) context.getResources().getDimension(R.dimen.person_box_width)), ((int) context.getResources().getDimension(R.dimen.person_box_height)));
        lp.setMargins(10, 10, 10, 10);
        setLayoutParams(lp);


    }
}
