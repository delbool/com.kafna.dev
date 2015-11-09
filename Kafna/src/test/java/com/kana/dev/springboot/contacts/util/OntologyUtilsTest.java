package com.kana.dev.springboot.contacts.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntologyUtilsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OntologyUtilsTest.class.getSimpleName());
	private OntologyUtils ontologyUtils;

	@Before
	public void setup() {
		ontologyUtils = new OntologyUtils();
	}

	@Test
	public void testDecode() {
		String result1 = ontologyUtils.joinWithSeparator(null, null);
		Assert.assertNotNull(result1);
		Assert.assertEquals("", result1);

		String result2 = ontologyUtils.joinWithSeparator(null, "John");
		Assert.assertNotNull(result2);
		Assert.assertEquals("John", result2);

		String result3 = ontologyUtils.joinWithSeparator("", "John", "Smith", "M");
		Assert.assertNotNull(result3);
		Assert.assertEquals("John|Smith|M", result3);

		String result4 = ontologyUtils.joinWithSeparator("_", new String[] { "John", "Smith", "M" });
		Assert.assertNotNull(result4);
		Assert.assertEquals("John_Smith_M", result4);
		
		String expression = "joinWithSeparator::~::Michael,Batista,Johnson";
		String result5 = ontologyUtils.evaluate(expression);
		Assert.assertNotNull(result5);
		LOGGER.info("Expression {} is evaluates as {} ", expression, result5);
		Assert.assertEquals("Michael~Batista~Johnson", result5);
		
	}

	@Test
	public void testIif() {
		String result1 = ontologyUtils.iif(null, null);
		Assert.assertNotNull(result1);
		Assert.assertEquals("", result1);

		String result2 = ontologyUtils.iif(null, new String[] { "M" });
		Assert.assertNotNull(result2);
		Assert.assertEquals("", result2);

		String result3 = ontologyUtils.iif("M", new String[] { "M", "Male", "F", "Female" });
		Assert.assertNotNull(result3);
		Assert.assertEquals("Male", result3);

		String result4 = ontologyUtils.iif("F", new String[] { "M", "Male", "F", "Female", "Unknown" });
		Assert.assertNotNull(result4);
		Assert.assertEquals("Female", result4);

		String result5 = ontologyUtils.iif("X", new String[] { "M", "Male", "F", "Female", "Unknown" });
		Assert.assertNotNull(result5);
		Assert.assertEquals("Unknown", result5);
		
		
	}

	@Test
	public void testIifForEach() {
		String propertyCategoryId = null;
		String [] possibleValues = null;
		String result1 = ontologyUtils.iif(propertyCategoryId, possibleValues);
		Assert.assertNotNull(result1);
		Assert.assertEquals("", result1);

		propertyCategoryId = null;
		possibleValues = new String[]{"ADP", "Image1"};		
		String result2 = ontologyUtils.iif(propertyCategoryId, possibleValues);
		Assert.assertNotNull(result2);
		Assert.assertEquals("", result2);

		propertyCategoryId = "ADP";
		possibleValues = new String[]{"ADP", "Image1"};		
		String result3 = ontologyUtils.iif(propertyCategoryId, possibleValues);
		Assert.assertNotNull(result3);
		Assert.assertEquals("Image1", result3);

		propertyCategoryId = "ARM";
		possibleValues = new String[] { "ADP", "ImageAdp", "AIR", "ImageAir", "ARM", "ImageArm", "CCM", "ImageCcm", "CDG", "ImageCdg", 
				"DRG", "ImageDrg", "OTH", "ImageOth", "PRH", "ImagePrh", "REL", "ImageRel", "VEH", "ImageVeh", 
				"VES", "ImageVes", "DOC", "ImageDoc", "ImageDefault"};
		String result5 = ontologyUtils.iif(propertyCategoryId, possibleValues);
		Assert.assertNotNull(result5);
		Assert.assertEquals("ImageArm", result5);

		propertyCategoryId = "XYZ";
		possibleValues = new String[] { "ADP", "ImageAdp", "AIR", "ImageAir", "ARM", "ImageArm", "CCM", "ImageCcm", "CDG", "ImageCdg", 
				"DRG", "ImageDrg", "OTH", "ImageOth", "PRH", "ImagePrh", "REL", "ImageRel", "VEH", "ImageVeh", 
				"VES", "ImageVes", "DOC", "ImageDoc", "ImageDefault"};
		String result6 = ontologyUtils.iif(propertyCategoryId, possibleValues);
		Assert.assertNotNull(result6);
		Assert.assertEquals("ImageDefault", result6);
	}
	
}
