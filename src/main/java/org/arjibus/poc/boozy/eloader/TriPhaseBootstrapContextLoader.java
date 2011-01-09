package org.arjibus.poc.boozy.eloader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.ResourceUtils;



public class TriPhaseBootstrapContextLoader implements BootstrapContextLoader { 

    static final String PHASE_ONE_KEY="primordial.property.resource";
    static final String PHASE_TWO_KEY="xml.reader.resources";
    static final String PHASE_THREE_KEY="parameteized.resource.generator.pairs";
    static final Pattern commaSeparator = Pattern.compile("\\s*,\\s*");
    static final Pattern equalsSeparator = Pattern.compile("\\s*=\\s*");

    public GenericApplicationContext createContext(String[] args)
        throws BootstrapException {
        
        GenericApplicationContext ctx =null;

        if(args != null & (args.length > 0)){
            try {
            ctx = new GenericApplicationContext();
            

            Properties props = loadBootStrapProperties(args[0]);

            firstPhaseLoadPrimordialProperties(props);

            Resource[] xmlResoures =
                secondPhaseGetXmlResources(props);

            Resource[] generatedXmlResources = thirdPhaseGetXmlResources(props);

            setupXmlBeanDefinations(concatResources(xmlResoures,generatedXmlResources),
                                    ctx);
            }
            catch(IOException  e){
                handleException(e);
            }
            catch(BeansException e){
                handleException(e);
            }
            catch(ClassNotFoundException  e){
                handleException(e);
            }
            catch(IllegalAccessException  e){
                handleException(e);
            }
            catch(InstantiationException  e){
                handleException(e);
            }

        }
        
        return ctx;     
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

    protected void firstPhaseLoadPrimordialProperties(Properties props)
        throws IOException {
        String primordialPropertiesResource=
            props.getProperty(PHASE_ONE_KEY);

        if(primordialPropertiesResource != null) {
            PrimordialPropertyLoader.loadPrimordialProperties(primordialPropertiesResource);
        }

    }

    protected Resource[] secondPhaseGetXmlResources(Properties props)
        throws IOException {

        Resource[] resourcesArry =null;

        String xmlReaderResources =
            props.getProperty(PHASE_TWO_KEY);

        if(xmlReaderResources != null) {

            String[] resources = commaSeparator.split(xmlReaderResources);
            ArrayList<Resource> resourceList = new ArrayList<Resource>();

            for(String resource: resources){

                Resource res =ResourceLocationUtils.convertLocation(resource);
                if(res != null){
                    resourceList.add(res);
                }
            }
            resourcesArry = resourceList.toArray(new Resource[resourceList.size()]);

        }


        return resourcesArry;
    }



    protected void setupXmlBeanDefinations(Resource[] resources,
                                           BeanDefinitionRegistry beanRegistry){
        
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanRegistry);
        
        
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        
        xmlReader.setResourceLoader(resourceLoader);
        
        xmlReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
        
        xmlReader.loadBeanDefinitions(resources);


    }


    protected Resource[] concatResources(Resource[] set1,Resource[]set2){

        ArrayList<Resource> resourceList =
            new ArrayList<Resource>();
        
        if(set1 != null) {
            resourceList.addAll(Arrays.asList(set1));
        }

        if(set2 != null) {
            resourceList.addAll(Arrays.asList(set2));
        }

        return resourceList.toArray(new Resource[resourceList.size()]);
    }


    protected Resource[] thirdPhaseGetXmlResources(Properties props) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Resource[] resourcesArry =null;

        String pairsStringList =props.getProperty(PHASE_THREE_KEY);

        String[]  generatorParamPairs =commaSeparator.split(pairsStringList);
        ArrayList<Resource> list = new ArrayList<Resource>();
        for(String pair : generatorParamPairs){

            String[] clazzNameAndParam=equalsSeparator.split(pair);
            if(2== clazzNameAndParam.length ) {
                List<Resource> resources =evaluateClazzNameAndParam(clazzNameAndParam[0],
                                                                    clazzNameAndParam[1]);

                list.addAll(resources); 
            }
        }
        resourcesArry = list.toArray(new Resource[list.size()]);

        return resourcesArry;
    }


    protected List<Resource> evaluateClazzNameAndParam(String clazzName, String param) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        ResourceGenerator rGen = (ResourceGenerator) Class.forName(clazzName).newInstance();
        return Arrays.asList(rGen.getResources(param));
    }

    private void handleException(Exception e) throws BootstrapException {
        throw new BootstrapException(e.getMessage(),e);
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



