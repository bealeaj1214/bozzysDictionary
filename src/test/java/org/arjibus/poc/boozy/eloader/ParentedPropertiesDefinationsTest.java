package org.arjibus.poc.boozy.eloader;

import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;



public class ParentedPropertiesDefinationsTest { 


    static Pattern regex = Pattern.compile("\\s*=\\s*");


    static Logger logger = Logger.getLogger(ParentedPropertiesDefinationsTest.class);


    static final String propKey="parented.bean.definations";

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
    public void checkParsingAndLoad() throws Exception{


	String parentAndResourceValue=System.getProperty(propKey);

	Assert.assertNotNull(parentAndResourceValue);

	String[] splitElements = regex.split(parentAndResourceValue);

	Assert.assertEquals("Should be a 2 elements",2,splitElements.length);

	String parentName = splitElements[0];

	String resourceLocation=  splitElements[1];
	Resource resource =
	    ResourceLocationUtils.convertLocation(resourceLocation);

	Assert.assertNotNull(resource);

	Resource[] resourceSet = { resource };

	BeanDefinationLoadAction loadAction =
	    new ParentedPropertiesBeanDefinationLoadAction(parentName,resourceSet);


	GenericApplicationContext ctx = new GenericApplicationContext();

	loadAction.loadBeanDefination(ctx);

	ctx.refresh();
    }

}
