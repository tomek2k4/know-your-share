package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pum.tomasz.knowyourshare.data.MeasureUnit;
import com.pum.tomasz.knowyourshare.preferences.Preferences;
import com.pum.tomasz.knowyourshare.share.ShareProvider;

import java.util.Locale;

/**
 * Created by tomasz on 15.07.2015.
 */
public class SettingsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        View rootView = (ScrollView)inflater.inflate(R.layout.fragment_settings, container, false);

        initializeSettingsLayoutComponents(rootView);

        return rootView;
    }

    private void initializeSettingsLayoutComponents(final View rootView) {

        int idx;
        SharedPreferences prefs = getActivity().getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

        RadioGroup languageRadioGroup = (RadioGroup) rootView.findViewById(R.id.settings_language_radiogroup);
        String languageString = prefs.getString(Preferences.KEY_LANGUAGE,Preferences.LanguageEnum.ENGLISH.name());
        idx = Preferences.LanguageEnum.valueOf(languageString).getValue();
        ((RadioButton) languageRadioGroup.getChildAt(idx)).setChecked(true);
        languageRadioGroup.setOnCheckedChangeListener(this);

        RadioGroup measureSystemRadioGroup = (RadioGroup) rootView.findViewById(R.id.settings_measurement_radiogroup);
        String measureString = prefs.getString(Preferences.KEY_MEASUREMENT_SYSTEM, Preferences.MeasurementSystemEnum.METRICAL.name());
        idx = Preferences.MeasurementSystemEnum.valueOf(measureString).getValue();
        ((RadioButton) measureSystemRadioGroup.getChildAt(idx)).setChecked(true);
        measureSystemRadioGroup.setOnCheckedChangeListener(this);

        EditText phoneEditText = (EditText) rootView.findViewById(R.id.settings_contact_phone_number_edittext);
        phoneEditText.setText(prefs.getString(Preferences.KEY_PHONE_NUMBER, ""));
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences prefs = getActivity().getApplicationContext()
                        .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

                EditText phoneEditText = (EditText) getView().findViewById(R.id.settings_contact_phone_number_edittext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Preferences.KEY_PHONE_NUMBER, phoneEditText.getText().toString());
                editor.commit();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        initializeLayoutButton(rootView.findViewById(R.id.set_share_app_button),
                getResources().getDrawable(R.drawable.share_today_sign), getString(R.string.share_app_default_name), -1);

        Button chooseContactButton = (Button) rootView.findViewById(R.id.settings_contact_browse_button);
        chooseContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareProvider shareProvider = new ShareProvider(getActivity());
                shareProvider.launchContactPicker();
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SharedPreferences prefs = getActivity().getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

        SharedPreferences.Editor editor = prefs.edit();
        int idx;
        Locale locale;
        Configuration config = new Configuration();

        switch (group.getId()){
            case R.id.settings_language_radiogroup:
                idx = group.indexOfChild(getView().findViewById(checkedId));
                locale = new Locale(Preferences.LanguageEnum.values()[idx].getCode());
                config.locale = locale;
                getActivity().getApplicationContext().getResources().updateConfiguration(config, null);
                editor.putString(Preferences.KEY_LANGUAGE,Preferences.LanguageEnum.values()[idx].name());
                break;
            case R.id.settings_measurement_radiogroup:
                idx = group.indexOfChild(getView().findViewById(checkedId));
                if(idx == Preferences.MeasurementSystemEnum.IMPERIAL.getValue()){
                    MeasureUnit.setImperialMeasureSystem(true);
                }else{
                    MeasureUnit.setImperialMeasureSystem(false);
                }
                editor.putString(Preferences.KEY_MEASUREMENT_SYSTEM, Preferences.MeasurementSystemEnum.values()[idx].name());
                break;
        }
        editor.commit();
        ((MainActivity)getActivity()).reattachAllFragments();
    }


    private void initializeLayoutButton(View buttonLayout, Drawable drawable, String title, Integer elements){

        // Draw all button components
        ImageView homeImageLeft = (ImageView)buttonLayout.findViewById(R.id.image_home_button);
        homeImageLeft.setImageDrawable(drawable);

        TextView homeTextView = (TextView) buttonLayout.findViewById(R.id.name_home_button);
        homeTextView.setText(title);

        TextView homeElementsRight = (TextView) buttonLayout.findViewById(R.id.text_elements_home_button);

        if(elements == -1){
            homeElementsRight.setText("");
        }else{
            homeElementsRight.setText(elements.toString());
        }

        buttonLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

    }
}
