package com.victor_xiao.calculator;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_1;
    Button bt_2;
    Button bt_3;
    Button bt_4;
    Button bt_5;
    Button bt_6;
    Button bt_7;
    Button bt_8;
    Button bt_9;
    Button bt_0;
    Button bt_plus;
    Button bt_minus;
    Button bt_multiply;
    Button bt_divide;
    Button bt_ce;
    Button bt_c;
    Button bt_del;
    Button bt_point;
    Button bt_negate;
    Button bt_equal;
    Button bt_sin;
    Button bt_cos;
    Button bt_pow;

    RadioButton rb_dec;
    RadioButton rb_oct;
    RadioButton rb_bin;

    TextView tv_input;
    TextView tv_output;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化所有控件
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_5 = (Button) findViewById(R.id.bt_5);
        bt_6 = (Button) findViewById(R.id.bt_6);
        bt_7 = (Button) findViewById(R.id.bt_7);
        bt_8 = (Button) findViewById(R.id.bt_8);
        bt_9 = (Button) findViewById(R.id.bt_9);
        bt_0 = (Button) findViewById(R.id.bt_0);
        bt_plus = (Button) findViewById(R.id.bt_plus);
        bt_minus = (Button) findViewById(R.id.bt_minus);
        bt_multiply = (Button) findViewById(R.id.bt_multiply);
        bt_divide = (Button) findViewById(R.id.bt_divide);
        bt_ce = (Button) findViewById(R.id.bt_ce);
        bt_c = (Button) findViewById(R.id.bt_c);
        bt_del = (Button) findViewById(R.id.bt_del);
        bt_point = (Button) findViewById(R.id.bt_point);
        bt_negate = (Button) findViewById(R.id.bt_negate);
        bt_equal = (Button) findViewById(R.id.bt_equal);
        bt_sin = (Button) findViewById(R.id.bt_sin);
        bt_cos = (Button) findViewById(R.id.bt_cos);
        bt_pow = (Button) findViewById(R.id.bt_pow);

        rb_dec = (RadioButton) findViewById(R.id.rb_decimal);
        rb_oct = (RadioButton) findViewById(R.id.rb_octal);
        rb_bin = (RadioButton) findViewById(R.id.rb_binary);

        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_output = (TextView) findViewById(R.id.tv_output);

        registerForContextMenu(tv_input);

        //为按钮设置监听器
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_0.setOnClickListener(this);
        bt_plus.setOnClickListener(this);
        bt_minus.setOnClickListener(this);
        bt_multiply.setOnClickListener(this);
        bt_divide.setOnClickListener(this);
        bt_ce.setOnClickListener(this);
        bt_c.setOnClickListener(this);
        bt_del.setOnClickListener(this);
        bt_point.setOnClickListener(this);
        bt_negate.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_sin.setOnClickListener(this);
        bt_cos.setOnClickListener(this);
        bt_pow.setOnClickListener(this);

        rb_dec.setOnClickListener(this);
        rb_oct.setOnClickListener(this);
        rb_bin.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_unit_converter:
                String str = tv_input.getText().toString();
                Intent intent = new Intent(MainActivity.this, UnitConverter.class);
                intent.putExtra("exdata", str);
                startActivity(intent);
                break;
            case R.id.menu_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.dialog_help, null);

                builder.setView(v);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.show();
                break;
            case R.id.menu_quit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy:
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tv_input.getText().toString());
                Toast.makeText(this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    int flag = 0;           //是否已做过计算的标识，计算完毕置1，重新开始计算时置0
    int checked = 1;        //当前单选所选选项标识，1为10进制，2为8进制，3为2进制

    @Override
    public void onClick(View v) {

        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_output = (TextView) findViewById(R.id.tv_output);
        String input = tv_input.getText().toString();
        String output = tv_output.getText().toString();
        double x = 0;
        int tip = 0;

        switch (v.getId()) {
            case R.id.bt_1:
            case R.id.bt_2:
            case R.id.bt_3:
            case R.id.bt_4:
            case R.id.bt_5:
            case R.id.bt_6:
            case R.id.bt_7:
            case R.id.bt_8:
            case R.id.bt_9:
            case R.id.bt_0:           //在最后输入数字，若输入框内为0或刚进行完一次计算，则清空输入框再输入数字
                String num = ((Button) v).getText().toString();
                if ("0".equals(input) || flag == 1) {
                    tv_input.setText(num);
                    flag = 0;
                } else
                    tv_input.setText(input + num);
                break;
            case R.id.bt_plus:
            case R.id.bt_minus:
            case R.id.bt_multiply:
            case R.id.bt_divide:
            case R.id.bt_pow:                   //选择运算符
                if ("error".equals(input))      //判断是否为error
                    break;
                input = predigest(input);       //将输入框内的字符串多余的后缀去掉

                if ("".equals(output)) {
                    tv_output.setText(input + " " + ((Button) v).getText().toString());
                    tv_input.setText("0");
                } else {
                    String result = getResult();
                    if ("error".equals(result))      //判断是否为error
                        break;
                    tv_output.setText(result + " " + ((Button) v).getText().toString());
                    tv_input.setText("0");
                }
                break;
            case R.id.bt_point:         //小数点，先遍历一遍输入框，避免重复
                tip = 0;
                if (flag == 1) {
                    tv_input.setText("0.");
                    flag = 0;
                } else {
                    for (int i = 0; i < input.length(); i++) {
                        if (input.charAt(i) == '.') {
                            tip = 1;
                            break;
                        }
                    }
                    if (tip == 0)
                        tv_input.setText(input + ".");
                }
                break;
            case R.id.bt_ce:            //清空输入框
                tv_input.setText("0");
                break;
            case R.id.bt_c:             //清空输入框与输出框
                tv_input.setText("0");
                tv_output.setText("");
                break;
            case R.id.bt_del:           //回退键
                int length = input.length();
                if (flag == 1 || length == 1) {
                    tv_input.setText("0");
                } else {
                    tv_input.setText(input.substring(0, length - 1));
                }
                break;
            case R.id.bt_negate:        //切换输入框正负号
                if ("0".equals(input))
                    break;
                if (!(input.charAt(0) == '-'))
                    input = "-" + input;
                else
                    input = input.substring(1, input.length());
                tv_input.setText(input);
                break;
            case R.id.bt_equal:         //等号，引用getresult()方法计算，结果在输入框输出，输出框清空
                if (!"".equals(output)) {
                    String result = getResult();
                    tv_input.setText(result);
                    tv_output.setText("");
                    flag = 1;
                }
                break;
            case R.id.bt_sin:
                x = Double.parseDouble(input);
                x = Math.sin(x);
                tv_input.setText(predigest(x + ""));
                break;
            case R.id.bt_cos:
                x = Double.parseDouble(input);
                x = Math.cos(x);
                tv_input.setText(predigest(x + ""));
                break;
            case R.id.rb_decimal: {

                if (checked == 1)
                    break;
                if (checked == 2) {
                    tv_input.setText(Integer.valueOf(input, 8).toString());
                }
                if (checked == 3) {
                    tv_input.setText(Integer.valueOf(input, 2).toString());
                }
                bt_2.setEnabled(true);
                bt_3.setEnabled(true);
                bt_4.setEnabled(true);
                bt_5.setEnabled(true);
                bt_6.setEnabled(true);
                bt_7.setEnabled(true);
                bt_8.setEnabled(true);
                bt_9.setEnabled(true);
                bt_plus.setEnabled(true);
                bt_minus.setEnabled(true);
                bt_multiply.setEnabled(true);
                bt_divide.setEnabled(true);
                bt_point.setEnabled(true);
                bt_equal.setEnabled(true);
                bt_sin.setEnabled(true);
                bt_cos.setEnabled(true);
                bt_pow.setEnabled(true);

                checked = 1;
            }
            break;
            case R.id.rb_octal: {
                int y = (int)Double.parseDouble(input);
                Toast.makeText(MainActivity.this, "此功能仅转换整数部分", Toast.LENGTH_SHORT).show();
                if (checked == 2)
                    break;
                if (checked == 1) {
                    if (y < 0) {                //对于负号的判断，似乎有负号转换函数会出错
                        y = -y;
                        tv_input.setText("-" + Integer.toOctalString(y));
                    }
                    else
                        tv_input.setText(Integer.toOctalString(y));
                }
                if (checked == 3) {
                    String s = Integer.valueOf(input, 2).toString();
                    if (s.charAt(0) == '-') {
                        s = s.substring(1, s.length());
                        tv_input.setText("-" + Integer.toOctalString(Integer.parseInt(s)));
                    }
                    else
                        tv_input.setText(Integer.toOctalString(Integer.parseInt(s)));
                }

                bt_2.setEnabled(true);
                bt_3.setEnabled(true);
                bt_4.setEnabled(true);
                bt_5.setEnabled(true);
                bt_6.setEnabled(true);
                bt_7.setEnabled(true);
                bt_8.setEnabled(false);
                bt_9.setEnabled(false);
                bt_plus.setEnabled(false);
                bt_minus.setEnabled(false);
                bt_multiply.setEnabled(false);
                bt_divide.setEnabled(false);
                bt_point.setEnabled(false);
                bt_equal.setEnabled(false);
                bt_sin.setEnabled(false);
                bt_cos.setEnabled(false);
                bt_pow.setEnabled(false);

                checked = 2;
            }
            break;
            case R.id.rb_binary: {
                int y = (int)Double.parseDouble(input);
                Toast.makeText(MainActivity.this, "此功能仅转换整数部分", Toast.LENGTH_SHORT).show();
                if (checked == 3)
                    break;
                if (checked == 1) {
                    if (y < 0) {
                        y = -y;
                        tv_input.setText("-" + Integer.toBinaryString(y));
                    }
                    else
                        tv_input.setText(Integer.toBinaryString(y));
                }
                if (checked == 2) {
                    String s = Integer.valueOf(input, 8).toString();
                    if (s.charAt(0) == '-') {
                        s = s.substring(1, s.length());
                        tv_input.setText("-" + Integer.toBinaryString(Integer.parseInt(s)));
                    }
                    else
                        tv_input.setText(Integer.toBinaryString(Integer.parseInt(s)));
                }

                bt_2.setEnabled(false);
                bt_3.setEnabled(false);
                bt_4.setEnabled(false);
                bt_5.setEnabled(false);
                bt_6.setEnabled(false);
                bt_7.setEnabled(false);
                bt_8.setEnabled(false);
                bt_9.setEnabled(false);
                bt_plus.setEnabled(false);
                bt_minus.setEnabled(false);
                bt_multiply.setEnabled(false);
                bt_divide.setEnabled(false);
                bt_point.setEnabled(false);
                bt_equal.setEnabled(false);
                bt_sin.setEnabled(false);
                bt_cos.setEnabled(false);
                bt_pow.setEnabled(false);

                checked = 3;
            }
            break;
            default:

        }
    }

    //计算结果，获取两个框内的字符串，分解为两个数和一个运算符，再进行运算
    public String getResult() {
        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_output = (TextView) findViewById(R.id.tv_output);
        String input = tv_input.getText().toString();
        String output = tv_output.getText().toString();

        double firstNumber = Double.parseDouble(output.substring(0, output.length() - 2));
        double secondNumber = Double.parseDouble(input);
        double result = 0;
        int isnull = 0;
        String resultString = "";

        switch (output.charAt(output.length() - 1)) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '×':
                result = firstNumber * secondNumber;
                break;
            case '÷':
                if (secondNumber != 0)
                    result = firstNumber / secondNumber;
                else
                    isnull = 1;
                break;
            case '^':
                result = Math.pow(firstNumber, secondNumber);
                break;
            default:
        }
        if (isnull == 1)
            return "error";

        resultString = result + "";
        resultString = predigest(resultString);

        if ("-0".equals(resultString))
            resultString = "0";

        return resultString;
    }

    //简化字符串方法
    public String predigest(String s) {
        int tip = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                tip = 1;
                break;
            }
        }
        if (tip == 1) {
            while (s.charAt(s.length() - 1) == '0') {
                s = s.substring(0, s.length() - 1);
            }
            if (s.charAt(s.length() - 1) == '.')
                s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
}