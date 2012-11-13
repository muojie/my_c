package com.android.calendar.tests;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import com.mediatek.android.performance.util.ServiceBindHelper;

public class PerformanceTestRunner extends InstrumentationTestRunner {

    @Override
    public TestSuite getAllTests() {
    	ServiceBindHelper.setModuleName("CalendarPerf");
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PerformanceTestCase.class);
        return suite;
    }
}
