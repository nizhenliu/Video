package com.example.tool.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/9/15 16:33
 * @description
 * @updateUser hahajing
 * @updateDate 2022/9/15 16:33
 * @updateRemark
 */
public class KeyboardUtils {

    public static void showKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) view.getContext()

                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            view.requestFocus();

            imm.showSoftInput(view, 0);

        }

    }

    public static void hideKeyboard(View view){

        InputMethodManager imm = (InputMethodManager) view.getContext()

                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        }

    }

    public static void toggleSoftInput(View view){

        InputMethodManager imm = (InputMethodManager) view.getContext()

                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.toggleSoftInput(0,0);

        }

    }

}
