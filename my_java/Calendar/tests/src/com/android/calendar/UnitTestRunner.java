package com.android.calendar;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;

public class UnitTestRunner extends InstrumentationTestRunner {

    @Override
    public TestSuite getAllTests() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(FormatDateRangeTest.class);
        suite.addTestSuite(UtilsTests.class);
        suite.addTestSuite(WeekNumberTest.class);        
        return suite;
    }
}
