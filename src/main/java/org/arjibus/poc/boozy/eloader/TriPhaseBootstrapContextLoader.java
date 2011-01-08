package org.arjibus.poc.boozy.eloader;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Properties;

import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;



public class TriPhaseBootstrapContextLoader implements BootstrapContextLoader { 

    static final String PHASE_ONE_KEY="primordial.property.resource";
    static final String PHASE_TWO_KEY="xml.reader.resources";
    static final String PHASE_THREE_KEY="parameteized.resource.generator.pairs";
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

	    Resource[] generatedXmlResources = null;

	    thirdPhaseGetXmlResources(props);

	    setupXmlBeanDefinations(concatResources(xmlResoures,generatedXmlResources),
				    ctx);

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
	else {
	    FileInputStream is = new FileInputStream(resourceLocation);
	    props = new Properties();
            props.load(is);

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


    protected Resource[] thirdPhaseGetXmlResources(Properties props){
	Resource[] resourcesArry =null;

	String pairsStringList =props.getProperty(PHASE_THREE_KEY);

	String[]  generatorParamPairs =commaSeparator.split(pairsStringList);

	return resourcesArry;
    }


    protected void setupXmlBeanDefinations(Resource[] resources,
					   BeanDefinitionRegistry beanRegistry){
	
	XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanRegistry);
	
	
	FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        
        xmlReader.setResourceLoader(resourceLoader);
        
        xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
	
	xmlReader.loadBeanDefinitions(resources);


    }


    protected Resource[] concatResources(Resource[] set1,Resource[]set2){

	ArrayList<Resource> resourceList =
	    new ArrayList<Resource>();
	
	if(set1 != null) {
	    for(Resource resource:set1){
		resourceList.add(resource);
	    }
	}

	if(set2 != null) {
	   for(Resource resource:set2){
		resourceList.add(resource);
	    } 
	}

	return resourceList.toArray(new Resource[resourceList.size()]);
    }
}


