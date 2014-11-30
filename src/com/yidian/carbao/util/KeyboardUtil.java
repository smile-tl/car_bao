package com.yidian.carbao.util;

import java.util.List;

import com.yidian.carbao.R;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class KeyboardUtil {
	private KeyboardView keyboardView;
	private Keyboard k1;
	public boolean isupper = false;// �Ƿ��д
	public static boolean isShow = false;
	private TextView tv;

	public KeyboardUtil(Activity act, Context ctx, TextView tv) {
//		this.ed = edit;
		this.tv = tv;
		k1 = new Keyboard(ctx, R.xml.custom);
		keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
		keyboardView.setKeyboard(k1);
		keyboardView.setEnabled(true);

		keyboardView.setOnKeyboardActionListener(listener);
	}

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		private String[] keyValue = { "��", "��", "��", "��", "��", "��", "��", "��",
				"��", "��", "��", "��", "��", "��", "³", "ԥ", "��", "��", "��", "��",
				"��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "̨" ,"���"};

		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			if(primaryCode!=32){
				tv.setText(keyValue[primaryCode]);
			}else{
				hideKeyboard();
			}
			
			

		}
	};

	/**
	 * ���̴�Сд�л�
	 */
	private void changeKey() {
		List<Key> keylist = k1.getKeys();
		if (isupper) {// ��д�л�Сд
			isupper = false;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0] + 32;
				} else if (key.label.toString().equals("Сд")) {
					key.label = "��д";
				}

			}
		} else {// Сд�л���д
			isupper = true;
			for (Key key : keylist) {
				if (key.label != null && isword(key.label.toString())) {
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0] - 32;
				} else if (key.label.toString().equals("��д")) {
					key.label = "Сд";
				}
			}
		}
	}

	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
			isShow = true;
		}
	}

	public void hideKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			keyboardView.setVisibility(View.INVISIBLE);
			isShow = false;
		}
	}

	private boolean isword(String str) {
		String wordstr = "abcdefghijklmnopqrstuvwxyz";
		if (wordstr.indexOf(str.toLowerCase()) > -1) {
			return true;
		}
		return false;
	}
}
