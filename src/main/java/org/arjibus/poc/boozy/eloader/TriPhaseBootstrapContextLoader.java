package org.arjibus.poc.boozy.eloader;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;

import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;


public class TriPhaseBootstrapContextLoader implements BootstrapContextLoader { 

    static final String PHASE_ONE_KEY="primordial.property.resource";
    static final String PHASE_TWO_KEY="xml.reader.resources";
    static final String PHASE_THREE_KEY="primordial.property.resource";
    static final Pattern commaSeparator = Pattern.compile("\\s*,\\s*");


    public GenericApplicationContext createContext(String[] args)
	throws IOException, BeansException {
	
	GenericApplicationContext ctx =null;

	if(args != null & (args.length > 0)){
	    ctx = new GenericApplicationContext();
	    

	    Properties props = loadBootStrapProperties(args[0]);

	    firstPhaseLoadPrimordialProperties(props);

	    Resource[] xmlResoures =
		secondPhaseGetXmlResources(props);


	}
	
	return ctx;	
    }

    protected Properties loadBootStrapProperties(String resourceLocation) 
	throws IOException{

	Properties props = null;
	if(ResourcePatternUtils.isUrl(resourceLocation)){

	    UrlResource resource = 
                new UrlResource(ResourceUtils.getURL(resourceLocation));

	    props = new Properties();
            props.load(resource.getInputStream());

	}
	return props;
    }

    protected void firstPhaseLoadPrimordialProperties(Properties props)
	throws IOException {
	String primordialPropertiesResource=
	    props.getProperty(PHASE_ONE_KEY);

	if(primordialPropertiesResource != null) {
	    PrimordialPropertyLoader.loadPrimordialProperties(primordialPropertiesResource);
	}

    }

    protected Resource[] secondPhaseGetXmlResources(Properties props)
	throws IOException {

	Resource[] resourcesArry =null;

	String xmlReaderResources =
	    props.getProperty(PHASE_TWO_KEY);

	if(xmlReaderResources != null) {

	    String[] resources = commaSeparator.split(xmlReaderResources);
	    ArrayList<Resource> resourceList = new ArrayList<Resource>();

		for(String resource: resources){
		    if(ResourcePatternUtils.isUrl(resource)){
			UrlResource res = 
			new UrlResource(ResourceUtils.getURL(resource));
			resourceList.add(res);

		    }
		}
	    resourcesArry = resourceList.toArray(new Resource[resourceList.size()]);

	}

	return resourcesArry;
    }

}
