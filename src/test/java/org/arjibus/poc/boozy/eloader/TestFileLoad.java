package org.arjibus.poc.boozy.eloader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.springframework.context.support.GenericApplicationContext;

public class TestFileLoad { 



    static public void main(String[] args){
	
	
	Logger.getRootLogger().setLevel((Level) Level.INFO);
        BasicConfigurator.configure();

	BootstrapContextLoader loader = new TetraPhaseBootstrapContextLoader ();
	try {

	    GenericApplicationContext context =  loader.createContext(args);
	    context.refresh();
	}
	catch(BootstrapException e){
	    e.printStackTrace();
	}
    }

}

