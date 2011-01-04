package org.arjibus.poc.boozy.eloader.javabeans;



public class Foo { 

    private String value;

    public void setValue(String val){
	value=val;
    }
    public String getValue(){ return value;}

    public String getDescription() {
	return getClass().getSimpleName() +" { value :"+value+"}";

    }

}
