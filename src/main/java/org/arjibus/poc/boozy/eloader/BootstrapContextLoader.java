package org.arjibus.poc.boozy.eloader;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.support.GenericApplicationContext;

public interface BootstrapContextLoader { 

    public GenericApplicationContext createContext(String[] args)
	throws BootstrapException;

}
