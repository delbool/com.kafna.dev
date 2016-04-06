package com.kana.pig.udf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The purpose of this UDF is to delete duplicate JOIN columns added
 * after join operation of two or more tables. This is done by
 * explicitly specifying the fields to be removed.
 * 
 * This may be helpful when the tables have many columns and the use of
 * FOREACH..GENERATE... to explicitly listing all columns to be included
 * is not easy.
 *
 */
public class RemoveDuplicateColumns extends EvalFunc<Tuple> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveDuplicateColumns.class);

	// This class adds this alias to output schema
	private final static String COLUMNS_REMOVAL = "CR";
	
	private String[] columnsToRemove;
	private List<Integer> keepColumnIndices;

	
	public RemoveDuplicateColumns(String... columns) {
		this.columnsToRemove = columns;
	}

	private List<Integer> getKeepColumnIndices(Schema inputSchema) throws FrontendException {
		List<Integer> schemaIndices = new ArrayList<Integer>();

		for (int i = 0; i < inputSchema.size(); i++) {
			String field;
			boolean found = false;
			if (inputSchema.getField(i) != null) {
				field = inputSchema.getField(i).toString();
				for (String column : columnsToRemove) {
					if (field.contains(column)) {
						found = true;
						break;
					}
				}
			}

			if (!found) {
				schemaIndices.add(i);
			}
		}
		return schemaIndices;
	}

	@Override
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
			LOGGER.warn("Tuple is empty. Returning same input tuple as output without processing: {}", input);
			return input;
		}

		if (keepColumnIndices == null) {
			keepColumnIndices = getKeepColumnIndices(getInputSchema());
		}

		Tuple tuple = TupleFactory.getInstance().newTuple();
		for (Integer i : keepColumnIndices) {
			tuple.append(input.get(i));
		}

		return tuple;
	}

	@Override
	public Schema outputSchema(Schema input) {

		if (input == null || input.size() == 0) {
			LOGGER.warn("Schema is empty.  Returning same input schema as output without processing: " + input);
			return input;
		}

		try {
			if (keepColumnIndices == null) {
				keepColumnIndices = getKeepColumnIndices(input);
			}

			List<Schema.FieldSchema> outputFieldSchema = new ArrayList<Schema.FieldSchema>(keepColumnIndices.size());
			List<Schema.FieldSchema> inputFieldSchema = input.getFields();
			for (Integer key : keepColumnIndices) {
				outputFieldSchema.add(inputFieldSchema.get(key));
			}

			Schema outputSchema = new Schema(outputFieldSchema);
			return new Schema(new Schema.FieldSchema(COLUMNS_REMOVAL, outputSchema));

		} catch (FrontendException e) {
			LOGGER.error("Unable to process removal of columns '{}' from schema: '{}'", columnsToRemove, input);
			throw new RuntimeException("Unable to process removal of columns '" + columnsToRemove + "'  from schema: " + input, e);
		}
	}
}