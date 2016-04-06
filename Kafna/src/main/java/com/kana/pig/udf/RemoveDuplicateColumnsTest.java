package com.kana.pig.udf;

import java.util.List;

import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.impl.logicalLayer.schema.Schema.FieldSchema;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * Unit tests for RemoveDuplicateColumns UDF
 */
public class RemoveDuplicateColumnsTest {

	private RemoveDuplicateColumns removeColumns;
	private Schema inputSchema;
	private Tuple inputTuple;

	@Before
	public void setup() throws Exception {
		inputSchema = new Schema();
		String[] schemaData = new String[] { //
				"person2::person1::ID", //
				"person2::person1::NAME", //
				"address::ID", //
				"address::ADDRESS", //
				"address::CITY", //
				"address::STATE", //
				"address::ZIPCODE" };

		for (String s : schemaData) {
			inputSchema.add(new Schema.FieldSchema(s, DataType.CHARARRAY));
		}

		inputTuple = TupleFactory.getInstance().newTuple(7);
		inputTuple.set(0, "123"); // person::ID
		inputTuple.set(1, "John Smith");
		inputTuple.set(2, "123"); // address::ID, same as person::ID (duplicate)
		inputTuple.set(3, "12345 Main Avenue");
		inputTuple.set(4, "Alexandira");
		inputTuple.set(5, "Virginia");
		inputTuple.set(6, "22222");
		
		removeColumns = new RemoveDuplicateColumns("address::ID"); // remove duplicate ID
		removeColumns.setInputSchema(inputSchema);
	}
	
	@Test
	public void testOutputSchema_Success() throws Exception {
		
		Schema outputSchema = removeColumns.outputSchema(inputSchema);		
		FieldSchema fs = outputSchema.getField(0);
		List<Schema.FieldSchema> schemaAfterRemoval = fs.schema.getFields();
		Assert.assertEquals(7, inputSchema.size());
		Assert.assertEquals(6, schemaAfterRemoval.size());		
	}

	@Test
	public void testExec_Sucess() throws Exception {
		Tuple outputTuple = removeColumns.exec(inputTuple);
		Assert.assertEquals(7, inputTuple.size());
		Assert.assertEquals(6, outputTuple.size());
		
		Assert.assertEquals("123", outputTuple.get(0).toString());
		Assert.assertEquals("John Smith", outputTuple.get(1).toString());
		
		// address ID is removed, so its index location (2) is taken away by next field value
		Assert.assertEquals("12345 Main Avenue", outputTuple.get(2).toString());
		Assert.assertEquals("Alexandira", outputTuple.get(3).toString());
		Assert.assertEquals("Virginia", outputTuple.get(4).toString());
		Assert.assertEquals("22222", outputTuple.get(5).toString());
	}

	@Test
	public void testOutputSchema_NullOrEmptySchema() throws Exception {
		Schema schemaIn = null;
		Schema schemaOut = removeColumns.outputSchema(schemaIn);
		Assert.assertEquals(schemaIn, schemaOut);
		
		schemaIn = new Schema();
		Assert.assertTrue(0 == schemaIn.size());
		schemaOut = removeColumns.outputSchema(schemaIn);
		Assert.assertEquals(schemaIn, schemaOut);
	}

	@Test
	public void testExec_NullOrEmptyTuple() throws Exception {
		Tuple tupleIn = null;
		Tuple tupleOut = removeColumns.exec(tupleIn);
		Assert.assertEquals(tupleIn, tupleOut);
		
		tupleIn = TupleFactory.getInstance().newTuple();
		Assert.assertTrue(0 == tupleIn.size());
		tupleOut = removeColumns.exec(tupleIn);
		Assert.assertEquals(tupleIn, tupleOut);
	}

	@After
	public void cleanup(){
		removeColumns = null;
		inputSchema = null;
		inputTuple = null;
	}
}

