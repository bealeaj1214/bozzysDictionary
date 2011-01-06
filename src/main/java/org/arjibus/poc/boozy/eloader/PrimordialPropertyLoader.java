package org.arjibus.poc.boozy.eloader;

import java.io.IOException;
import java.util.Properties;


import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternUtils;



public class PrimordialPropertyLoader { 



    static void loadPrimordialProperties(String resourceLocation) 
        throws IOException {
        if(ResourcePatternUtils.isUrl(resourceLocation)){

            UrlResource resource = 
                new UrlResource(ResourceUtils.getURL(resourceLocation));

            Properties props = new Properties();
            props.load(resource.getInputStream());

            for(String key :props.stringPropertyNames()) {
                System.setProperty(key,props.getProperty(key));
            }

        }

    }
}
