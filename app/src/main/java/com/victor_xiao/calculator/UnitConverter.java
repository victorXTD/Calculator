package com.victor_xiao.calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Victor_Xiao on 16/10/26.
 */

public class UnitConverter extends AppCompatActivity {

    EditText text_num;
    TextView text_result;
    Spinner type;
    Spinner from;
    Spinner to;

    ArrayAdapter<String> typeAdapter = null;  //省级适配器
    ArrayAdapter<String> unitAdapter = null;    //地级适配器

    static int typePosition = 0;

    private String[] typeS = new String[]{"length", "weight", "time", "temperature", "speed"};
    private String[][] unit = new String[][]
            {
                    {"km", "m", "dm", "cm"},
                    {"t", "kg", "g", "mg"},
                    {"h", "min", "s", "ms"},
                    {"C", "F", "K"},
                    {"m/s", "km/s", "km/h"}
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(UnitConverter.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.dialog_help, null);

                builder.setView(v);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        text_num = (EditText) findViewById(R.id.text_num);
        text_result = (TextView) findViewById(R.id.result);

        type = (Spinner) findViewById(R.id.type);
        from = (Spinner) findViewById(R.id.from);
        to = (Spinner) findViewById(R.id.to);


        Intent intent = getIntent();
        String str = intent.getStringExtra("exdata");

        text_num.setText(str);

        typeAdapter = new ArrayAdapter<String>(UnitConverter.this,
                android.R.layout.simple_spinner_dropdown_item, typeS);
        type.setAdapter(typeAdapter);
        type.setSelection(0, true);  //设置默认选中项

        unitAdapter = new ArrayAdapter<String>(UnitConverter.this,
                android.R.layout.simple_spinner_dropdown_item, unit[0]);
        from.setAdapter(unitAdapter);
        from.setSelection(1, true);  //设置默认选中项
        to.setAdapter(unitAdapter);
        to.setSelection(2, true);  //设置默认选中项

        text_num.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(text_num.getText().toString()))
                    text_num.setText("0");

