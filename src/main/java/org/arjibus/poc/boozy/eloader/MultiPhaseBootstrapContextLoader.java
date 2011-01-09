package org.arjibus.poc.boozy.eloader;

import java.util.Properties;

import org.springframework.context.support.GenericApplicationContext;

public abstract class MultiPhaseBootstrapContextLoader implements BootstrapContextLoader { 

    public GenericApplicationContext createContext(String[] args)
        throws BootstrapException {
        
        GenericApplicationContext ctx =null;

        if(args != null & (args.length > 0)){
            
	    ctx = new GenericApplicationContext();

	    BeanDefinationLoadAction resultantLoadAction =
		createLoadAction(args);

	    resultantLoadAction.loadBeanDefination(ctx);
		
	}
	return ctx;
    }
	   
    abstract public BeanDefinationLoadAction createLoadAction(String[] args) throws BootstrapException;


    protected void handleException(Exception e) throws BootstrapException {
        throw new BootstrapException(e.getMessage(),e);
    }
}



