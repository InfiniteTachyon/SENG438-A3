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

public class DataUtilitiesTestCloneAndArray {
	Mockery mock;
	Values2D empty;
	double [][] arrNormal;
	double [][] arrOneEmpty;
	double [][] arrTwoEmpty;
	double [][] arrResult;
   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
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
	

	
	
	
	
}
