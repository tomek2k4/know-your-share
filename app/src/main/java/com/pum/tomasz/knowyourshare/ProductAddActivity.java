package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pum.tomasz.knowyourshare.data.MeasureUnit;
import com.pum.tomasz.knowyourshare.data.MeasureUnitTypeEnum;
import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductDatabaseFacade;
import com.pum.tomasz.knowyourshare.data.ProductDbOpenHelper;
import com.pum.tomasz.knowyourshare.preferences.Preferences;

import java.text.ParseException;
import java.util.Date;

public class ProductAddActivity extends Activity implements View.OnClickListener {

    private ProductDbOpenHelper dbOpenHelper = null;
    private ProductDatabaseFacade dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.initializeLocaleFromPreferences(this);
        Preferences.initializeMeasurementSystemFromPreferences(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_add);

        EditText buyDateTextView = (EditText) findViewById(R.id.new_buy_date_edittext);
        buyDateTextView.setText(Utilities.convertDateToString(new Date()));

        Button addProductButton = (Button) findViewById(R.id.add_product_activity_add_button);
        addProductButton.setOnClickListener(this);

        initializeSpinner();

        setupDatabaseConnection();
    }

    private void initializeSpinner() {
        String[] measurementTypeStringArray = getResources().getStringArray(R.array.measurement_type_enum_stringarray);

        for(int i=0;i<measurementTypeStringArray.length;i++){
            MeasureUnitTypeEnum mute = MeasureUnitTypeEnum.values()[i];
            MeasureUnit measureUnit = new MeasureUnit(mute);
            measurementTypeStringArray[i] = new String(measurementTypeStringArray[i] + "("+measureUnit.getUnitsName()+")");
        }

        Spinner measurementUnitTypeSpinner = (Spinner) findViewById(R.id.new_measure_type_spinner);
        measurementUnitTypeSpinner.setAdapter(new ArrayAdapter<String>
                (this, R.layout.measurement_type_spinner_item, measurementTypeStringArray));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.dispose();
    }

    private void setupDatabaseConnection() {
        if (dbOpenHelper == null) {
            dbOpenHelper = new ProductDbOpenHelper(this);
        }
        if (dbHelper == null) {
            dbHelper = new ProductDatabaseFacade(dbOpenHelper.getWritableDatabase());
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.add_product_activity_add_button){
            addProduct();
        }
    }

    private void addProduct() {
        Product p = new Product();

        String buffer;
        buffer = ((EditText) findViewById(R.id.new_product_name_edittext)).getText().toString().trim();
        if (buffer.length() == 0) {
            Toast.makeText(this, R.string.new_msg_empty_prduct_name,
                    Toast.LENGTH_LONG).show();
            return;
        }
        p.setName(buffer);

        buffer = ((EditText) findViewById(R.id.new_buy_date_edittext)).getText().toString().trim();
        try {
            Utilities.DATEFMT.parse(buffer);
        } catch (ParseException e) {
            Log.d(Utilities.TAG,"Error parsing date on adding new product");
            Toast.makeText(this, R.string.new_msg_format_buy_date,
                    Toast.LENGTH_LONG).show();
            return;
        }
        p.setBuyDate(buffer);

        Spinner measurementUnitTypeSpinner = (Spinner) findViewById(R.id.new_measure_type_spinner);


        MeasureUnitTypeEnum measureUnitTypeEnum =
                MeasureUnitTypeEnum.values()[measurementUnitTypeSpinner.getSelectedItemPosition()];
        p.setMeasureUnitTypeEnum(measureUnitTypeEnum);

        EditText valueEditText = (EditText) findViewById(R.id.new_value_edittext);
        buffer = valueEditText.getText().toString().trim();
        if (buffer.length() == 0) {
            Toast.makeText(this, R.string.new_msg_empty_value,
                    Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Double value = new Double(buffer);
            p.setSize(value);
        }catch (NumberFormatException e){
            Log.d(Utilities.TAG,"Wrong value formatting");
            Toast.makeText(this, R.string.new_msg_wrong_value_format,
                    Toast.LENGTH_LONG).show();
            return;
        }

        dbHelper.insert(p);
        finish();

    }


}
