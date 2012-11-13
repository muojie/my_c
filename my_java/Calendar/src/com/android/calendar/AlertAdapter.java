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

package com.android.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class AlertAdapter extends ResourceCursorAdapter {

    public AlertAdapter(Context context, int resource) {
        super(context, resource, null);
    }
    
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView;

        View stripe = view.findViewById(R.id.vertical_stripe);
        int color = cursor.getInt(AlertActivity.INDEX_COLOR);
        stripe.setBackgroundColor(color);
        textView = (TextView) view.findViewById(R.id.event_title);
        textView.setTextColor(color);

        // Repeating info
        ImageView repeatContainer = (ImageView)view.findViewById(R.id.repeat_icon);
        String rrule = cursor.getString(AlertActivity.INDEX_RRULE);
        if (!TextUtils.isEmpty(rrule)) {
            repeatContainer.setVisibility(View.VISIBLE);
        } else {
        	String originalEvent = cursor.getString(AlertActivity.INDEX_ORIGINAL_EVENT);
        	if(!TextUtils.isEmpty(originalEvent)) {
        		repeatContainer.setVisibility(View.VISIBLE);
        		repeatContainer.setImageResource(R.drawable.ic_repeat_dark_exception);
        	} else {
        		 repeatContainer.setVisibility(View.GONE);
        	}        
        }
        
        /*
        // Reminder
        boolean hasAlarm = cursor.getInt(AlertActivity.INDEX_HAS_ALARM) != 0;
        if (hasAlarm) {
            AgendaAdapter.updateReminder(view, context, cursor.getLong(AlertActivity.INDEX_BEGIN),
                    cursor.getLong(AlertActivity.INDEX_EVENT_ID));
        }
        */
        
        String eventName = cursor.getString(AlertActivity.INDEX_TITLE);
        String location = cursor.getString(AlertActivity.INDEX_EVENT_LOCATION);
        long startMillis = cursor.getLong(AlertActivity.INDEX_BEGIN);
        long endMillis = cursor.getLong(AlertActivity.INDEX_END);
        boolean allDay = cursor.getInt(AlertActivity.INDEX_ALL_DAY) != 0;
        
        updateView(context, view, eventName, location, startMillis, endMillis, allDay);
    }
    
    public static void updateView(Context context, View view, String eventName, String location,
            long startMillis, long endMillis, boolean allDay) {
        
        Resources res = context.getResources();
        TextView textView;
        
        // What
        if (eventName == null || eventName.length() == 0) {
            eventName = res.getString(R.string.no_title_label);
        }
        textView = (TextView) view.findViewById(R.id.event_title);
        textView.setText(eventName);
        
        // When
        String when;
        int flags;
        if (allDay) {
            flags = DateUtils.FORMAT_UTC | DateUtils.FORMAT_SHOW_WEEKDAY |
                    DateUtils.FORMAT_SHOW_DATE;
        } else {
            flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE;
        }
        if (DateFormat.is24HourFormat(context)) {
            flags |= DateUtils.FORMAT_24HOUR;
        }
        when = Utils.formatDateRange(context, startMillis, endMillis, flags);
        textView = (TextView) view.findViewById(R.id.when);
        textView.setText(when);
        
        // Where
        textView = (TextView) view.findViewById(R.id.where);
        if (location == null || location.length() == 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(location);
        }
    }
}
