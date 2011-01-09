package org.arjibus.poc.boozy.eloader;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import org.springframework.core.io.Resource;


public abstract class BaseResourceGenerator implements ResourceGenerator { 

    static Pattern regex = Pattern.compile("\\s*,\\s*");

    static Logger logger = Logger.getLogger(BaseResourceGenerator.class);


    public Resource[] getResources(String param) {
	Resource[] results = null;

	if(System.getProperty(param) !=null) {
	    String nameSet = System.getProperty(param);
 
	    String[] baseNames = regex.split(nameSet);
	    if(baseNames.length== 1) {
		try{
		    String singleLoc = buildLocation(baseNames[0]);
		    Resource singleResource = ResourceLocationUtils.convertLocation(singleLoc);
		    Resource[] singleResult = 	{ singleResource };
		    results =singleResult;
		}
		catch(FileNotFoundException e){
		    handleFileNotFound(e);
		}
	    }
	    else {
		ArrayList<Resource> urlList =new ArrayList<Resource>();
		String location;
		for( String baseName : baseNames){
		    try{
			location =buildLocation(baseName);
			Resource urlResource = ResourceLocationUtils.convertLocation(location);
			urlList.add(urlResource);
		    }
		    catch(FileNotFoundException e){
			handleFileNotFound(e);
		    }
		}
		results =urlList.toArray(new Resource[baseNames.length]);
	    }
	}
	return results;
    }


    private void handleFileNotFound(FileNotFoundException e){
	logger.info(e.getMessage(),e);
    }

    abstract  protected String buildLocation(String baseName);

}

