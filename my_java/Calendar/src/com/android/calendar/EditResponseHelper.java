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
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

/**
 * A helper class for editing the response to an invitation when the invitation
 * is a repeating event.
 */
public class EditResponseHelper implements DialogInterface.OnClickListener {
    private final Activity mParent;
    private int mWhichEvents = -1;
    private AlertDialog mAlertDialog;

    /**
     * This callback is passed in to this object when this object is created
     * and is invoked when the "Ok" button is selected.
     */
    private DialogInterface.OnClickListener mDialogListener;
    
    public EditResponseHelper(Activity parent) {
        mParent = parent;
    }
    
    public void setOnClickListener(DialogInterface.OnClickListener listener) {
        mDialogListener = listener;
    }
    
    public int getWhichEvents() {
        return mWhichEvents;
    }
    
    public void onClick(DialogInterface dialog, int which) {
    }

    /**
     * This callback is used when a list item is selected
     */
    private DialogInterface.OnClickListener mListListener =
            new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            mWhichEvents = which;
            
            // Enable the "ok" button now that the user has selected which
            // events in the series to delete.
            Button ok = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setEnabled(true);
        }
    };
    
    public void showDialog(int whichEvents) {
        // We need to have a non-null listener, otherwise we get null when
        // we try to fetch the "Ok" button.
        if (mDialogListener == null) {
            mDialogListener = this;
        }
        AlertDialog dialog = new AlertDialog.Builder(mParent)
                .setTitle(R.string.change_response_title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setSingleChoiceItems(R.array.change_response_labels, whichEvents,
                        mListListener)
                .setPositiveButton(android.R.string.ok, mDialogListener)
                .setNegativeButton(android.R.string.cancel, null)
                .show();
        mAlertDialog = dialog;
        
        if (whichEvents == -1) {
            // Disable the "Ok" button until the user selects which events to
            // delete.
            Button ok = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            ok.setEnabled(false);
        }
    }
}
