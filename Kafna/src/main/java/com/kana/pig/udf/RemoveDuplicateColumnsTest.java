package com.kana.pig.udf;

import java.io.IOException;
import java.util.List;

import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.impl.logicalLayer.schema.Schema.FieldSchema;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RemoveDuplicateColumnsTest {

	private RemoveDuplicateColumns removeDups;
	private Schema inputSchema;
	private Tuple inputTuple;

	@Before
	public void setup() throws ExecException {
		removeDups = new RemoveDuplicateColumns("address::ID");
		inputSchema = new Schema();
		String[] headerRowData = new String[] { //
				"person2::person1::ID", //
				"person2::person1::NAME", //
				"address::ID", //
				"address::ADDRESS", //
				"address::CITY", //
				"address::STATE", //
				"address::ZIPCODE" };

		for (String s : headerRowData) {
			inputSchema.add(new Schema.FieldSchema(s, DataType.TUPLE));
		}

		removeDups.setInputSchema(inputSchema);

		inputTuple = TupleFactory.getInstance().newTuple(7);
		inputTuple.set(0, "123"); // person::ID
		inputTuple.set(1, "John Smith");
		inputTuple.set(2, "123"); // address::ID, same as person::ID (duplicate)
		inputTuple.set(3, "12345 Main Avenue");
		inputTuple.set(4, "Alexandira");
		inputTuple.set(5, "Virginia");
		inputTuple.set(6, "22222");
	}
	
	@Test
	public void testOutputSchemaSuccess() throws FrontendException {
		
		Schema outputSchema = removeDups.outputSchema(inputSchema);		
		FieldSchema fs = outputSchema.getField(0);
		List<Schema.FieldSchema> schemaAfterRemoval = fs.schema.getFields();
		Assert.assertEquals(7, inputSchema.size());
		Assert.assertEquals(6, schemaAfterRemoval.size());
	}

	@Test
	public void testExecSucess() throws IOException {
		Tuple outputTuple = removeDups.exec(inputTuple);
		Assert.assertEquals(7, inputTuple.size());
		Assert.assertEquals(6, outputTuple.size());
	}

	@Test
	public void testExecNullOrEmptyTuple() throws IOException {
		Tuple tuple = null;
		Tuple outputTuple = removeDups.exec(tuple);
		Assert.assertNull(outputTuple);
		
		tuple = TupleFactory.getInstance().newTuple();
		Assert.assertTrue(0 == tuple.size());
		outputTuple = removeDups.exec(tuple);
		Assert.assertNotNull(outputTuple);
		Assert.assertTrue(0 == outputTuple.size());		
	}

}
