package org.arjibus.poc.boozy.eloader;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import org.springframework.core.io.Resource;

import org.springframework.util.ResourceUtils;
import org.springframework.core.io.UrlResource;


public class ClasspathEtcResourceGenerator implements ResourceGenerator { 

    static final private String baseUrl ="classpath:etc/";
    static final private String suffix=".xml";

    static Pattern regex = Pattern.compile("\\s*,\\s*");

    static Logger logger = Logger.getLogger(ClasspathEtcResourceGenerator.class);

    public Resource[] getResources(String param) {
	Resource[] results = null;

	if(System.getProperty(param) !=null) {
	    
	    String nameSet = System.getProperty(param);
 
	    String[] baseNames = regex.split(nameSet);
	    if(baseNames.length== 1) {
		try{
		    String singleLoc = buildLocation(baseNames[0]);

		    Resource[] singleResult = 
			{ new UrlResource(ResourceUtils.getURL(singleLoc))	};

		    results =singleResult;
		}
		catch(FileNotFoundException e){
		    handleFileNotFound(e);
		}
	    }
	    else {
		ArrayList<UrlResource> urlList =new ArrayList<UrlResource>();
		String location;
		for( String baseName : baseNames){
		    try{
			location =buildLocation(baseName);
			UrlResource urlResource =
			    new UrlResource(ResourceUtils.getURL(location));
			urlList.add(urlResource);
		    }
		    catch(FileNotFoundException e){
			handleFileNotFound(e);
		    }
		}
		results =urlList.toArray(new UrlResource[baseNames.length]);
	    }


	}
	return results;
    }

    private String buildLocation(String baseName) {

	return baseUrl+ baseName.trim() + suffix;
    }

    private void handleFileNotFound(FileNotFoundException e){
	//
	logger.info(e.getMessage(),e);
    }

    static public ClasspathEtcResourceGenerator getResourceGenerator(){
	return new ClasspathEtcResourceGenerator();
    }
}
