package com.jdkgroup.dynamiclayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {


    LinearLayout llDynamic;
    private static int viewsCount = 0;
    private List<View> allViews = new ArrayList<View>();
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_form);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(5, 5, 5, 5);

        llDynamic = (LinearLayout) findViewById(R.id.llDynamic);
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("Select");
        spinnerList.add("Hyderabad");
        spinnerList.add("Banglore");
        spinnerList.add("Chennai");
        spinnerList.add("Delhi");
        spinnerList.add("Mumbai");

        appCompatEditText("First Name");
        appCompatEditText("Last Name");
        appCompatEditText("Age");
        appCompatEditText("Address");
        appCompatSpinner(spinnerList);
        appCompatEditText("State");

        appCompatCheckBox("Key Contact");
        appCompatCheckBox("Target contact");
        createRadio("");
        createRadio("");
        saveButton();
    }

    private void saveButton() {
        Button saveButton = new Button(this);
        saveButton.setHeight(WRAP_CONTENT);
        saveButton.setText("Save");
        saveButton.setOnClickListener(submitListener);
        llDynamic.addView(saveButton, params);
    }

    private View.OnClickListener submitListener = new View.OnClickListener() {
        public void onClick(View view) {
            StringBuilder stringBuilder = new StringBuilder();
            for (View singView : allViews) {

                String className = getClassName(singView.getClass());

                if (className.equalsIgnoreCase("AppCompatEditText")) {
                    EditText editText = (EditText) singView;
                    stringBuilder.append(" " + editText.getText());
                } else if (className.equalsIgnoreCase("AppCompatSpinner")) {
                    Spinner spiner = (Spinner) singView;
                    stringBuilder.append(" " + spiner.getSelectedItem());
                } else if (className.equalsIgnoreCase("AppCompatCheckBox")) {
                    CheckBox checkBox = (CheckBox) singView;
                    stringBuilder.append(" " + checkBox.isChecked());
                } else if (className.equalsIgnoreCase("RadioGroup")) {
                    RadioGroup radioGroup = (RadioGroup) singView;

                    if(radioGroup.getCheckedRadioButtonId()!=-1){
                        int id= radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(id);
                        int radioId = radioGroup.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                        String selection = (String) btn.getText();

                        stringBuilder.append(" " + selection);
                    }



                }

            }
            Log.i("All Data ", stringBuilder.toString());
            showAlertDialog(view.getContext(), "Data", stringBuilder.toString());
        }
    };


    public void appCompatEditText(String hint) {
        AppCompatEditText editText = new AppCompatEditText(this);
        editText.setId(viewsCount++);
        editText.setHint(hint);
        allViews.add(editText);
        llDynamic.addView(editText, params);
    }

    public void appCompatSpinner(List<String> spinnerList) {
        AppCompatSpinner spinner = new AppCompatSpinner(this);
        spinner.setId(viewsCount++);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(spinnerArrayAdapter);
        allViews.add(spinner);
        llDynamic.addView(spinner, params);
    }

    public void appCompatCheckBox(String label) {

        final AppCompatCheckBox checkBox = new AppCompatCheckBox(this);
        checkBox.setId(viewsCount++);
        checkBox.setText(label);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        allViews.add(checkBox);
        llDynamic.addView(checkBox, params);
    }

    public void createRadio(String label) {

        int buttons = 2;
        RadioGroup rgp = new RadioGroup(this);
        rgp.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i <= buttons; i++) {
            AppCompatRadioButton rbn = new AppCompatRadioButton(this);
            rbn.setId(viewsCount++);
            rbn.setText("RadioButton" + i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rbn.setLayoutParams(params);
            rgp.addView(rbn);
        }
        allViews.add(rgp);
        llDynamic.addView(rgp, params);
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null)
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        builder.show();
    }

    public static String getClassName(Class c) {
        String className = c.getName();
        int firstChar;
        firstChar = className.lastIndexOf('.') + 1;
        if (firstChar > 0) {
            className = className.substring(firstChar);
        }
        return className;
    }

}