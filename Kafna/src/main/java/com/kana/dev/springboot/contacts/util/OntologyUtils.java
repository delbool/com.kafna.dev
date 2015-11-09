package com.kana.dev.springboot.contacts.util;

import org.apache.commons.lang3.StringUtils;

public class OntologyUtils {

	public String evaluate(String expression){
		
		String[] parts = expression.split("::");
		if ( parts[0].equals("joinWithSeparator")){
			return joinWithSeparator(parts[1], parts[2].split(","));
		}
		
		return null;
	}
	public String joinWithSeparator(String concatentator, String... fields) {
		if (StringUtils.isBlank(concatentator)) {
			concatentator = "|";
		}
		StringBuilder b = new StringBuilder();
		if (fields != null && fields.length > 0) {
			b.append(fields[0]);
			for (int i = 1; i < fields.length; i++) {
				b.append(concatentator);
				b.append(fields[i]);
			}
		}
		return b.toString();
	}
	
	public String iif(String field, String [] possibleValues){
		String defaultValue;
		int iterations;
		
		if ( StringUtils.isBlank(field) || possibleValues == null ){
			return "";
		}
		
		if (possibleValues.length % 2 == 0){
			// even number of parameters - no default
			defaultValue = "";
			iterations = possibleValues.length;
		}else{
			// odd number of parameters - last one is default.
			defaultValue = possibleValues[possibleValues.length - 1];
			iterations = (possibleValues.length - 1 );			
		}
		
		for ( int i = 0; i < iterations; i = i+2){
			if ( field.equals(possibleValues[i]) ){
				return possibleValues[i+1];
			}
		}
		return defaultValue;
	}
}
