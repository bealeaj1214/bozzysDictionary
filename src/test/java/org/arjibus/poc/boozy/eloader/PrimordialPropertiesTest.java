package org.arjibus.poc.boozy.eloader;

import java.util.Properties;


import org.junit.Assert;
//import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.core.io.UrlResource;


public class PrimordialPropertiesTest { 

    static  final String URL  = "classpath:etc/common/firstLoad.properties";



    @Test
    public void basicIdeaTest() throws Exception{


        UrlResource resource = new UrlResource(ResourceUtils.getURL(URL));


        Properties props = new Properties();
        props.load(resource.getInputStream());

        Assert.assertNotNull(props.getProperty("a.series.letters"));

        Assert.assertEquals("alpha,beta, gamma, delta,epsilon",
                            props.getProperty("a.series.letters"));

        Assert.assertNotNull(props.getProperty("b.series.letters"));

        Assert.assertNotNull(props.getProperty("c.series.letters"));


    }



    @Test
    public void checkLoading() throws Exception {

        PrimordialPropertyLoader.
            loadPrimordialProperties(URL);

        Assert.assertNotNull(System.getProperty("a.series.letters"));

        Assert.assertEquals("alpha,beta, gamma, delta,epsilon",
                            System.getProperty("a.series.letters"));

        Assert.assertNotNull(System.getProperty("b.series.letters"));

        Assert.assertNotNull(System.getProperty("c.series.letters"));



    }
}
