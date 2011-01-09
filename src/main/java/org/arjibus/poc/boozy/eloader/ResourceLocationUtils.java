package org.arjibus.poc.boozy.eloader;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;


public abstract class ResourceLocationUtils { 



    public static Resource convertLocation(String resourceLocation)
	throws FileNotFoundException {
	Resource resource=null;
	if(ResourcePatternUtils.isUrl(resourceLocation)){
	    resource = 
                new UrlResource(ResourceUtils.getURL(resourceLocation));
	}

	return resource;
    }

}
