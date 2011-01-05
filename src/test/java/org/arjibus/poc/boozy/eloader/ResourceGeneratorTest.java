package org.arjibus.poc.boozy.eloader;

import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.core.io.Resource;

public class ResourceGeneratorTest { 


    static Pattern regex = Pattern.compile(",");

    static Pattern altRegex = Pattern.compile("\\s*,\\s*");

    static final String fooKey="foo.greek.letters";
    static final String barKey="bar.greek.letters";
    static final String snafuKey="snafu.greek.letters";

    static Logger logger = Logger.getLogger(ResourceGeneratorTest.class);


    @BeforeClass
    static public void before() {
	Logger.getRootLogger().setLevel((Level) Level.INFO);
        BasicConfigurator.configure();
    }


    @AfterClass
    static public void cleanUp(){
	BasicConfigurator.resetConfiguration();
    }


    @Test
    public void checkV1Regex(){

	String input="alpha,beta, gamma, delta,epsilon";

	String[] results = regex.split(input);
	Assert.assertEquals(5,results.length);
	Assert.assertEquals(" gamma",results[2]);
	Assert.assertEquals(" delta",results[3]);
    }

    @Test
    public void checkV1AltRegex(){

	String input="alpha,beta, gamma, delta,epsilon";

	String[] results = altRegex.split(input);
	Assert.assertEquals(5,results.length);
	Assert.assertEquals("gamma",results[2]);
	Assert.assertEquals("delta",results[3]);
    }

    @Test
    public void checkSingleValueInputAltRegex(){

	String input="omicron";

	String[] results = altRegex.split(input);
	Assert.assertEquals(1,results.length);
	Assert.assertEquals(input,results[0]);
    }

    @Test
    public void checkFooGreeks() {
	String input=System.getProperty(fooKey);
	String[] results = altRegex.split(input);

	Assert.assertEquals(5,results.length);
	Assert.assertEquals("gamma",results[2]);
	Assert.assertEquals("delta",results[3]);

    }

    @Test
    public void checkBarGreeks() {
	String input=System.getProperty(barKey);

	String[] results = altRegex.split(input);

	Assert.assertEquals(5,results.length);
	Assert.assertEquals("upsilon",results[2]);
	Assert.assertEquals("tau",results[3]);

    }

    @Test
    public void checkSanfuGreeks() {
	String input=System.getProperty(snafuKey);

	String[] results = altRegex.split(input);

	Assert.assertEquals(1,results.length);
	Assert.assertEquals("omicron",results[0]);
    }


    public ClasspathEtcResourceGenerator buildResourceLocator(){

	ClasspathEtcResourceGenerator resourceGenator =
	    ClasspathEtcResourceGenerator.getResourceGenerator();

	return resourceGenator;
    }


    @Test
    public void checkSanfuResources() throws Exception  {

	checkResourcesWithKey(snafuKey,1);
    }


    @Test
    public void checkFooResources() throws Exception  {
	checkResourcesWithKey(fooKey,5);
	
    }

    @Test
    public void checkBarResources() throws Exception  {
	checkResourcesWithKey(barKey,5);
	
    }


    public  void checkResourcesWithKey(String key,int expectedCount)throws Exception  {

	ClasspathEtcResourceGenerator resourceGenator= buildResourceLocator();
	Resource[] resources =resourceGenator.getResources(key);
	
	Assert.assertNotNull(resources);
	Assert.assertEquals(expectedCount,resources.length);
	for(Resource resource : resources){
	    logger.debug(" name " + resource.getFilename());
	    logger.debug(" URL " + resource.getURL());
	    Assert.assertTrue(resource.exists());
	}
    }
}
