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

import android.widget.BaseAdapter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Vector;

class History {
    private static final int VERSION_1 = 1;
    private static final int MAX_ENTRIES = 100;
    Vector<HistoryEntry> mEntries = new Vector<HistoryEntry>();
    int mPos;
    BaseAdapter mObserver;

    History() {
        clear();
    }

    History(int version, DataInput in) throws IOException {
        if (version >= VERSION_1) {
            int size = in.readInt();
            for (int i = 0; i < size; ++i) {
                mEntries.add(new HistoryEntry(version, in));
            }
            mPos = in.readInt();
        } else {
            throw new IOException("invalid version " + version);
        }
    }

    void setObserver(BaseAdapter observer) {
        mObserver = observer;
    }

    private void notifyChanged() {
        if (mObserver != null) {
            mObserver.notifyDataSetChanged();
        }
    }

    void clear() {
        mEntries.clear();
        mEntries.add(new HistoryEntry(""));
        mPos = 0;
        notifyChanged();
    }

    void write(DataOutput out) throws IOException {
        out.writeInt(mEntries.size());
        for (HistoryEntry entry : mEntries) {
            entry.write(out);
        }
        out.writeInt(mPos);
    }

    void update(String text) {
        current().setEdited(text);
    }

    boolean moveToPrevious() {
        if (mPos > 0) {
            --mPos;
            return true;
        }
        return false;
    }

    boolean moveToNext() {
        if (mPos < mEntries.size() - 1) {
            ++mPos;
            return true;
        }
        return false;
    }

    void enter(String text) {
        if (mEntries.size() >= MAX_ENTRIES) {
            mEntries.remove(0);
        }
        if (mEntries.size() < 2 ||
            !text.equals(mEntries.elementAt(mEntries.size() - 2).getBase())) {
            mEntries.insertElementAt(new HistoryEntry(text), mEntries.size() - 1);
        }
        mPos = mEntries.size() - 1;
        current().clearEdited();
        notifyChanged();
    }

    HistoryEntry current() {
        return mEntries.elementAt(mPos);
    }

    String getText() {
        return current().getEdited();
    }

    String getBase() {
        return current().getBase();
    }
}
