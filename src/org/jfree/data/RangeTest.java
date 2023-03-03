package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.Range;
import org.jfree.data.Values2D;
import org.jmock.*;
import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    private Range secondRange;
    private Range thirdRange;
    private Range sameValuesRange;
    private Range smallest;
    private Range largest;
    private Range myTester;
    
    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }
    

    @Before
    public void setUp() throws Exception { 
    	exampleRange = new Range(-1, 1);
    	secondRange = new Range (5, 30);
    	thirdRange = new Range(-30, -5);
    	sameValuesRange = new Range(0,0);
    	smallest = new Range(-(Double.MAX_VALUE), -(Double.MAX_VALUE) + 1);
    	largest = new Range(Double.MAX_VALUE - 1, Double.MAX_VALUE);
    	
    }
    
    /**
     * Testing Range Constructor
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithLowerGreaterThanUpper() {
    	myTester = new Range(5,-1);
    	assertNull("Constructor should have thrown exception, and object should be NULL.", myTester);
    }
    
    
    /**
     * Testing intersects(double, double)
     */
    @Test
    public void testIntersectsWithNumberLowerThanLowerBound() {
    	myTester = new Range(5,10);
    	boolean actual = myTester.intersects(3,12);
    	assertTrue("The given range intersects with Range object.",actual);
    }
    
    @Test
    public void testIntersectsWithNumberGreaterThanLowerBound() {
    	myTester = new Range(5,10);
    	boolean actual = myTester.intersects(6,12);
    	assertTrue("The given range intersects with Range object.",actual);
    }
    
    @Test
    public void testIntersectsWithRangeLessThanObject() {
    	myTester = new Range(5, 10);
    	boolean actual = myTester.intersects(1,3);
    	assertFalse("The given range does not intersect with Range object.", actual);
    }
    
    @Test
    public void testIntersectsWithRangeGreaterThanObject() {
    	myTester = new Range(5, 10);
    	boolean actual = myTester.intersects(11,30);
    	assertFalse("The given range does not intersect with Range object.", actual);
    }
    
    /**
     * Testing intersects(Range)
     */    
    @Test
    public void testIntersectsWithRangeInRange() {
    	myTester = new Range(5, 10);
    	boolean actual = secondRange.intersects(myTester);
    	assertTrue("The given Range object does intersect with this Range object.",actual);
    }
    
    @Test
    public void testIntersectsWithRangeNotInRange() {
    	myTester = new Range(-20,0);
    	boolean actual = secondRange.intersects(myTester);
    	assertFalse("The given Range object does not intersect with this Range object.", actual);
    }
    
    /**
     * Testing constrain(double)
     */
    @Test
    public void testConstrainWithValueSmallerThanLower() {
    	myTester = new Range(5, 10);
    	double actual = myTester.constrain(0);
    	assertEquals("Incorrect constrain returned.", 5, actual, .000000001d);
    }
    
    @Test
    public void testConstrainWithValueGreaterThanUpper() {
    	myTester = new Range(5, 10);
    	double actual = myTester.constrain(15);
    	assertEquals("Incorrect constrain returned.", 10, actual, .000000001d);
    }
    
    @Test
    public void testConstrainWithValueInRange() {
    	myTester = new Range(5, 10);
    	double actual = myTester.constrain(6);
    	assertEquals("Incorrect constrain returned.", 6, actual, .000000001d);
    }
    
    /**
     * Testing combine(Range, Range)
     */
    @Test
    public void testCombining2NullRange() {
    	Range a = null;
    	Range b = null;
    	Range actual = Range.combine(a, b);
    	assertNull("Returned object should be null.", actual);
    	
    }
    
    @Test
    public void testCombining1NullAnd1Range() {
    	myTester = new Range(5,10);
    	Range actual = Range.combine(null, myTester);
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombining1RangeAnd1Null() {
    	Range actual = Range.combine(new Range(5,10), null);
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombining2Ranges() {
    	Range actual = Range.combine(new Range(5,10), new Range(-2,7)); 
    	assertEquals("Incorrect lower bound for object.", -2, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    
    /**
     * Testing combineIgnoringNaN(Range, Range)
     */
    @Test
    public void testCombineNaNWith2Null() {
    	Range actual = Range.combineIgnoringNaN(null, null);
    	assertNull("Returned object should be null.", actual);
    }
    
    @Test
    public void testCombiningNaNWith1Null1NaNRange() {
    	Range actual = Range.combineIgnoringNaN(null,  new Range(Double.NaN, Double.NaN));
    	assertNull("Returned object should be null.", actual);
    }
    
    @Test
    public void testCombiningNaNWith1Null1Range() {
    	Range actual = Range.combineIgnoringNaN(null, new Range(5, 10));
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testCombiningNaNWith1NaN1Null() {
    	Range actual = Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null);
    	assertNull("Returned object should be null.", actual);
    }
    
    @Test
    public void testCombiningNaNWith2NaN() {
    	Range actual = Range.combineIgnoringNaN(new Range(Double.NaN,Double.NaN), new Range(Double.NaN,Double.NaN));
    	assertNull("Returned object should be null.", actual);
    }
    
    @Test
    public void testCombiningNaNWith1Range1NaN() {
    	Range actual = Range.combineIgnoringNaN(new Range(5,10), new Range(Double.NaN,Double.NaN));
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    
    /**
     * Testing expandToInclude(Range, double)
     */
    @Test
    public void testExpandToIncludeWithNullRange() {
    	Range actual = Range.expandToInclude(null, 0);
    	assertEquals("Incorrect lower bound for object.", 0, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 0, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testExpandToIncludeWithValueGreaterThanUpper() {
    	Range actual = Range.expandToInclude(exampleRange, 10);
    	assertEquals("Incorrect lower bound for object.", -1, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testExpandToIncludeWithValueInRange() {
    	Range actual = Range.expandToInclude(secondRange, 10);
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 30, actual.getUpperBound(), .000000001d);
    }
    
    /**
     * Testing expand(Range, double, double)
     */
    @Test
    public void testExpand0LowerAnDNegativeUpper() {
    	Range actual = Range.expand(new Range(5,10), 0, -5);
    	assertEquals("Incorrect lower bound for object.", -5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", -5, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testExpandNormalRange() {
    	Range actual = Range.expand(new Range(5,10), 0, 0);
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 10, actual.getUpperBound(), .000000001d);
    }
    
    
    /**
     * Testing shift(Range, double, boolean)
     */
    @Test
    public void testShiftTrueBoolean() {
    	Range actual = Range.shift(secondRange, 5, true);
    	assertEquals("Incorrect lower bound for object.", 10, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 35, actual.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testShiftFalseBoolean() {
    	Range actual = Range.shift(new Range(0,30), 5, false);
    	assertEquals("Incorrect lower bound for object.", 5, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 35, actual.getUpperBound(), .000000001d);
    }
    
    /**
     * Testing shift(Range, double)
     */
    @Test
    public void testShift() {
    	Range actual = Range.shift(secondRange, 5);
    	assertEquals("Incorrect lower bound for object.", 10, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 35, actual.getUpperBound(), .000000001d);
    }
    
    /**
     * Testing scale(Range, double)
     */
    @Test
    public void testingScaleWithFactorLessThanZero() {
    	try {
    		Range actual = Range.scale(new Range(0,0), -1);
    		fail("Should have thrown an error.");
    	} catch (IllegalArgumentException e) {
    		assertTrue(true);
    	}
    }
    
    @Test
    public void testingScaleWithPositiveFactor() {
    	Range actual = Range.scale(new Range(5,10), 2);
    	assertEquals("Incorrect lower bound for object.", 10, actual.getLowerBound(), .000000001d);
    	assertEquals("Incorrect upper bound for object.", 20, actual.getUpperBound(), .000000001d);
    }
    
    //Testing Contains
    
    //Testing values that are in the range
    @Test
    public void testContainHasValue() {
    	boolean result = exampleRange.contains(0);
    	boolean isTrue = true;
    	
    	result = exampleRange.contains(0);
    	
    	assertTrue("0 is in exampleRange (-1,1)", result == isTrue);
    	
    	result = secondRange.contains(0);
    	
    	assertTrue("5 is in thirdRange (5,30)", result == isTrue);
    	
    	result = thirdRange.contains(-5);
    	
    	assertTrue("-5 is in thirdRange (-30,-5)", result == isTrue);
    	
    	result = sameValuesRange.contains(0);
    	
    	assertTrue("0 is in sameValuesRange (-1,1)", result == isTrue);
    }
    
    //test values that are not in the range
    @Test
    public void testContainsDoesntHaveValue() {
    	boolean result = exampleRange.contains(-100);
    	boolean isTrue = false;
    	
    	result = exampleRange.contains(100);
    	
    	assertTrue("-100 is not in exampleRange (-1,1)", result == isTrue);
    	
    	result = secondRange.contains(-100);
    	
    	assertTrue("-100 is not in thirdRange (5,30)", result == isTrue);
    	
    	result = thirdRange.contains(100);
    	
    	assertTrue("100 is not in thirdRange (-30,-5)", result == isTrue);
    	
    	result = sameValuesRange.contains(100);
    	
    	assertTrue("100 is not in sameValuesRange (-1,1)", result == isTrue);
    }
    
    
    
    
    //Testing Length()
    @Test
    public void testContainsNull() {
    	try {
    		Range empty = null;
    		boolean length = empty.contains(0);
    		
    		assertTrue("Did not throw error correctly", false);
    	} catch(Exception e) {
    		assertTrue("Threw error correctly", true);
    	} 
    }
    @Test
    public void testGetLengthnegativelowerpostiviveupper() {
        double length = this.exampleRange.getLength();
        double expectedRange = 2.0;
        assertTrue("Incorrect Range for exampleRange (-1,1)", expectedRange == length);
    }
    
    @Test
    public void testGetLengthbothpositive() {
    	double length = this.secondRange.getLength();
    	double expectedRange = 25.0;
    	assertTrue("Incorrect Range for exampleRange (5,30)", expectedRange == length);
    }
    
    @Test
    public void testGetLengthbothnegative() {
    	double length = this.thirdRange.getLength();
    	double expectedRange = 25.0;
    	assertTrue("Incorrect Range for exampleRange (5,30)", expectedRange == length);
    }
    
    @Test
    public void testGetLengthequalvalue() {
    	double length = this.sameValuesRange.getLength();
    	double expectedRange = 0.0;
    	assertTrue("Incorrect Range for exampleRange (5,30)", expectedRange == length);
    }


    @Test
    public void centralValueShouldBeZero() {
        assertEquals("The central value of -1 and 1 should be 0",
        0, exampleRange.getCentralValue(), .000000001d);
    }
    
    
    //Testing getLowerBound()
    @Test
    public void getNegativeLowerBound() {
    	assertEquals("Incorrect Lower Bound was returned.", -1, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getPositiveLowerBound() {
    	assertEquals("Incorrect Lower Bound was returned.", 5, secondRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getZeroLowerBound() {
    	assertEquals("Incorrect Lower Bound was returned.", 0, sameValuesRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getOneLessThanMaxDoubleLowerBound() {
    	assertEquals("Incorrect Lower Bound was returned.", (Double.MAX_VALUE) -1, largest.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getMinimumDoubleLowerBound() {
    	assertEquals("Incorrect Lower Bound was returned.", -(Double.MAX_VALUE), largest.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getNullLowerBound() {
    	Range nullRange = null;
    	
    	try {
    		Double tester = nullRange.getLowerBound();
    		fail("getLowerBound() should have thrown an error for a Range object that is null.");
    	} catch(Exception e) {
    		assertTrue(true);
    	}
    }
    
    // Testing getUpperBound()
    @Test
    public void getNegativeUpperBound() {
    	assertEquals("Incorrect Upper Bound was returned.", -5, thirdRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getPositiveUpperBound() {
    	assertEquals("Incorrect Upper Bound was returned.", 30, secondRange.getUpperBound(), .000000001d);
    } 
    
    @Test
    public void getZeroUpperBound() {
    	assertEquals("Incorrect Upper Bound was returned.", 0, sameValuesRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getMaximumDoubleUpperBound() {
    	assertEquals("Incorrect Upper Bound was returned.", Double.MAX_VALUE, largest.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getOneGreaterThanMinDoubleUpperBound() {
    	assertEquals("Incorrect Upper Bound was returned.", -Double.MAX_VALUE + 1, smallest.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getNullUpperBound() {
    	Range nullRange = null;
    	
    	try {
    		Double tester = nullRange.getUpperBound();
    		fail("getUpperBound() should have thrown an error for a Range object that is null.");
    	} catch(Exception e) {
    		assertTrue(true);
    	}
    }
    
    // Testing toString()
    @Test
    public void correctToStringWithPositiveUpperAndLower() {
    	assertEquals("Incorrect String Returned.", "Range[5.0,30.0]", secondRange.toString());
    }
    
    @Test
    public void correctToStringWithNegativeUpperAndLower() {
    	assertEquals("Incorrect String Returned.", "Range[-30.0,-5.0]", thirdRange.toString());
    }
    
    @Test
    public void correctToStringWithNegativeLowerAndPositiveUpper() {
    	assertEquals("Incorrect String Returned.", "Range[-1.0,1.0]", exampleRange.toString());
    }
    
    @Test
    public void correctToStringWithEquivilentLowerAndUpper() {
    	assertEquals("Incorrect String Returned.", "Range[0.0,0.0]", sameValuesRange.toString());
    }
    
    @Test
    public void correctToStringWithNullRange() {
    	Range nullRange = null;
    	try {
    		String tester = nullRange.toString();
    		fail("toString() should have thrown an error for a null Range object.");
    	} catch (Exception e) {
    		assertTrue(true);
    	}
    }
    
    @Test
    public void correctToStringWithVeryLargeBound() {
    	String expected = "Range[";
    	expected+=(Double.MAX_VALUE - 1)+",";
    	expected+=Double.MAX_VALUE+"]";
    	assertEquals("Incorrect String Returned.", expected, largest.toString());
    }
    
    

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("All test completed (hopefully)");
    }
    
    
}
