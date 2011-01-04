package org.arjibus.poc.boozy.eloader;

import java.io.File;

import java.net.URL;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import org.springframework.beans.BeansException;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import org.springframework.context.support.GenericApplicationContext;

import org.springframework.core.io.DefaultResourceLoader;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;


public class FooXMLStructureTst { 

    static final String fooSpringXMLResource="/etc/foo.xml";


    @BeforeClass
    static public void before() {
	Logger.getRootLogger().setLevel((Level) Level.INFO);
        BasicConfigurator.configure();
    }


    @AfterClass
    static public void cleanUp(){
	BasicConfigurator.resetConfiguration();
    }

    public File provideFooXmlResourceAsFile() {
	URL fileURL = this.getClass().getResource(fooSpringXMLResource);
	File file = new File(fileURL.getFile());

	return file;
    }

    @Test
    public void checkFooResourceAndPath(){

	File file = provideFooXmlResourceAsFile();
	Assert.assertTrue(file.exists());

	System.out.println(file.getAbsolutePath());
    }


    @Test
    public void loadAndCheckFooSpring() {

	File file = provideFooXmlResourceAsFile();

	String[] args= {file.getAbsolutePath() };

	GenericApplicationContext ctx = new GenericApplicationContext();
	
	XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		
	FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
	
	xmlReader.setResourceLoader(resourceLoader);
	
	xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);

	FileSystemResource[] resources = new FileSystemResource[args.length];

	for(int i=0; i < args.length ; i++) {
	    resources[i] = new FileSystemResource(args[i]);
	}

	xmlReader.loadBeanDefinitions(resources);

	ctx.refresh();

    }
}
