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

import org.springframework.context.support.GenericApplicationContext;

import org.springframework.core.io.Resource;

public class BootstrapContextLoaderTest { 

    static final String demoPropertiesResource="/conf/demo.properties";


    @BeforeClass
    static public void before() {
        Logger.getRootLogger().setLevel((Level) Level.INFO);
        BasicConfigurator.configure();
    }

    @AfterClass
    static public void cleanUp(){
        BasicConfigurator.resetConfiguration();
    }



    public File provideDemoPropertiesResourceAsFile() {
	URL fileUrl = this.getClass().getResource(demoPropertiesResource);
	File file = new File(fileUrl.getFile());

	return file;
    }


    @Test
    public void checkDemoResource() {

	File file=provideDemoPropertiesResourceAsFile();
	Assert.assertTrue(file.exists());
    }


    @Test
    public void checkTriPhaseBootstrapContextLoader() throws Exception {

	BootstrapContextLoader loader = new TriPhaseBootstrapContextLoader();

	File file = provideDemoPropertiesResourceAsFile();

	String[] args = { file.getAbsolutePath() };

	GenericApplicationContext context =
	    loader.createContext(args);

	Assert.assertNotNull(context);

	context.refresh();
    }

}
