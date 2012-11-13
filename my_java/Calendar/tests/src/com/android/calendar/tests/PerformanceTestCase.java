package com.android.calendar.tests;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.ComponentName;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;

import com.android.calendar.*;
import com.mediatek.android.performance.BasePerformanceTestCase;
/**
 * This class do the launch performance test for Calendar application module.
 * Include the performance test base on the low memory condition
 */
public class PerformanceTestCase extends BasePerformanceTestCase {
    private static final String TAG = "CalPerformanceTestcase";
    /**
     * Test Calendar launch time. Default Calendar view is MonthActivity
     * @throws InterruptedException 
     */
    public void testMonthViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testMonthViewLoadingTime");
        setDefaultView("com.android.calendar.MonthActivity");
        
        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".MonthActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (;  resultCount< totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());
    }

    /**
     * Test press back key after launch MonthActivity.
     */
    public void testMonthViewPressBackKey() {
    	mBindHelper.setDescription("testMonthViewPressBackKey");
        setDefaultView("com.android.calendar.MonthActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);
      
        dumpResult(new Intent(getInstrumentation().getTargetContext(), MonthActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch MonthActivity
     * @throws InterruptedException 
     */
    public void testMonthViewPressHomeKey() throws InterruptedException {
    	mBindHelper.setDescription("testMonthViewPressHomeKey");
        String activityName = "com.android.calendar.MonthActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), MonthActivity.class), "_homekey", result);
    }

    /**
     * Test DayView Activity loading time.
     * @throws InterruptedException 
     */
    public void testDayViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testDayViewLoadingTime");
        setDefaultView("com.android.calendar.DayActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".DayActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());

    }

    /**
     * Test press back key after launch DayActivity.
     */
    public void testDayViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testDayViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.DayActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), DayActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch DayActivity.
     * @throws InterruptedException 
     */
    public void testDayViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testDayViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.DayActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), DayActivity.class), "_homekey", result);

    }


    /**
     * Test week view Activity loading time.
     * @throws InterruptedException
     */
    public void testWeekViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testWeekViewLoadingTime");
        setDefaultView("com.android.calendar.WeekActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".WeekActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());
    }

    /**
     * Test press back key after launch WeekActivity.
     */
    public void testWeekViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testWeekViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.WeekActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), WeekActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch WeekActivity.
     * @throws InterruptedException 
     */
    public void testWeekViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testWeekViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.WeekActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), WeekActivity.class), "_homekey", result);

    }
   
     
    /**
     * This test tests for performance of how much time used when saving an calendar event.
     * @throws InterruptedException
     */
    public void testEditEvent() throws InterruptedException {
    	mBindHelper.setDescription("testEditEvent");
        int testCount = mBindHelper.getTotalTestCount();
        ArrayList<Integer> result = new ArrayList<Integer>();

        Uri uri = Uri.parse("content://com.android.calendar");
        getInstrumentation().getTargetContext().getContentResolver().registerContentObserver(uri, true, mObserver);
        Intent intent = new Intent();
        intent.setClass(getInstrumentation().getTargetContext(), EditEvent.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            while (testCount-- > 0) {
                gc();
                getInstrumentation().startActivitySync(intent);
                // sleep 1 second to let EditEvent activity full launched.
                Thread.sleep(1000);
                getInstrumentation().sendStringSync("this is a test event");
                getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                getInstrumentation().waitForIdleSync();
   
                //getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                //getInstrumentation().waitForIdleSync();
                for (int i = 0; i < 100; ++i) {
                    getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
                    getInstrumentation().waitForIdleSync();
                }
                getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_LEFT);
                getInstrumentation().waitForIdleSync();

                mContentTime = 0;

                long timeBeforeSaved = System.currentTimeMillis();
                getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_CENTER);
                getInstrumentation().waitForIdleSync();
                synchronized (mObserver) {
                    if (mContentTime == 0) {
                        mObserver.wait(5000);
                    }
                }
                assertTrue(mContentTime != 0);
                result.add((int) (mContentTime - timeBeforeSaved));
                sleep(500);
            }
        }catch(InterruptedException e){
        	Log.e(TAG, "testEditEvent exception");
        }
        finally {
            getInstrumentation().getTargetContext().getContentResolver().unregisterContentObserver(mObserver);
        }

        dumpResult(intent, "_save_event_time", result);
    }
    
    /**
     * Test AgendaView loading time.
     * @throws InterruptedException 
     */
    public void testAgendaViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testAgendaViewLoadingTime");
        setDefaultView("com.android.calendar.AgendaActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".AgendaActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());

    }

    /**
     * Test press back key after launch AgendaActivity.
     */
    public void testAgendaViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testAgendaViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.AgendaActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), AgendaActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch AgendaActivity.
     * @throws InterruptedException 
     */
    public void testAgendaViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testAgendaViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.AgendaActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), AgendaActivity.class), "_homekey", result);

    }
    
    /**
     * Test AlertActivity loading time.
     * @throws InterruptedException 
     */
    public void testAlertActivityViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testAlertActivityViewLoadingTime");
        setDefaultView("com.android.calendar.AlertActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".AlertActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());

    }

    /**
     * Test press back key after launch AlertActivity.
     */
    public void testAlertActivityViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testAlertActivityViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.AlertActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), AlertActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch AlertActivity.
     * @throws InterruptedException 
     */
    public void testAlertActivityViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testAlertActivityViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.AlertActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), AlertActivity.class), "_homekey", result);

    }
    
    /**
     * Test CalendarPreferenceActivity loading time.
     * @throws InterruptedException 
     */
    public void testCalendarPreferenceActivityViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testCalendarPreferenceActivityViewLoadingTime");
        setDefaultView("com.android.calendar.CalendarPreferenceActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".CalendarPreferenceActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());

    }

    /**
     * Test press back key after launch CalendarPreferenceActivity.
     */
    public void testCalendarPreferenceActivityViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testCalendarPreferenceActivityViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.CalendarPreferenceActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), CalendarPreferenceActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch CalendarPreferenceActivity.
     * @throws InterruptedException 
     */
    public void testCalendarPreferenceActivityViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testCalendarPreferenceActivityViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.CalendarPreferenceActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), CalendarPreferenceActivity.class), "_homekey", result);

    }
    
     /**
     * Test SelectCalendarsActivity loading time.
     * @throws InterruptedException 
     */
    public void testSelectCalendarsActivityViewLoadingTime() throws InterruptedException {
    	mBindHelper.setDescription("testSelectCalendarsActivityViewLoadingTime");
        setDefaultView("com.android.calendar.SelectCalendarsActivity");

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mBindHelper.reset();
        mBindHelper.setListenedActivity(getInstrumentation().getTargetContext().getPackageName(), ".SelectCalendarsActivity");
        mBindHelper.start();

        int totalTestCount = mBindHelper.getTotalTestCount();
        int resultCount = mBindHelper.getResultCount();
        for (; resultCount < totalTestCount;resultCount++) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);
            // press back key to finish MonthActivity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();
            sleep(500);
        }

        mBindHelper.stop();
        mBindHelper.dumpResult();
        assertTrue(mBindHelper.isExpectedResult());

    }

    /**
     * Test press back key after launch SelectCalendarsActivity.
     */
    public void testSelectCalendarsActivityViewLoadingTimeBackkey() {
    	mBindHelper.setDescription("testSelectCalendarsActivityViewLoadingTimeBackkey");
        setDefaultView("com.android.calendar.SelectCalendarsActivity");

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressBackkey(result);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), SelectCalendarsActivity.class), "_backkey", result);
    }

    /**
     * Test press home key after launch SelectCalendarsActivity.
     * @throws InterruptedException 
     */
    public void testSelectCalendarsActivityViewLoadingTimeHomekey() throws InterruptedException {
    	mBindHelper.setDescription("testSelectCalendarsActivityViewLoadingTimeHomekey");
        String activityName = "com.android.calendar.SelectCalendarsActivity";
        setDefaultView(activityName);

        ArrayList<Integer> result = new ArrayList<Integer>();

        triggerPressHomekey(result, activityName);

        dumpResult(new Intent(getInstrumentation().getTargetContext(), SelectCalendarsActivity.class), "_homekey", result);

    }
    
    //Test Activity Launch performance under Low Memory condition, using launchTest();
    /**
     * Test Agenda Activity performance.
     * @throws RemoteException 
     */
    public void testAgendaViewLowMemory() throws RemoteException {
    	try {
        	Log.i(TAG, "testAgendaViewLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testAgendaViewLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), AgendaActivity.class);
            Log.i(TAG, "testAgendaViewLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testAgendaViewLowMemory--->>launchTest end.");
            mBindHelper.releaseLowMemory();
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test Alert Activity performance.
     */
    public void testAlertViewLowMemory() throws RemoteException {
        try {
        	Log.i(TAG, "testAlertViewLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testAlertViewLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), AlertActivity.class);
            Log.i(TAG, "testAlertViewLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testAlertViewLowMemory--->>launchTest end.");
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test CalendarPreference Activity performance.
     */
    public void testCalendarPreferenceLowMemory() throws RemoteException {
        try {
        	Log.i(TAG, "testCalendarPreferenceLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testCalendarPreferenceLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), CalendarPreferenceActivity.class);
            Log.i(TAG, "testCalendarPreferenceLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testCalendarPreferenceLowMemory--->>launchTest end.");
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test Week Activity performance.
     */
    public void testWeekViewLowMemory() throws RemoteException {
        try {
        	Log.i(TAG, "testWeekViewLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testWeekViewLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), WeekActivity.class);
            Log.i(TAG, "testWeekViewLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testWeekViewLowMemory--->>launchTest end.");
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test Day Activity performance.
     */
    public void testDayViewLowMemory()  throws RemoteException {
        try {
        	Log.i(TAG, "testDayViewLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testDayViewLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), DayActivity.class);
            Log.i(TAG, "testDayViewLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testDayViewLowMemory--->>launchTest end.");
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test Month Activity performance.
     */
    public void testMonthViewLowMemory() throws RemoteException {
        try {
            Log.i(TAG, "testMonthViewLowMemory--->>start");
            mBindHelper.makeLowMemory();
            mBindHelper.setDescription("testMonthViewLowMemory");
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getInstrumentation().getTargetContext(), MonthActivity.class);
            Log.i(TAG, "testMonthViewLowMemory--->>launchTest start.");
            launchTest(intent);
            Log.i(TAG, "testMonthViewLowMemory--->>launchTest end.");
        } finally {
            mBindHelper.releaseLowMemory();
        }
    }
    /**
     * Test SelectCalendars Activity performance.
     *  @throws InterruptedException 
     */
    public void testSelectCalendarsLowMemory() throws InterruptedException {
        try {
            Log.i(TAG, "testSelectCalendarsLowMemory--->>start");
            mBindHelper.setDescription("testSelectCalendarsActivityViewLoadingTime");
            setDefaultView("com.android.calendar.SelectCalendarsActivity");

            Intent intent = new Intent(getInstrumentation().getTargetContext(),LaunchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mBindHelper.reset();
            mBindHelper.setListenedActivity(getInstrumentation()
                    .getTargetContext().getPackageName(),
                    ".SelectCalendarsActivity");
            mBindHelper.start();

            int totalTestCount = mBindHelper.getTotalTestCount();
            int resultCount = mBindHelper.getResultCount();
            for (; resultCount < totalTestCount;resultCount++) {
                gc();
                getInstrumentation().startActivitySync(intent);
                getInstrumentation().waitForIdleSync();
                // because LaunchActivity will start target and finish itself.
                // So here just sleep
                // 400 ms to let target activity full Launched.
                sleep(400);
                // press back key to finish MonthActivity
                getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                getInstrumentation().waitForIdleSync();
                sleep(500);
                }
            mBindHelper.stop();
            mBindHelper.dumpResult();
            assertTrue(mBindHelper.isExpectedResult());
            Log.i(TAG, "testSelectCalendarsLowMemory--->>end");
        }finally {
            mBindHelper.releaseLowMemory();
        }
    }
    
///////////////////////////private function/////////////////////////////////////////////////////////////////////////
    private long mContentTime;

    private ContentObserver mObserver = new ContentObserver(null) {
        public void onChange(boolean selfChange) {
            synchronized (this) {
                mContentTime = System.currentTimeMillis();
            }
        }
    };
    
    /**
     * Start target activity then press back key. Collect result in ArrayList.
     * @param result
     */
    private void triggerPressBackkey(ArrayList<Integer> result) {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int totalTestCount = mBindHelper.getTotalTestCount();
        System.out.println("[AppLaunch] Back key pressed,totalTestCount="+totalTestCount);
        while (totalTestCount-- > 0) {
            gc();
            getInstrumentation().startActivitySync(intent);
            getInstrumentation().waitForIdleSync();
            // because LaunchActivity will start target and finish itself. So here just sleep
            // 400 ms to let target activity full Launched.
            sleep(400);

            mBindHelper.reset();
            mBindHelper.startRecordLog();

            // print this string to let performance helper know that a back key is pressed. 
            System.out.println("[AppLaunch] Back key pressed");

            // press back key to finish target activity
            getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

            // sleep 1 second to let previous activity full show.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            	Log.e(TAG, "triggerPressBackkey exception");
            }
            getInstrumentation().waitForIdleSync();
            mBindHelper.stopRecordLog();
            result.add(mBindHelper.analysisBackKeyLog());

            sleep(500);
        }
    }

    /**
     * Start target activity then press home key. Collect result in ArrayList.
     * @param result
     * @throws InterruptedException 
     */
    private void triggerPressHomekey(ArrayList<Integer> result, String activityName) throws InterruptedException {
        Intent homeIntent = new Intent();
        homeIntent.setAction(Intent.ACTION_MAIN);
        homeIntent.addCategory("android.intent.category.HOME");
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int totalTestCount = mBindHelper.getTotalTestCount();
        System.out.println("[AppLaunch] home key pressed,totalTestCount="+totalTestCount);
        while (totalTestCount-- > 0) {
            gc();
            ActivityMonitor am = new ActivityMonitor(activityName, null, false);
            getInstrumentation().addMonitor(am);
            Activity activity = null;
            try {
                getInstrumentation().startActivitySync(intent);
                getInstrumentation().waitForIdleSync();
                // because LaunchActivity will start target and finish itself. So here just sleep
                // 400 ms to let target activity full Launched.
                sleep(400);
                activity = am.waitForActivity();

                mBindHelper.reset();
                mBindHelper.startRecordLog();
                getInstrumentation().getTargetContext().startActivity(homeIntent);

                getInstrumentation().waitForIdleSync();
                // sleep 1 second to let Home full launched.
                Thread.sleep(1000);
                mBindHelper.stopRecordLog();
                result.add(mBindHelper.analysisHomeKeyLog());
            }catch(InterruptedException e){
            	Log.e(TAG, "triggerPressHomekey exception");
            }
            finally {
                if (activity != null) {
                    activity.finish();
                }
                getInstrumentation().removeMonitor(am);
            }
            sleep(500);
        }
    }

    private void dumpResult(Intent intent, String postfix, ArrayList<Integer> result) {
        ComponentName cn = intent.getComponent();
        String activityName = cn.getPackageName() + "/" + cn.getShortClassName() + postfix;
        mBindHelper.dumpResultStr(activityName + "\t" + result.get(0));
    }
    
    /**
     * set default view to month view.
     */
    private void setDefaultView(String activity) {
        SharedPreferences prefs = CalendarPreferenceActivity.getSharedPreferences(getInstrumentation().getTargetContext());
        Editor editor = prefs.edit();
        editor.putString("startView", activity).commit();
    }
    
    private void gc() {
        System.gc();
        sleep(500);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e1) {
        	Log.e(TAG, "sleep exception");
        }
    }

}
