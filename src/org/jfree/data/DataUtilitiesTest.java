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
   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Before
	
	public void SetUp( ) {
	
	
		
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
}
