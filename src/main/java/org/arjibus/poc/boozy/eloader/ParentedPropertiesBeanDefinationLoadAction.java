package org.arjibus.poc.boozy.eloader;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.Resource;


public class ParentedPropertiesBeanDefinationLoadAction implements BeanDefinationLoadAction { 

    private String parentName;

    private Resource[] resources;

    public ParentedPropertiesBeanDefinationLoadAction(String defaultParentBean,Resource[] resourceLocations){
	parentName = defaultParentBean;
	resources= resourceLocations;
    }


    public void loadBeanDefination(BeanDefinitionRegistry beanRegistry){

	PropertiesBeanDefinitionReader propertiesReader = new PropertiesBeanDefinitionReader(beanRegistry);

	propertiesReader.setDefaultParentBean(parentName);

	propertiesReader.loadBeanDefinitions(resources);
    }

}
