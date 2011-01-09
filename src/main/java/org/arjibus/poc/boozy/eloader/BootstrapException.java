package org.arjibus.poc.boozy.eloader;



public class BootstrapException extends Exception { 

    public BootstrapException(){
	super(); 
    }

    public BootstrapException(Throwable ex) { 
	super(ex); 
    }

    public BootstrapException(String message) {
	super(message);
    }


    public BootstrapException(String message,Throwable ex) { 
	super(message,ex);
    }

}
