package org.arjibus.poc.boozy.eloader;



public class FileUrlEtcResourceGenerator extends BaseResourceGenerator { 

    static final private String baseUrl ="file:etc/";
    static final private String suffix=".xml";


    protected String buildLocation(String baseName) {
	return baseUrl+ baseName.trim() + suffix;
    }
    

    static public FileUrlEtcResourceGenerator getResourceGenerator(){
	return new FileUrlEtcResourceGenerator();
    }

}
