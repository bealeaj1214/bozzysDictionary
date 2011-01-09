package org.arjibus.poc.boozy.eloader;



import org.apache.log4j.Logger;



public class ClasspathEtcResourceGenerator extends BaseResourceGenerator { 

    static final private String baseUrl ="classpath:etc/";
    static final private String suffix=".xml";

    static Logger logger = Logger.getLogger(ClasspathEtcResourceGenerator.class);


    protected String buildLocation(String baseName) {
	return baseUrl+ baseName.trim() + suffix;
    }


    static public ClasspathEtcResourceGenerator getResourceGenerator(){
	return new ClasspathEtcResourceGenerator();
    }
}
