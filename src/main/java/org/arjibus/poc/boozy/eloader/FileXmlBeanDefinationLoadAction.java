package org.arjibus.poc.boozy.eloader;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;


public class FileXmlBeanDefinationLoadAction implements BeanDefinationLoadAction { 


    private Resource[] resources;

    public FileXmlBeanDefinationLoadAction(Resource[] resourceLocations){
	resources= resourceLocations;
    }

    public void loadBeanDefination(BeanDefinitionRegistry beanRegistry){

	XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanRegistry);
	
	
	FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        
        xmlReader.setResourceLoader(resourceLoader);
        
        xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
	
	xmlReader.loadBeanDefinitions(resources);

    }

}
