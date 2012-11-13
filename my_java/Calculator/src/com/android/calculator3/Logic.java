/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.calculator3;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;


class Logic {
    private CalculatorDisplay mDisplay;
    private History mHistory;
    private String  mResult = "";
    private boolean mIsError = false;
    private int mLineLength = 0;

    private static final String INFINITY_UNICODE = "\u221e";

    // the two strings below are the result of Double.toString() for Infinity & NaN
    // they are not output to the user and don't require internationalization
    private static final String INFINITY = "Infinity"; 
    private static final String NAN      = "NaN";

    static final char MINUS = '\u2212';

    private final String mErrorString;

    Logic(Context context, History history, CalculatorDisplay display, Button equalButton) {
        mErrorString = context.getResources().getString(R.string.error);
        mHistory = history;
        mDisplay = display;
        mDisplay.setLogic(this);

        clearWithHistory(false);
    }

    void setLineLength(int nDigits) {
        mLineLength = nDigits;
    }

    boolean eatHorizontalMove(boolean toLeft) {
        EditText editText = mDisplay.getEditText();
        int cursorPos = editText.getSelectionStart();
        return toLeft ? cursorPos == 0 : cursorPos >= editText.length(); 
    }

    private String getText() {
        return mDisplay.getText().toString();
    }

    void insert(String delta) {
        mDisplay.insert(delta);
    }

    private void setText(CharSequence text) {
        mDisplay.setText(text, CalculatorDisplay.Scroll.UP);
    }

    private void clearWithHistory(boolean scroll) {
        mDisplay.setText(mHistory.getText(), 
                         scroll ? CalculatorDisplay.Scroll.UP : CalculatorDisplay.Scroll.NONE);
        mResult = "";
        mIsError = false;
    }

    private void clear(boolean scroll) {
        mDisplay.setText("", scroll ? CalculatorDisplay.Scroll.UP : CalculatorDisplay.Scroll.NONE);
        cleared();
    }

    void cleared() {
        mResult = "";
        mIsError = false;
        updateHistory();
    }

    boolean acceptInsert(String delta) {
        String text = getText();
        return !mIsError &&
            (!mResult.equals(text) || 
             isOperator(delta) ||
             mDisplay.getSelectionStart() != text.length());
    }

    void onDelete() {
        if (getText().equals(mResult) || mIsError) {
            clear(false);
        } else {
            mDisplay.dispatchKeyEvent(new KeyEvent(0, KeyEvent.KEYCODE_DEL));
            mResult = "";
        }
    }

    void onClear() {
        clear(false);
    }

    void onEnter() {
        String text = getText();
        if (text.equals(mResult)) {
            clearWithHistory(false); //clear after an Enter on result
        } else {
            mHistory.enter(text);

            if (text.equals(mResult)) {
                //no need to show result, it is exactly what the user entered
                clearWithHistory(true);
            } else {
                setText(mResult);
                //mEqualButton.setText(mEnterString);
            }
        }
    }

    void onUp() {
        String text = getText();
        if (!text.equals(mResult)) {
            mHistory.update(text);
        }
        if (mHistory.moveToPrevious()) {
            mDisplay.setText(mHistory.getText(), CalculatorDisplay.Scroll.DOWN);
        }
    }

    void onDown() {
        String text = getText();
        if (!text.equals(mResult)) {
            mHistory.update(text);
        }
        if (mHistory.moveToNext()) {
            mDisplay.setText(mHistory.getText(), CalculatorDisplay.Scroll.UP);
        }
    }

    void updateHistory() {
        mHistory.update(getText());
    }

    private static final int ROUND_DIGITS = 3;

    static boolean isOperator(String text) {
        return text.length() == 1 && isOperator(text.charAt(0));
    }

    static boolean isOperator(char c) {
        //plus minus times div
        return "+\u2212\u00d7\u00f7/*".indexOf(c) != -1;
    }
    
    boolean isErrorString(String targetStr) {
    	return mErrorString.equalsIgnoreCase(targetStr);
    }
}
