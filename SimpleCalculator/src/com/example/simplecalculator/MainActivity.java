package com.example.simplecalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private TextView text; // textBox
	private TextView text_sign;
	private ImageButton button0, button1, button2, button3, button4, button5,
			button6, button7, button8, button9;
	private ImageButton buttonDiv, buttonMult, buttonSub, buttonAdd, buttonDot,
			buttonEq, buttonClear, buttonDel;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deneme);

		text = (TextView) findViewById(R.id.TextView1);
		text_sign = (TextView) findViewById(R.id.textView2);
		button0 = (ImageButton) findViewById(R.id.button0);
		button0.getBackground().setAlpha(128);
		button1 = (ImageButton) findViewById(R.id.button1);
		button1.getBackground().setAlpha(128);
		button2 = (ImageButton) findViewById(R.id.button2);
		button2.getBackground().setAlpha(128);
		button3 = (ImageButton) findViewById(R.id.button3);
		button3.getBackground().setAlpha(128);
		button4 = (ImageButton) findViewById(R.id.button4);
		button4.getBackground().setAlpha(128);
		button5 = (ImageButton) findViewById(R.id.button5);
		button5.getBackground().setAlpha(128);
		button6 = (ImageButton) findViewById(R.id.button6);
		button6.getBackground().setAlpha(128);
		button7 = (ImageButton) findViewById(R.id.button7);
		button7.getBackground().setAlpha(128);
		button8 = (ImageButton) findViewById(R.id.button8);
		button8.getBackground().setAlpha(128);
		button9 = (ImageButton) findViewById(R.id.button9);
		button9.getBackground().setAlpha(128);
		buttonDiv = (ImageButton) findViewById(R.id.buttonDiv);
		buttonDiv.getBackground().setAlpha(128);
		buttonMult = (ImageButton) findViewById(R.id.buttonMult);
		buttonMult.getBackground().setAlpha(128);
		buttonSub = (ImageButton) findViewById(R.id.buttonSub);
		buttonSub.getBackground().setAlpha(128);
		buttonAdd = (ImageButton) findViewById(R.id.buttonAdd);
		buttonAdd.getBackground().setAlpha(128);
		buttonDot = (ImageButton) findViewById(R.id.buttonDot);
		buttonDot.getBackground().setAlpha(128);
		buttonEq = (ImageButton) findViewById(R.id.buttonEq);
		buttonEq.getBackground().setAlpha(128);
		buttonClear = (ImageButton) findViewById(R.id.buttonClear);
		buttonClear.getBackground().setAlpha(128);
		buttonDel = (ImageButton) findViewById(R.id.buttonDel);
		buttonDel.getBackground().setAlpha(128);
	
		button0.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		buttonDiv.setOnClickListener(this);
		buttonMult.setOnClickListener(this);
		buttonSub.setOnClickListener(this);
		buttonAdd.setOnClickListener(this);
		buttonDot.setOnClickListener(this);
		buttonEq.setOnClickListener(this);
		buttonClear.setOnClickListener(this);
		buttonDel.setOnClickListener(this);

	}

	int clear_flag = 0;
	String sign_flag = "";
	Double total = 0.0;
	int last_button = 0;

	public void shownum(String num) {
		if (clear_flag == 1) {
			text.setText("");
			clear_flag = 0;
		} else if (text.getText() == "0") {
			text.setText("");
		}
		text.setText(text.getText() + num);
	}

	public void showsign(String sign) {
		if (last_button == R.id.buttonAdd || last_button == R.id.buttonSub
				|| last_button == R.id.buttonMult
				|| last_button == R.id.buttonDiv) {

		} else {
			clear_flag = 1;
			Double newNumber = Double.parseDouble(text.getText().toString());
			if (sign_flag == "" || sign_flag == "=") {
				total = newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "+") {
				total += newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "-") {
				total -= newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "*") {
				total *= newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "/") {
				if (newNumber != 0) {
					total /= newNumber;
					text.setText(total.toString());
				} else {
					text.setText("NaN");
				}
			}
		}
		sign_flag = sign;
		text_sign.setText(sign_flag);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.button0) {
			shownum("0");
		} else if (v.getId() == R.id.button1) {
			shownum("1");
		} else if (v.getId() == R.id.button2) {
			shownum("2");
		} else if (v.getId() == R.id.button3) {
			shownum("3");
		} else if (v.getId() == R.id.button4) {
			shownum("4");
		} else if (v.getId() == R.id.button5) {
			shownum("5");
		} else if (v.getId() == R.id.button6) {
			shownum("6");
		} else if (v.getId() == R.id.button7) {
			shownum("7");
		} else if (v.getId() == R.id.button8) {
			shownum("8");
		} else if (v.getId() == R.id.button9) {
			shownum("9");
		} else if (v.getId() == R.id.buttonClear) {
			text.setText("0");
			total = 0.0;
			sign_flag = "";
			text_sign.setText(sign_flag);
		} else if (v.getId() == R.id.buttonDot) {
			if (clear_flag == 1) {
				text.setText("");
				clear_flag = 0;
			}
			if (text.getText().toString().indexOf(".") < 0) {
				text.setText(text.getText() + ".");
			}
		} else if (v.getId() == R.id.buttonDel) {
			if (text.getText().toString().length() > 0) {
				int start = 0;
				int end = text.getText().toString().length() - 1;
				String newText = text.getText().toString()
						.substring(start, end);
				text.setText(newText);
			}
			
		} else if (v.getId() == R.id.buttonAdd) {
			showsign("+");
		} else if (v.getId() == R.id.buttonSub) {
			showsign("-");
		} else if (v.getId() == R.id.buttonMult) {
			showsign("*");
		} else if (v.getId() == R.id.buttonDiv) {
			showsign("/");
		} else if (v.getId() == R.id.buttonEq) {
			Double newNumber = Double.parseDouble(text.getText().toString());
			if (sign_flag == "+") {
				total += newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "-") {
				total -= newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "*") {
				total *= newNumber;
				text.setText(total.toString());
			} else if (sign_flag == "/") {
				if (newNumber != 0) {
					total /= newNumber;
					text.setText(total.toString());
				} else {
					text.setText("NaN");
				}
			} else if (sign_flag == "%") {
				total *= newNumber / 100;
				text.setText(total.toString());
			}
			sign_flag = "=";
		}
		last_button = v.getId();
	}

}
