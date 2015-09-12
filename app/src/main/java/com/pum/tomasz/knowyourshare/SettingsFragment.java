package com.pum.tomasz.knowyourshare;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import android.content.pm.ActivityInfo;
import android.widget.Toast;

import com.pum.tomasz.knowyourshare.data.MeasureUnit;
import com.pum.tomasz.knowyourshare.preferences.Preferences;
import com.pum.tomasz.knowyourshare.share.ShareProvider;

import java.util.List;
import java.util.Locale;

/**
 * Created by tomasz on 15.07.2015.
 */
public class SettingsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {


    Intent email = new Intent(Intent.ACTION_SEND);
    AppAdapter adapter=null;

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

        initializeShareAppChooserButton(rootView);

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


    private void initializeShareAppChooserButton(View rootView){

        View buttonLayout = (View)rootView.findViewById(R.id.set_share_app_button);

        SharedPreferences prefs = getActivity().getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);

        String packageName = prefs.getString(Preferences.KEY_SHARE_APP_PACKAGE_NAME, "");

        Drawable icon = null;
        String label = null;

        try {
            PackageManager pm = getActivity().getPackageManager();
            ApplicationInfo app = pm.getApplicationInfo(packageName, 0);
            icon = pm.getApplicationIcon(app);
            label =(String) app.loadLabel(pm);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(Utilities.TAG, "Did not found app: " + e.getStackTrace().toString());
            icon = getResources().getDrawable(R.drawable.share_today_sign);
            label = getString(R.string.share_app_default_name);
        }


        // Draw all button components
        ImageView homeImageLeft = (ImageView)buttonLayout.findViewById(R.id.image_home_button);
        homeImageLeft.setImageDrawable(icon);

        TextView homeTextView = (TextView) buttonLayout.findViewById(R.id.name_home_button);
        homeTextView.setText(label);

        TextView homeElementsRight = (TextView) buttonLayout.findViewById(R.id.text_elements_home_button);
        homeElementsRight.setText("");

        buttonLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Log.d(Utilities.TAG,"Clicked on share app chooser button");
        showShareAppChooser();
    }

    private void showShareAppChooser() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_app_share_chooser);
        dialog.setCancelable(true);
        ListView lv=(ListView)dialog.findViewById(R.id.share_app_list_view);


        PackageManager pm = getActivity().getPackageManager();

        List<ResolveInfo> supportedApps = ShareProvider.getSupportedApps(pm);


        adapter = new AppAdapter(pm, supportedApps);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {



                ResolveInfo launchable = adapter.getItem(position);
                ActivityInfo activity = launchable.activityInfo;

                //Store package name and activity name in shared preferences
                SharedPreferences prefs = getActivity().getApplicationContext()
                        .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString(Preferences.KEY_SHARE_APP_PACKAGE_NAME, activity.applicationInfo.packageName);
                editor.putString(Preferences.KEY_SHARE_APP_ACTIVITY_NAME, activity.name);
                Log.d(Utilities.TAG, "Selected package is: " + activity.applicationInfo.packageName);
                Log.d(Utilities.TAG, "Selected app is: " + activity.name);
                editor.commit();
                dialog.cancel();

                ((MainActivity) getActivity()).reattachAllFragments();


//                ComponentName name=new ComponentName(activity.applicationInfo.packageName,
//                        activity.name);
//                email.addCategory(Intent.CATEGORY_LAUNCHER);
//                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                email.setComponent(name);
//                startActivity(email);
            }
        });
        dialog.show();

        //Get selected app and show on button
        initializeShareAppChooserButton(getView());



    }




    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(SettingsFragment.this.getActivity(), R.layout.dialog_app_share_item_row, apps);
            this.pm=pm;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView==null) {
                convertView=newView(parent);
            }
            bindView(position, convertView);
            return(convertView);
        }

        private View newView(ViewGroup parent) {
            return(getActivity().getLayoutInflater().inflate(R.layout.dialog_app_share_item_row, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label=(TextView)row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon=(ImageView)row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }
}