                switch (typePosition) {
                    case 0: {
                        lengthConverter();
                    }
                    break;
                    case 1: {
                        weightConverter();
                    }
                    break;
                    case 2: {
                        timeConverter();
                    }
                    break;
                    case 3: {
                        temperatureConverter();
                    }
                    break;
                    case 4: {
                        speedConverter();
                    }
                    break;

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lengthConverter();

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                unitAdapter = new ArrayAdapter<String>(
                        UnitConverter.this, android.R.layout.simple_spinner_dropdown_item, unit[pos]);
                from.setAdapter(unitAdapter);
                to.setAdapter(unitAdapter);

                typePosition = pos;

                defaultSelection(); //设置默认选中项
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                switch (typePosition) {
                    case 0: {
                        lengthConverter();
                    }
                    break;
                    case 1: {
                        weightConverter();
                    }
                    break;
                    case 2: {
                        timeConverter();
                    }
                    break;
                    case 3: {
                        temperatureConverter();
                    }
                    break;
                    case 4: {
                        speedConverter();
                    }
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                switch (typePosition) {
                    case 0: {
                        lengthConverter();
                    }
                    break;
                    case 1: {
                        weightConverter();
                    }
                    break;
                    case 2: {
                        timeConverter();
                    }
                    break;
                    case 3: {
                        temperatureConverter();
                    }
                    break;
                    case 4: {
                        speedConverter();
                    }
                    break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void defaultSelection() {
        switch (typePosition) {
            case 0:
            case 1:
            case 2: {
                from.setSelection(1, true);  //设置默认选中项
                to.setSelection(2, true);
            }
            break;
            case 3:
            case 4: {
                from.setSelection(0, true);  //设置默认选中项
                to.setSelection(1, true);
            }
            break;
        }
    }

    public void lengthConverter() {
        String in, out;
        in = from.getSelectedItem().toString();
        out = to.getSelectedItem().toString();

        double input = Double.parseDouble(text_num.getText().toString());
        if ("km".equals(in)) {
            if ("km".equals(out)) {
                text_result.setText(input + "");
            } else if ("m".equals(out)) {
                text_result.setText(input * 1000 + "");
            } else if ("dm".equals(out)) {
                text_result.setText(input * 10000 + "");
            } else {
                text_result.setText(input * 100000 + "");
            }
        } else if ("m".equals(in)) {
            if ("km".equals(out)) {
                text_result.setText(input / 1000 + "");
            } else if ("m".equals(out)) {
                text_result.setText(input + "");
            } else if ("dm".equals(out)) {
                text_result.setText(input * 10 + "");
            } else {
                text_result.setText(input * 100 + "");
            }
        } else if ("dm".equals(in)) {
            if ("km".equals(out)) {
                text_result.setText(input / 10000 + "");
            } else if ("m".equals(out)) {
                text_result.setText(input / 10 + "");
            } else if ("dm".equals(out)) {
                text_result.setText(input + "");
            } else {
                text_result.setText(input * 10 + "");
            }
        } else {
            if ("km".equals(out)) {
                text_result.setText(input / 100000 + "");
            } else if ("m".equals(out)) {
                text_result.setText(input / 100 + "");
            } else if ("dm".equals(out)) {
                text_result.setText(input / 10 + "");
            } else {
                text_result.setText(input + "");
            }
        }
    }

    public void weightConverter() {
        String in, out;
        in = from.getSelectedItem().toString();
        out = to.getSelectedItem().toString();

        double input = Double.parseDouble(text_num.getText().toString());
        if ("t".equals(in)) {
            if ("t".equals(out)) {
                text_result.setText(input + "");
            } else if ("kg".equals(out)) {
                text_result.setText(input * 1000 + "");
            } else if ("g".equals(out)) {
                text_result.setText(input * 10000 + "");
            } else {
                text_result.setText(input * 10000000 + "");
            }
        } else if ("kg".equals(in)) {
            if ("t".equals(out)) {
                text_result.setText(input / 1000 + "");
            } else if ("kg".equals(out)) {
                text_result.setText(input + "");
            } else if ("g".equals(out)) {
                text_result.setText(input * 10 + "");
            } else {
                text_result.setText(input * 10000 + "");
            }
        } else if ("g".equals(in)) {
            if ("t".equals(out)) {
                text_result.setText(input / 10000 + "");
            } else if ("kg".equals(out)) {
                text_result.setText(input / 10 + "");
            } else if ("g".equals(out)) {
                text_result.setText(input + "");
            } else {
                text_result.setText(input * 1000 + "");
            }
        } else {
            if ("t".equals(out)) {
                text_result.setText(input / 10000000 + "");
            } else if ("kg".equals(out)) {
                text_result.setText(input / 10000 + "");
            } else if ("g".equals(out)) {
                text_result.setText(input / 10 + "");
            } else {
                text_result.setText(input + "");
            }
        }
    }

    public void timeConverter() {
        String in, out;
        in = from.getSelectedItem().toString();
        out = to.getSelectedItem().toString();

        double input = Double.parseDouble(text_num.getText().toString());
        if ("h".equals(in)) {
            if ("h".equals(out)) {
                text_result.setText(input + "");
            } else if ("min".equals(out)) {
                text_result.setText(input * 60 + "");
            } else if ("s".equals(out)) {
                text_result.setText(input * 3600 + "");
            } else {
                text_result.setText(input * 3600 * 1000 + "");
            }
        } else if ("min".equals(in)) {
            if ("h".equals(out)) {
                text_result.setText(input / 60 + "");
            } else if ("min".equals(out)) {
                text_result.setText(input + "");
            } else if ("s".equals(out)) {
                text_result.setText(input * 60 + "");
            } else {
                text_result.setText(input * 60 * 1000 + "");
            }
        } else if ("s".equals(in)) {
            if ("h".equals(out)) {
                text_result.setText(input / 3600 + "");
            } else if ("min".equals(out)) {
                text_result.setText(input / 60 + "");
            } else if ("s".equals(out)) {
                text_result.setText(input + "");
            } else {
                text_result.setText(input * 1000 + "");
            }
        } else {
            if ("h".equals(out)) {
                text_result.setText(input / 3600 / 1000 + "");
            } else if ("min".equals(out)) {
                text_result.setText(input / 60 / 1000 + "");
            } else if ("s".equals(out)) {
                text_result.setText(input / 1000 + "");
            } else {
                text_result.setText(input + "");
            }
        }
    }

    public void temperatureConverter() {
        String in, out;
        in = from.getSelectedItem().toString();
        out = to.getSelectedItem().toString();

        double input = Double.parseDouble(text_num.getText().toString());
        if ("C".equals(in)) {
            if ("C".equals(out)) {
                text_result.setText(input + "");
            } else if ("K".equals(out)) {
                text_result.setText(input + 273.15 + "");
            } else {
                text_result.setText(input * 9 / 5 + 32 + "");
            }
        } else if ("K".equals(in)) {
            if ("C".equals(out)) {
                text_result.setText(input - 273.5 + "");
            } else if ("K".equals(out)) {
                text_result.setText(input + "");
            } else {
                text_result.setText((input - 32) * 5 / 9 + 273.15 + "");
            }
        } else {
            if ("C".equals(out)) {
                text_result.setText((input - 32) * 5 / 9 + "");
            } else if ("K".equals(out)) {
                text_result.setText(((input - 32) * 5 / 9) + 273.15 + "");
            } else {
                text_result.setText(input + "");
            }
        }
    }

    public void speedConverter() {
        String in, out;
        in = from.getSelectedItem().toString();
        out = to.getSelectedItem().toString();

        double input = Double.parseDouble(text_num.getText().toString());
        if ("m/s".equals(in)) {
            if ("m/s".equals(out)) {
                text_result.setText(input + "");
            } else if ("km/s".equals(out)) {
                text_result.setText(input / 1000 + "");
            } else {
                text_result.setText(input * 3.6 + "");
            }
        } else if ("km/s".equals(in)) {
            if ("m/s".equals(out)) {
                text_result.setText(input * 1000 + "");
            } else if ("km/s".equals(out)) {
                text_result.setText(input + "");
            } else {
                text_result.setText(input * 3600 + "");
            }
        } else {
            if ("m/s".equals(out)) {
                text_result.setText(input / 3.6 + "");
            } else if ("km/s".equals(out)) {
                text_result.setText(input / 3600 + "");
            } else {
                text_result.setText(input + "");
            }
        }
    }

}