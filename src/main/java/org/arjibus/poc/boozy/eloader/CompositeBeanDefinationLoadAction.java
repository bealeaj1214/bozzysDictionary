package org.arjibus.poc.boozy.eloader;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.support.BeanDefinitionRegistry;



public class CompositeBeanDefinationLoadAction implements BeanDefinationLoadAction { 

    ArrayList<BeanDefinationLoadAction> actions = new ArrayList<BeanDefinationLoadAction>();


    public CompositeBeanDefinationLoadAction(List<BeanDefinationLoadAction> loadActions){
	actions.addAll(loadActions);
    }

    public CompositeBeanDefinationLoadAction(BeanDefinationLoadAction[] loadActions){
     
	actions.addAll(Arrays.asList(loadActions));
    }


    public void loadBeanDefination(BeanDefinitionRegistry beanRegistry){
	for(BeanDefinationLoadAction action : actions){
	    action.loadBeanDefination(beanRegistry);
	}

    }

}
