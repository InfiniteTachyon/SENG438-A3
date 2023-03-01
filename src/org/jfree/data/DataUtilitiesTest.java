package org.jfree.data;

import static org.junit.Assert.*;
import java.security.InvalidParameterException;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.Values2D;
import org.jmock.*;
import org.junit.*;

/*
 * This test will test DataUtilites class 
 * function includes:
 * calculateColumTotal, calculateTowTotal, creatNumberArray
 */

public class DataUtilitiesTest {
    //declare mock variable 
  	private Mockery mockingContext;
  	Values2D values;
  	
	private Mockery testArray;
	private KeyedValues testValues;
    
    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }
    

    @Before
    public void setUp() throws Exception { 
    	//Mock setup
    	mockingContext = new Mockery();
    	values = mockingContext.mock(Values2D.class);
    }
    
    
   
    
    /*
     * Begin test for calculateRowTotal-----------------------
     */
    
    //valid test, test row sum total for table of two column 
    //Mock table of two column and return 4.5 and 5.5 
    //for position of [0,1] and [1,0]
    //function should return (double) 10 as sum
	@Test
	public void calculateRowTotalTwoPosValids() {
		mockingContext.checking(new Expectations() {
			{
				
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(0,0);
				will(returnValue(4.5));
				one(values).getValue(0, 1);
				will(returnValue(5.5));
			}
		});
		double calcResult = DataUtilities.calculateRowTotal(values, 0);
		//System.out.println(calcResult);
		assertEquals(calcResult, 10.0, .000000001d);
	}
	
	//valid test, test row sum total for table of two column 
    //Mock table of two column and return -4.5 and -5.5 
    //for position of [0,1] and [1,0]
    //function should return (double) 10 as sum
	@Test
	public void calcRowTotalNegativeValids() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				
				one(values).getValue(1,0);
				will(returnValue(-3.14));
				one(values).getValue(1, 1);
				will(returnValue(-2));
				
			}
		});
		double result = DataUtilities.calculateRowTotal(values, 1);
		System.out.println(result);
		assertEquals(result, -5.14, .000000001d);
	}

	
	//test for calculating out of bound raw index sums
	//mock for table of 2 column but only one raw
	//call function to calculate the 2nd row of the table
	//out of bound exception should be throw
	@Test(expected = IndexOutOfBoundsException.class)
	public void calculateRowTotalOutOfBounds() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(0,0);
				will(returnValue(4.5));
				one(values).getValue(0, 1);
				will(returnValue(5.5));
				
				one(values).getValue(1, 0);
				will(throwException(new IndexOutOfBoundsException("Index out of bounds")));
				
			}
		});
		DataUtilities.calculateRowTotal(values, 1);
	}
	
	
	
	//test for calculating invalid raw index sums
	//mock for table of 2 column 
	//call function to calculate the -1 row of the table
	//out of bound exception should be throw
	@Test(expected = IndexOutOfBoundsException.class)
	public void calculateRowTotalNegativeRows() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				one(values).getValue(0,0);
				will(returnValue(4.5));
				one(values).getValue(0, 1);
				will(returnValue(5.5));
				
				one(values).getValue(-1, 0);
				will(throwException(new IndexOutOfBoundsException("Index out of bounds")));
				
			}
		});
		DataUtilities.calculateRowTotal(values, -1);
	}
	
	
	//Test on boundary cases
	//crate table with 3 rows,
	//call function to calculate 3rd row sum
	@Test
	public void calculateRowTotalOnBoundaryValid() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(2));
				
				
				one(values).getValue(2,0);
				will(returnValue(3.14));
				one(values).getValue(2,1);
				will(returnValue(2));
				
			}
		});
		double result = DataUtilities.calculateRowTotal(values, 2);
		//System.out.println(result);
		assertEquals(result, 5.14, .000000001d);
	}
	
	
	
	//check how function handle invalid input
	//according to specification:
	//With invalid input, a total of zero will be returned.
	@Test
	public void calculateRowTotalInvalidDataInput() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getColumnCount();
				will(returnValue(0));
				one(values).getValue(0, 0);
				will(throwException(new NullPointerException("Null entries")));
				
			}
		});
		double result = DataUtilities.calculateRowTotal(values, 0);
		assertEquals(result, 0.0000, .000000001d);
	}
	
	
	//pass in null as values2D
	//which should cause a exception
	@Test(expected = Exception.class)
	public void calcRowNullTest() {
		DataUtilities.calculateRowTotal(null, 0);
	}
    
    
	/*
     * End of test for calculateRowTotal----------------------
     */
	
	
	
	/*
     * Begin test for calculateColumnTotal---------------------
     */
	
	
    //valid test, test Column sum total for table of two row 
    //Mock table of two row and return 4.5 and 5.5 
    //for position of [0,1] and [1,0]
    //function should return (double) 10 as sum
	@Test
	public void calculateColumnTotalTwoPosValids() {
		mockingContext.checking(new Expectations() {
			{
				
				one(values).getRowCount();
				will(returnValue(2));
				
				one(values).getValue(0,0);
				will(returnValue(4.5));
				one(values).getValue(1,0);
				will(returnValue(5.5));
			}
		});
		double calcResult = DataUtilities.calculateColumnTotal(values, 0);
		//System.out.println(calcResult);
		assertEquals(calcResult, 10.0, .000000001d);
	}
	
	//valid test, test row sum total for table of two column 
    //Mock table of two column and return -4.5 and -5.5 
    //for position of [0,1] and [1,0]
    //function should return (double) 10 as sum
	@Test
	public void calculateColumnTotalTwoNegativeValids() {
		mockingContext.checking(new Expectations() {
			{
				
				one(values).getRowCount();
				will(returnValue(2));
				
				one(values).getValue(0,0);
				will(returnValue(-9.9));
				one(values).getValue(1,0);
				will(returnValue(-6.4));
			}
		});
		double calcResult = DataUtilities.calculateColumnTotal(values, 0);
		//System.out.println(calcResult);
		assertEquals(calcResult, (-9.9-6.4), .000000001d);
	}

	
	//test for calculating out of bound raw index sums
	//mock for table of 2 row but only one col
	//call function to calculate the 2nd row of the table
	//out of bound exception should be throw
	@Test(expected = IndexOutOfBoundsException.class)
	public void calculateColumnTotalOutOfBounds() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				
				one(values).getValue(0, 1);
				will(throwException(new IndexOutOfBoundsException("Index out of bounds")));
				
			}
		});
		DataUtilities.calculateColumnTotal(values, 1);
	}
	
	
	
	//test for calculating invalid raw index sums
	//mock for table of 2 column 
	//call function to calculate the -1 row of the table
	//out of bound exception should be throw
	@Test(expected = IndexOutOfBoundsException.class)
	public void calculateColumnTotalNegativeRows() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				
				one(values).getValue(0, -1);
				will(throwException(new IndexOutOfBoundsException("Index out of bounds")));
				
			}
		});
		DataUtilities.calculateColumnTotal(values, -1);
	}
	
	
	//Test on boundary cases
	//crate table with 3 rows,
	//call function to calculate 3rd row sum
	@Test
	public void calculateColumnTotalOnBoundaryValid() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(2));
				
				
				one(values).getValue(0,2);
				will(returnValue(3.14));
				one(values).getValue(1,2);
				will(returnValue(2));
				
			}
		});
		double result = DataUtilities.calculateColumnTotal(values, 2);
		System.out.println(result);
		assertEquals(result, 5.14, .000000001d);
	}
	
	
	
	//check how function handle invalid input
	//according to specification:
	//With invalid input, a total of zero will be returned.
	@Test
	public void calculateColumnTotalInvalidDataInput() {
		mockingContext.checking(new Expectations() {
			{
				one(values).getRowCount();
				will(returnValue(0));
				one(values).getValue(0, 0);
				will(throwException(new NullPointerException("Null entries")));
				
			}
		});
		double result = DataUtilities.calculateColumnTotal(values, 0);
		assertEquals(result, 0.0000, .000000001d);
	}
	
	
	
	//pass in null as values2D
	//which should cause a exception
	@Test(expected = Exception.class)
	public void calcColumnNullTest() {
		DataUtilities.calculateColumnTotal(null, 0);
	}
	
	
	/*
     * End of test for calculateColumnTotal---------------------------
     */
	
	/*
     * Begin test for calculateColumnTotal---------------------
     */
	
	
	//Test converting natural number double array to Numberarray
	@Test
	public void createNumberArrayTestValid() {
		double[] input = new double[] {1, 2, 3};
		Number[] expected = new Number[] {1, 2, 3};
		Number[] result = DataUtilities.createNumberArray(input);
		assertArrayEquals("Works",expected,result);
	}
	
	//Test converting decimal number double array to Number array
	@Test
	public void createNumberArrayDecimalValid() {
		double[] input = new double[] {1.25, 2.25, 3.25};
		Number[] expected = new Number[] {1.25, 2.25, 3.25};
		Number[] result = DataUtilities.createNumberArray(input);
		assertArrayEquals("Works",expected,result);
	}
	
	
	//Test converting empty double arrya to number array
	@Test
	public void createNumberArrayEmpty() {
		double[] input = new double[] {};
		Number[] expected = new Number[] {};
		Number[] result = DataUtilities.createNumberArray(input);
		assertArrayEquals("Works",expected,result);
	}
	
	
	//Test negative double array to number array
	@Test
	public void createNumberArrayValidNegative() {
		double[] input = new double[] {-1, -2, -3};
		Number[] expected = new Number[] {-1, -2, -3};
		Number[] result = DataUtilities.createNumberArray(input);
		assertArrayEquals("Works",expected,result);
	}
	
	
	
	/*
     * End of test for calculateColumnTotal---------------------------
     */
	
	
	
	
	/**
	 * Copied from DataUtilities Cummulative Percentage Test
	 */
	
	@Test
	public void testFirstList() {
		testArray = new Mockery();
		testValues = testArray.mock(KeyedValues.class);
		testArray.checking(new Expectations() {
			{
				allowing(testValues).getValue(0);
				will(returnValue(100));
				allowing(testValues).getKey(0);
				will(returnValue(0));
				allowing(testValues).getValue(1);
				will(returnValue(12));
				allowing(testValues).getKey(1);
				will(returnValue(1));
				allowing(testValues).getItemCount();
				will(returnValue(2));
			}
		});
		double[] values = new double[2];
		KeyedValues output = DataUtilities.getCumulativePercentages(testValues);
		values[0] = (double) output.getValue(output.getKey(0));
		values[1] = (double) output.getValue(output.getKey(1));
		assertTrue("Values are greater than one or less than 0", values[0] > 1 || values[0] < 0);
		assertTrue("Values are greater than one or less than 0", values[1] > 1 || values[1] < 0);
	}
	
	@Test
	public void testNegativeList() {
		testArray = new Mockery();
		testValues = testArray.mock(KeyedValues.class);
		testArray.checking(new Expectations() {
			{
				allowing(testValues).getValue(0);
				will(returnValue(-100));
				allowing(testValues).getKey(0);
				will(returnValue(0));
				allowing(testValues).getValue(1);
				will(returnValue(-12));
				allowing(testValues).getKey(1);
				will(returnValue(1));
				allowing(testValues).getItemCount();
				will(returnValue(2));
			}
		});
		double[] values = new double[2];
		KeyedValues output = DataUtilities.getCumulativePercentages(testValues);
		values[0] = (double) output.getValue(output.getKey(0));
		values[1] = (double) output.getValue(output.getKey(1));
		assertTrue("Values are greater than one or less than 0", values[0] > 1 || values[0] < 0);
		assertTrue("Values are greater than one or less than 0", values[1] > 1 || values[1] < 0);
		
	}
	
	@Test
	public void testValueNull() {
		testArray = new Mockery();
		testValues = testArray.mock(KeyedValues.class);
		testArray.checking(new Expectations() {
			{
				allowing(testValues).getValue(0);
				will(throwException(new UnknownKeyException ("0")));
				
				allowing(testValues).getItemCount();
				will(returnValue(1));
			}
		});
		int asserting = 0;
		try {
			DataUtilities.getCumulativePercentages(testValues);
		} catch (Exception e) {
			asserting = 1;
		}
		if (asserting == 0) {
			assertTrue(false);
		}
	}
	
	@Test
	//null argument passed
	public void testNull() {
		
		int asserting = 0;
		try {
			DataUtilities.getCumulativePercentages(null);
		} catch (Exception e) {
			asserting = 1;
		}
		if (asserting == 0) {
			assertTrue(false);
		}
	}
	
	
	/*
	 * Copied from ArrayTest2D
	 */
	
	@Test
    public void testWithValidPositive() {
        double[][] data = {{1.0, 2.0,3.0}, {4.0, 5.0, 6.0}};
        Number[][] expected = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
        Number[][] result = DataUtilities.createNumberArray2D(data);
        assertArrayEquals(expected, result);
    }

   

	@Test
    public void testWithEmptyInputData() {
        double[][] data = {{}};
        Number[][] expected = {{}};
        Number[][] result = DataUtilities.createNumberArray2D(data);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testOutputNegative() {
        double[][] data = {{-1.0, -2.0, -3.0}, {-4.0, -5.0, -6.0}};
        Number[][] expected = {{-1.0, -2.0, -3.0}, {-4.0, -5.0, -6.0}};
        Number[][] result = DataUtilities.createNumberArray2D(data);
        assertArrayEquals(expected, result);
    }
    
    
    @Test
    public void testNullArray() {
    	boolean test = false;
        try {
        	Number[][] result = DataUtilities.createNumberArray2D(null);
        } catch (Exception e) {
        	test = true;
        }
        assertTrue(test);
        
    }
	
	
	

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    	System.out.println("All test completed (hopefully)");
    }
    
    
}
