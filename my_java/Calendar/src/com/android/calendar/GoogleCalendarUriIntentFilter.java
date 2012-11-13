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
**
** Copyright 2009, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** See the License for the specific language governing permissions and
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** limitations under the License.
*/

package com.android.calendar;

import static android.provider.Calendar.EVENT_BEGIN_TIME;
import static android.provider.Calendar.EVENT_END_TIME;
import static android.provider.Calendar.AttendeesColumns.ATTENDEE_STATUS;
import static android.provider.Calendar.AttendeesColumns.ATTENDEE_STATUS_ACCEPTED;
import static android.provider.Calendar.AttendeesColumns.ATTENDEE_STATUS_DECLINED;
import static android.provider.Calendar.AttendeesColumns.ATTENDEE_STATUS_NONE;
import static android.provider.Calendar.AttendeesColumns.ATTENDEE_STATUS_TENTATIVE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Calendar.Events;

public class GoogleCalendarUriIntentFilter extends Activity {
    private static final int EVENT_INDEX_ID = 0;
    private static final int EVENT_INDEX_START = 1;
    private static final int EVENT_INDEX_END = 2;

    private static final String[] EVENT_PROJECTION = new String[] {
        Events._ID,      // 0
        Events.DTSTART,  // 1
        Events.DTEND,    // 2
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                String eid = uri.getQueryParameter("eid");
                if (eid != null) {
                    String selection = Events.HTML_URI + " LIKE \"%eid=" + eid + "%\"";

                    Cursor eventCursor = managedQuery(Events.CONTENT_URI, EVENT_PROJECTION,
                            selection, null, null);

                    // TODO what to do when there's more than one match
                    if (eventCursor != null && eventCursor.getCount() > 0) {
                        // Get info from Cursor
                        eventCursor.moveToFirst();
                        int eventId = eventCursor.getInt(EVENT_INDEX_ID);
                        long startMillis = eventCursor.getLong(EVENT_INDEX_START);
                        long endMillis = eventCursor.getLong(EVENT_INDEX_END);

                        // Pick up attendee status action from uri clicked
                        int attendeeStatus = ATTENDEE_STATUS_NONE;
                        if ("RESPOND".equals(uri.getQueryParameter("action"))) {
                            try {
                                switch (Integer.parseInt(uri.getQueryParameter("rst"))) {
                                case 1: // Yes
                                    attendeeStatus = ATTENDEE_STATUS_ACCEPTED;
                                    break;
                                case 2: // No
                                    attendeeStatus = ATTENDEE_STATUS_DECLINED;
                                    break;
                                case 3: // Maybe
                                    attendeeStatus = ATTENDEE_STATUS_TENTATIVE;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                // ignore this error as if the response code
                                // wasn't in the uri.
                            }
                        }

                        // Send intent to calendar app
                        Uri calendarUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
                        intent = new Intent(Intent.ACTION_VIEW, calendarUri);
                        intent.putExtra(EVENT_BEGIN_TIME, startMillis);
                        intent.putExtra(EVENT_END_TIME, endMillis);
                        if (attendeeStatus != ATTENDEE_STATUS_NONE) {
                            intent.putExtra(ATTENDEE_STATUS, attendeeStatus);
                        }
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }

            // Can't handle the intent. Pass it on to the next Activity.
            try {
                startNextMatchingActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // no browser installed? Just drop it.
            }
        }
        finish();
    }
}
