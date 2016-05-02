package com.kana.pig.udf;

import java.io.IOException;

import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.junit.Test;
import org.junit.Test.None;

public class CartesianProductDetectorTest {

	private CartesianProductDetector cpd;

	@Test(expected = RuntimeException.class)
	public void testExecYesCartesianProductDetectedUsingLeftJoin() {
		cpd = getLeftJoinYesCartesian();
		Tuple tuple = TupleFactory.getInstance().newTuple();
		try {
			cpd.exec(tuple);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = None.class)
	public void testExecNoCartesianProductDetected() {
		
		// left join
		cpd = getLeftJoinNoCartesian();
		Tuple tuple = TupleFactory.getInstance().newTuple();
		try {
			cpd.exec(tuple);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// inner join
		cpd = getInnerJoinNoCartesian();
		try {
			cpd.exec(tuple);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// right join
		cpd = getRightJoinNoCartesian();
		try {
			cpd.exec(tuple);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private CartesianProductDetector getLeftJoinYesCartesian() {
		String leftAlias = "campus";
		String rigtAlias = "school";
		String joinType = "Left Join";
		String beforeJoinCount = "1000";
		String afterJoinCount = "20000";
		cpd = new CartesianProductDetector(leftAlias, rigtAlias, joinType, beforeJoinCount, afterJoinCount);
		return cpd;
	}

	private CartesianProductDetector getLeftJoinNoCartesian() {
		String leftAlias = "campus";
		String rigtAlias = "school";
		String joinType = "Left Join";
		String beforeJoinCount = "1000";
		String afterJoinCount = "1000";
		cpd = new CartesianProductDetector(leftAlias, rigtAlias, joinType, beforeJoinCount, afterJoinCount);
		return cpd;
	}

	private CartesianProductDetector getInnerJoinNoCartesian() {
		String leftAlias = "campus";
		String rigtAlias = "school";
		String joinType = "Inner Join";
		String beforeJoinCount = "1000";
		String afterJoinCount = "500";
		cpd = new CartesianProductDetector(leftAlias, rigtAlias, joinType, beforeJoinCount, afterJoinCount);
		return cpd;
	}

	private CartesianProductDetector getRightJoinNoCartesian() {
		String leftAlias = "campus";
		String rigtAlias = "school";
		String joinType = "Right Join";
		String beforeJoinCount = "1000";
		String afterJoinCount = "1000";
		cpd = new CartesianProductDetector(leftAlias, rigtAlias, joinType, beforeJoinCount, afterJoinCount);
		return cpd;
	}


	@Test(expected = RuntimeException.class)
	public void outputSchemaTestYesCartesianProductDetectedUsingLeftJoin() {
		Schema schema = new Schema();
		cpd = getLeftJoinYesCartesian();
		cpd.outputSchema(schema);
	}
	
}
