package org.arjibus.poc.boozy.eloader;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface BeanDefinationLoadAction { 


    public void loadBeanDefination(BeanDefinitionRegistry beanRegistry);

}
