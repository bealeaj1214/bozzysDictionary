package org.arjibus.poc.boozy.eloader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Properties;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;



/**
 *
 *
 */
public abstract class MultiPhasePropertyBootstrapContextLoader extends MultiPhaseBootstrapContextLoader { 


    abstract public BeanDefinationLoadAction createLoadAction(Properties properties) throws BootstrapException;


    public BeanDefinationLoadAction createLoadAction(String[] args) throws BootstrapException  {
        BeanDefinationLoadAction result = null;
        try {
            
            Properties props = loadBootStrapProperties(args[0]);
            result = createLoadAction(props);
        }
        catch(IOException e){
            handleException(e);
        }
        return result;
    }

    protected Properties loadBootStrapProperties(String resourceLocation) 
        throws IOException{
        
        Properties props = null;
        Resource resource =
            ResourceLocationUtils.convertLocation(resourceLocation);
        
        InputStream inputStream=null;
        
        if(resource != null){
            inputStream=resource.getInputStream();
        }
        else {
            FileInputStream is = new FileInputStream(resourceLocation);
            inputStream=is;
        }
        props=loadPropertiesFromInputStream(inputStream);
        
        return props;
    }


    private Properties loadPropertiesFromInputStream(InputStream inStream)
        throws IOException {
        
        Properties props = new Properties();

        try {
            props.load(inStream);
        }
        finally {
            if(inStream != null){
                inStream.close();
            }
        }
        return props;
    }

}

