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

import android.text.SpannableStringBuilder;
import android.text.Editable;
import android.util.Log;

class CalculatorEditable extends SpannableStringBuilder {
    private static final char[] ORIGINALS    = {'-',      '*',      '/'};
    private static final char[] REPLACEMENTS = {'\u2212', '\u00d7', '\u00f7'};
    private static final int MAX_INPUT_LENGTH = 300;
    private boolean isInsideReplace = false;
    private Logic mLogic;

    private CalculatorEditable(CharSequence source, Logic logic) {
        super(source);
        mLogic = logic;
    }

    @Override
    public SpannableStringBuilder 
    replace(int start, int end, CharSequence tb, int tbstart, int tbend) {
        if (isInsideReplace) {
            // If delta is too long and would cause the total length
            // over MAX_INPUT_LENGTH, Calculator will stop it, and 
            // accept only part of delta.
            int origLength = length();
            int deltaLength = tb.length();
            if (origLength + deltaLength > MAX_INPUT_LENGTH) {
                int remainLength = MAX_INPUT_LENGTH - origLength;
                return super.replace(start, end, tb, tbstart, tbstart + remainLength);
            }
            return super.replace(start, end, tb, tbstart, tbend);
        } else {        
            isInsideReplace = true;
            try {
                String delta = tb.subSequence(tbstart, tbend).toString();            
                return internalReplace(start, end, delta);
            } finally {
                isInsideReplace = false;
            }
        }
    }
    
    private SpannableStringBuilder internalReplace(int start, int end, String delta) {
        if (!mLogic.acceptInsert(delta)) {            
            mLogic.cleared();
            start = 0;
            end = length();
        }

        for (int i = ORIGINALS.length - 1; i >= 0; --i) {
            delta = delta.replace(ORIGINALS[i], REPLACEMENTS[i]);
        }

        int length = delta.length();
        if (length == 1) {
            char text = delta.charAt(0);

            //don't allow two dots in the same number
            if (text == '.') {
                int p = start - 1;
                while (p >= 0 && Character.isDigit(charAt(p))) {
                    --p;
                }
                if (p >= 0 && charAt(p) == '.') {
                    return super.replace(start, end, "");
                }
            }

            char prevChar = start > 0 ? charAt(start-1) : '\0';

            //don't allow 2 successive minuses
            if (text == Logic.MINUS && prevChar == Logic.MINUS) {
                return super.replace(start, end, "");
            }

            //don't allow multiple successive operators
            if (Logic.isOperator(text)) {
                while (Logic.isOperator(prevChar) && 
                       (text != Logic.MINUS || prevChar == '+')) {
                    --start;
                    prevChar = start > 0 ? charAt(start-1) : '\0';
                }
            }

            //don't allow leading operator + * /
            if (start == 0 && Logic.isOperator(text) && text != Logic.MINUS) {
                return super.replace(start, end, "");
            }
        } 
        return super.replace(start, end, delta);
    }

    public static class Factory extends Editable.Factory {
        private Logic mLogic;

        public Factory(Logic logic) {
            mLogic = logic;
        }

        public Editable newEditable(CharSequence source) {
            return new CalculatorEditable(source, mLogic);
        }
    }
}
