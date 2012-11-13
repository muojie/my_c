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
 * Copyright (C) 2007 The Android Open Source Project
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.provider.Calendar.CalendarCache;
import android.text.TextUtils;

public class CalendarPreferenceActivity extends PreferenceActivity implements
        OnSharedPreferenceChangeListener, OnPreferenceChangeListener {
    private static final String BUILD_VERSION = "build_version";

    // The name of the shared preferences file. This name must be maintained for historical
    // reasons, as it's what PreferenceManager assigned the first time the file was created.
    private static final String SHARED_PREFS_NAME = "com.android.calendar_preferences";

    // Preference keys
    static final String KEY_HIDE_DECLINED = "preferences_hide_declined";
    static final String KEY_ALERTS_TYPE = "preferences_alerts_type";
    static final String KEY_ALERTS_VIBRATE = "preferences_alerts_vibrate";
    static final String KEY_ALERTS_VIBRATE_WHEN = "preferences_alerts_vibrateWhen";
    static final String KEY_ALERTS_RINGTONE = "preferences_alerts_ringtone";
    static final String KEY_DEFAULT_REMINDER = "preferences_default_reminder";
    static final String KEY_START_VIEW = "startView";
    static final String KEY_DETAILED_VIEW = "preferredDetailedView";
    static final String KEY_DEFAULT_CALENDAR = "preference_defaultCalendar";
    static final String KEY_HOME_TZ_ENABLED = "preferences_home_tz_enabled";
    static final String KEY_HOME_TZ = "preferences_home_tz";

    // These must be in sync with the array preferences_alert_type_values
    static final String ALERT_TYPE_ALERTS = "0";
    static final String ALERT_TYPE_STATUS_BAR = "1";
    static final String ALERT_TYPE_OFF = "2";

    // Default preference values
    static final String DEFAULT_START_VIEW =
            CalendarApplication.ACTIVITY_NAMES[CalendarApplication.MONTH_VIEW_ID];
    static final String DEFAULT_DETAILED_VIEW =
            CalendarApplication.ACTIVITY_NAMES[CalendarApplication.DAY_VIEW_ID];


    ListPreference mAlertType;
    ListPreference mVibrateWhen;
    RingtonePreference mRingtone;
    CheckBoxPreference mUseHomeTZ;
    ListPreference mHomeTZ;

    private static CharSequence[][] mTimezones;

    // In case we need to update something later
    private Runnable mTZUpdater = null;

    /** Return a properly configured SharedPreferences instance */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    /** Set the default shared preferences in the proper context */
    public static void setDefaultValues(Context context) {
        PreferenceManager.setDefaultValues(context, SHARED_PREFS_NAME, Context.MODE_PRIVATE,
                R.xml.preferences, false);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Make sure to always use the same preferences file regardless of the package name
        // we're running under
        PreferenceManager preferenceManager = getPreferenceManager();
        SharedPreferences sharedPreferences = getSharedPreferences(this);
        preferenceManager.setSharedPreferencesName(SHARED_PREFS_NAME);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        mAlertType = (ListPreference) preferenceScreen.findPreference(KEY_ALERTS_TYPE);
        mVibrateWhen = (ListPreference) preferenceScreen.findPreference(KEY_ALERTS_VIBRATE_WHEN);
        mRingtone = (RingtonePreference) preferenceScreen.findPreference(KEY_ALERTS_RINGTONE);
        mUseHomeTZ = (CheckBoxPreference) preferenceScreen.findPreference(KEY_HOME_TZ_ENABLED);
        mUseHomeTZ.setOnPreferenceChangeListener(this);
        mHomeTZ = (ListPreference) preferenceScreen.findPreference(KEY_HOME_TZ);
        String tz = mHomeTZ.getValue();

        if (mTimezones == null) {
            mTimezones = (new TimezoneAdapter(this, tz)).getAllTimezones();
        }
        mHomeTZ.setEntryValues(mTimezones[0]);
        mHomeTZ.setEntries(mTimezones[1]);
        CharSequence tzName = mHomeTZ.getEntry();
        if (!TextUtils.isEmpty(tzName)) {
            mHomeTZ.setSummary(tzName);
        } else {
            mHomeTZ.setSummary(Utils.getTimeZone(this, mTZUpdater));
        }
        mHomeTZ.setOnPreferenceChangeListener(this);

        // If needed, migrate vibration setting from a previous version
        if (!sharedPreferences.contains(KEY_ALERTS_VIBRATE_WHEN) &&
                sharedPreferences.contains(KEY_ALERTS_VIBRATE)) {
            int stringId = sharedPreferences.getBoolean(KEY_ALERTS_VIBRATE, false) ?
                    R.string.prefDefault_alerts_vibrate_true :
                    R.string.prefDefault_alerts_vibrate_false;
            mVibrateWhen.setValue(getString(stringId));
        }

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            findPreference(BUILD_VERSION).setSummary(packageInfo.versionName);
        } catch (NameNotFoundException e) {
            findPreference(BUILD_VERSION).setSummary("?");
        }
        updateChildPreferences();
    }

    @Override
    protected void onResume(){
    	super.onResume();
        if(!mUseHomeTZ.isChecked()){
        	//(2011/07/14)if use_home_timezone is not checked, update timezone's summary(2011/07/14)
        	//mHomeTZ's summary will be set to current Time zone
        	//mHomeTZ will keep consistent with system's time zone
        	String timezone = Utils.getTimeZone(this, mTZUpdater);
        	mHomeTZ.setValue(timezone);
        	mHomeTZ.setSummary(timezone);
        }
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	//If this activity is destroyed,we should reload the timezones(static)' data next time.
    	if( mTimezones != null ) {
    		mTimezones = null;
    	}
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_ALERTS_TYPE)) {
            updateChildPreferences();
        }
    }

    private void updateChildPreferences() {
        if (mAlertType.getValue().equals(ALERT_TYPE_OFF)) {
            mVibrateWhen.setValue(getString(R.string.prefDefault_alerts_vibrate_false));
            mVibrateWhen.setEnabled(false);
            mRingtone.setEnabled(false);
        } else {
            mVibrateWhen.setEnabled(true);
            mRingtone.setEnabled(true);
        }
    }

    /**
     * Handles time zone preference changes
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String tz;
        if (preference == mUseHomeTZ) {
            if ((Boolean)newValue) {
                tz = mHomeTZ.getValue();
            } else {
                tz = CalendarCache.TIMEZONE_TYPE_AUTO;
            }
        } else if (preference == mHomeTZ) {
            tz = (String)newValue;
            mHomeTZ.setValue(tz);
            mHomeTZ.setSummary(mHomeTZ.getEntry());
        } else {
            return false;
        }
        Utils.setTimeZone(this, tz);
        return true;
    }
}
