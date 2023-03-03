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
	Mockery mock;
	Values2D empty;
	KeyedValues values;
	
	
   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	double [][] arrNormal;
	double [][] arrOneEmpty;
	double [][] arrTwoEmpty;
	double [][] arrResult;
   
	
	@Before
	public void SetUp( )throws Exception  {
		
		
		arrNormal = new double [][]{
				{1,2,3},
				{4,5,6}
		};
		arrOneEmpty = new double [][]{
			{1,2},
			{}
		};
		arrTwoEmpty = new double [][]{
			{},
			{}
		};
	
		
	}
	

	@Test
	public void cloneTestNormal() {
		
		arrResult = DataUtilities.clone(arrNormal);
		assertTrue("Should Equal", DataUtilities.equal(arrResult, arrNormal));
	}
	
	
	@Test
	public void cloneTestOneEmpty() {
		
		arrResult = DataUtilities.clone(arrOneEmpty);
		assertTrue("Should Equal", DataUtilities.equal(arrResult, arrOneEmpty));
	}
	
	
	@Test
	public void cloneTestTwoEmpty() {
		
		arrResult = DataUtilities.clone(arrTwoEmpty);
		assertTrue("Should Equal", DataUtilities.equal(arrResult, arrTwoEmpty));
	}
		
	
	
	
	
	
	
	@Test
	public void createNumberArrayTestInt() {
		Number[] expected = {7,2,4};
		double[] doubleArray = {7,2,4};
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}
	
	@Test
	public void createNumberArrayTestDouble() {
		Number[] expected = { 1.7, 2.2, 3.4 };
		double[] doubleArray = { 1.7, 2.2, 3.4 };
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}
	@Test
	public void createNumberArrayTestIntNegative() {
		Number[] expected = {-7,-2,-4};
		double[] doubleArray = {-7,-2,-4};
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}
	@Test
	public void createNumberArrayTestDoubleNegative() {
		Number[] expected = { -1.7, -2.2, -3.4 };
		double[] doubleArray = { -1.7,- 2.2, -3.4 };
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}
	@Test
	public void createNumberArrayTestOneElement() {
		Number[] expected = {6 };
		double[] doubleArray = { 6};
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}
	@Test
	public void createNumberArrayTestEmpty() {
		Number[] expected = { };
		double[] doubleArray = { };
		
		Number[] result = DataUtilities.createNumberArray(doubleArray);
		assertArrayEquals("Should Equal",expected,result);
	}

	
	
	
	
	
	
	@Test
	public void createNumberArray2DNormal() {
		Number[][] expected = { { 3.1, 1.4, 1.5}, { 1.3 , 4.1, 5.1} };
		double[][] doubleArray = { { 3.1, 1.4, 1.5}, { 1.3 , 4.1, 5.1} };
		Number[][] result = DataUtilities.createNumberArray2D(doubleArray);

		assertArrayEquals("Should Equal", expected, result);
	}
	@Test
	public void createNumberArray2DNoArray() {
		Number[][] expected = { };
		double[][] doubleArray = { };
		Number[][] result = DataUtilities.createNumberArray2D(doubleArray);

		assertArrayEquals("Should Equal", expected, result);
	}
	@Test
	public void createNumberArray2DOneEmpty() {
		Number[][] expected = { { 3.1, 1.4, 1.5}, {} };
		double[][] doubleArray = { { 3.1, 1.4, 1.5}, {} };
		Number[][] result = DataUtilities.createNumberArray2D(doubleArray);

		assertArrayEquals("Should Equal", expected, result);
	}
	@Test
	public void createNumberArray2DTwoEmpty() {
		Number[][] expected = { { }, {} };
		double[][] doubleArray = { { }, {} };
		Number[][] result = DataUtilities.createNumberArray2D(doubleArray);

		assertArrayEquals("Should Equal", expected, result);
	}
	
	//Calculate Column Total
	
	@Test
	public void calcNegCol() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, -1);
				will(returnValue(10.0));
				one(empty).getValue(1, 0);
				will(returnValue(20.0));
				one(empty).getValue(2, 0);
				will(returnValue(30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		try {
			DataUtilities.calculateColumnTotal(empty, -1);
			
			throw new Exception("Should return error");
		} catch (Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
	}
	
	@Test
	public void calcPosColPosVal() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(10.0));
				one(empty).getValue(1, 1);
				will(returnValue(20.0));
				one(empty).getValue(2, 1);
				will(returnValue(30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", 60.0, DataUtilities.calculateColumnTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcPosColNegVal() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(-10.0));
				one(empty).getValue(1, 1);
				will(returnValue(-20.0));
				one(empty).getValue(2, 1);
				will(returnValue(-30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", -60.0, DataUtilities.calculateColumnTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcColumnEmpty() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getRowCount();
				will(returnValue(0));
			}
		});
		assertEquals("Total adds to 0", 0, DataUtilities.calculateColumnTotal(empty, 0), 0.0000001d);;
	}
	
	@Test
	public void calcColNull() {
		try {
			DataUtilities.calculateColumnTotal(null, 0);
			
			throw new Exception("Should return error");
		} catch (Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
		
	}
	
	@Test
	public void calcColNullNegData() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(-30.0));
				one(empty).getValue(2, 1);
				will(returnValue(-30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", -60.0, DataUtilities.calculateColumnTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcColNullPosData() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(30.0));
				one(empty).getValue(2, 1);
				will(returnValue(30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.",60.0, DataUtilities.calculateColumnTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcColNullCheck() {
		try {
			int[] rows = {0};
			DataUtilities.calculateColumnTotal(null, 0, rows);
			throw new Exception("Should return error");
		} catch(Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
	}
	
	@Test
	public void calcColNullPosDataObj() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(30.0));
				one(empty).getValue(2, 1);
				will(returnValue(30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		int[] rows = {1};
		assertEquals("The total has not been added correctly.",60.0, DataUtilities.calculateColumnTotal(empty, 1, rows),0.0000001d);
	}
	
	@Test
	public void calcColNullPosDataObjNonNull() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(10.0));
				one(empty).getValue(1, 1);
				will(returnValue(30.0));
				one(empty).getValue(2, 1);
				will(returnValue(30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		int[] rows = {1};
		assertEquals("The total has not been added correctly.",70.0, DataUtilities.calculateColumnTotal(empty, 1, rows),0.0000001d);
	}
	@Test
	public void calcColNullNegDataObjNonNull() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(0, 1);
				will(returnValue(-10.0));
				one(empty).getValue(1, 1);
				will(returnValue(-30.0));
				one(empty).getValue(2, 1);
				will(returnValue(-30.0));
				one(empty).getRowCount();
				will(returnValue(3));
			}
		});
		
		int[] rows = {1};
		assertEquals("The total has not been added correctly.",-70.0, DataUtilities.calculateColumnTotal(empty, 1, rows),0.0000001d);
	}
	
	//Calculate Row Total
	
	public void calcRowEmpt() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getColumnCount();
				will(returnValue(0));
				one(empty).getRowCount();
				will(returnValue(0));
			}
		});
		
		assertEquals("Row is not added to 0", 0, DataUtilities.calculateRowTotal(empty, 0), 0.0000001d);
	}
	
	@Test
	public void calcPosRowPosVal() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(1,0);
				will(returnValue(10.0));
				one(empty).getValue(1, 1);
				will(returnValue(20.0));
				one(empty).getValue(1, 2);
				will(returnValue(30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", 60.0, DataUtilities.calculateRowTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcPosRowNetVal() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(1,0);
				will(returnValue(-10.0));
				one(empty).getValue(1, 1);
				will(returnValue(-20.0));
				one(empty).getValue(1, 2);
				will(returnValue(-30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", -60.0, DataUtilities.calculateRowTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void calcNegRow() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(-1, 0);
				will(returnValue(10.0));
				one(empty).getValue(-1, 1);
				will(returnValue(20.0));
				one(empty).getValue(-1, 2);
				will(returnValue(30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		try {
			DataUtilities.calculateRowTotal(empty, -1);
			
			throw new Exception("Should return error");
		} catch (Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
	}
	
	@Test
	public void calcRowNullCheck() {
		try {
			int[] col = {0};
			DataUtilities.calculateRowTotal(null, 0, col);
			throw new Exception("Should return error");
		} catch(Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
	}
	
	@Test
	public void calcPosRowNetValNull() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(1,0);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(-20.0));
				one(empty).getValue(1, 2);
				will(returnValue(-30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		int[]col = {1};
		assertEquals("The total has been added correctly.", -50.0, DataUtilities.calculateRowTotal(empty, 1, col),0.0000001d);
	}
	
	@Test
	public void calcPosRowPosValNull() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(1,0);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(20.0));
				one(empty).getValue(1, 2);
				will(returnValue(30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		int[]col = {1};
		assertEquals("The total has been added correctly.", 50.0, DataUtilities.calculateRowTotal(empty, 1, col),0.0000001d);
	}
	
	@Test
	public void calcPosRowPosValNullTwo() {
		mock = new Mockery();
		empty = mock.mock(Values2D.class);
		
		mock.checking(new Expectations() {
			{
				one(empty).getValue(1,0);
				will(returnValue(null));
				one(empty).getValue(1, 1);
				will(returnValue(20.0));
				one(empty).getValue(1, 2);
				will(returnValue(30.0));
				one(empty).getColumnCount();
				will(returnValue(3));
			}
		});
		
		assertEquals("The total has been added correctly.", 50.0, DataUtilities.calculateRowTotal(empty, 1),0.0000001d);
	}
	
	@Test
	public void twoEqualArrays() {
		double[] [] firstarray = {{1.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		double[] [] secondarray = {{1.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are the same array", true, equals);
	}
	
	@Test
	public void twoNonEqualArrays() {
		double[] [] firstarray = {{1.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		double[] [] secondarray = {{2.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are not the same array", false, equals);
	}
	
	@Test
	public void twoNonEqualArraysDifLength() {
		double[] [] firstarray = {{1.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		double[] [] secondarray = {{2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are not the same array", false, equals);
	}
	
	@Test
	public void nullarrayOne() {
		double[] [] firstarray = null;
		
		double[] [] secondarray = {{2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are not the same array", false, equals);
	}
	
	@Test
	public void nullArrayTwo() {
		double[] [] firstarray = {{1.0,2.0,3.0,4.0,5.0}, {6.0,7.0,8.0,9.0,10.0}};
		
		double[] [] secondarray = null;
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are not the same array", false, equals);
	}
	
	@Test
	public void bothNullArray() {
		double[] [] firstarray = null;
		
		double[] [] secondarray = null;
		
		boolean equals = DataUtilities.equal(firstarray, secondarray);
		
		assertEquals("These are not the same array", true, equals);
	}
	
	@Test
	public void getCumPercentageTestOne() {
		mock = new Mockery();
		values = mock.mock(KeyedValues.class);
		
		mock.checking(new Expectations() {
			{
				atLeast(1).of(values).getValue(0);
				will(returnValue(10));
				atLeast(1).of(values).getKey(0);
				will(returnValue(10));
				atLeast(1).of(values).getValue(1);
				will(returnValue(20));
				atLeast(1).of(values).getKey(1);
				will(returnValue(11));
				atLeast(1).of(values).getItemCount();
				will(returnValue(2));
			}
		});
		
		assertEquals("Cumulative Percentage is not correct", 0.33333333333333, DataUtilities.getCumulativePercentages(values).getValue(0).doubleValue(), 0.0000001d );
		
	}
	
	@Test
	public void getCumPercentageNull() {
		try {
			DataUtilities.getCumulativePercentages(null);
			throw new Exception("Should throw Error");
		} catch(Exception e) {
			assertEquals("Error Thrown Correctly", e.getClass(), IllegalArgumentException.class);
		}
	}
	
	@Test
	public void getCumPercentagewithNull() {
		mock = new Mockery();
		values = mock.mock(KeyedValues.class);
		
		mock.checking(new Expectations() {
			{
				atLeast(1).of(values).getValue(0);
				will(returnValue(10));
				atLeast(1).of(values).getKey(0);
				will(returnValue(10));
				atLeast(1).of(values).getValue(1);
				will(returnValue(20));
				atLeast(1).of(values).getKey(1);
				will(returnValue(11));
				atLeast(1).of(values).getValue(2);
				will(returnValue(null));
				atLeast(1).of(values).getKey(2);
				will(returnValue(12));
				atLeast(1).of(values).getItemCount();
				will(returnValue(2));
			}
		});
		assertEquals("Cumulative Percentage is not correct", 0.33333333333333, DataUtilities.getCumulativePercentages(values).getValue(0).doubleValue(), 0.0000001d );
		
	}
}
