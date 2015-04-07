package com.chengl.game_2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private int num = 0;
	private TextView label;

	public Card(Context context) {
		super(context);
		
		label = new TextView(getContext());
		label.setTextSize(32);
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		
		LayoutParams layoutParams = new LayoutParams(-1, -1);
		layoutParams.setMargins(10, 10, 0, 0);
		addView(label, layoutParams);
		
		setNum(0);
	}
	
	public boolean equals(Card o) {
		
		return getNum() == o.getNum();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		
		if (num <= 0) {
			label.setText("");
		} else {
			label.setText(num + "");
		}
		
	}

}
