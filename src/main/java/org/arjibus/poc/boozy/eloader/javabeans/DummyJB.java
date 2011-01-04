package org.arjibus.poc.boozy.eloader.javabeans;



public abstract class DummyJB { 

    private String value;

    public void setValue(String val){
	value=val;
    }
    public String getValue(){ return value;}

    public String getDescription() {
	return getClass().getSimpleName() +" { value :"+value+"}";

    }

}
