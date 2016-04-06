package com.kana.pig.udf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * The purpose of this UDF is to delete duplicate JOIN columns added 
 * after each join operation of two or more tables.  However, if the tables 
 * joined have same column names other than their join columns do not use this UDF.  
 * It may delete non-join data column(s) too.  
 * 
 */
public class RemoveDuplicateColumns extends EvalFunc<Tuple> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveDuplicateColumns.class);

	private String[] columnsToRemove;
	private List<Integer> columnIndicesTokeep;
	
	public RemoveDuplicateColumns(String... columns){
		this.columnsToRemove = columns;
	}
	
	private List<Integer> getColumnIndicesTokeep(Schema inputSchema) throws FrontendException {
		List<Integer> schemaIndices = new ArrayList<Integer>();

		for (int i = 0; i < inputSchema.size(); i++) {
			String field;
			boolean found = false;
			if (inputSchema.getField(i) != null) {
				field = inputSchema.getField(i).toString();
				for ( String column : columnsToRemove){
					if (field.contains(column)){
						found = true;
						break;
					}
				}			
			}

			if (!found){
				schemaIndices.add(i);
			}
		}
		return schemaIndices;
	}

	@Override
	public Tuple exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
			LOGGER.warn("Tuple is empty.  Returning same without processing: " + input);
			return input;
		}

		if ( columnIndicesTokeep == null){
			columnIndicesTokeep = getColumnIndicesTokeep(getInputSchema());
		}

		Tuple tuple = TupleFactory.getInstance().newTuple();
		for (Integer i : columnIndicesTokeep) {
			tuple.append(input.get(i));
		}

		return tuple;
	}

	@Override
	public Schema outputSchema(Schema input) {

		if (input == null || input.size() == 0){
			LOGGER.warn("Schema is empty.  Returning same without processing: " + input);
			return input;
		}
		
		try {
			if ( columnIndicesTokeep == null){
				columnIndicesTokeep = getColumnIndicesTokeep(input);
			}
			List<Schema.FieldSchema> inputFieldSchema = input.getFields();
			List<Schema.FieldSchema> outputFieldSchema = new ArrayList<Schema.FieldSchema>(columnIndicesTokeep.size());

			for (Integer key : columnIndicesTokeep) {
				outputFieldSchema.add(inputFieldSchema.get(key));
			}
			
			Schema outputSchema = new Schema(outputFieldSchema);
			
			return new Schema(new Schema.FieldSchema("myalias", outputSchema, DataType.TUPLE));
			
		} catch (FrontendException e) {
			LOGGER.info("Unable to process removal of duplicate columns from schema: " + input );
			throw new RuntimeException("Unable to process removal of columns from schema: " + input, e);
		}
	}
}