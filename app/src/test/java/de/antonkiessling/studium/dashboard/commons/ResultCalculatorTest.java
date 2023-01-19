package de.antonkiessling.studium.dashboard.commons;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import de.antonkiessling.studium.db.Module;

public class ResultCalculatorTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCalculateAverage() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module("Test 1", 1.0, 5, 1));
        modules.add(new Module("Test 0,0", 0.0, 5, 1));
        modules.add(new Module("Test 0", 0, 5, 1));
        modules.add(new Module("Test 2.0", 2.0, 5, 1));

        ResultCalculator resultCalculator = new ResultCalculator();

        assertEquals("1.5", resultCalculator.calculateAverage(modules));
    }
}