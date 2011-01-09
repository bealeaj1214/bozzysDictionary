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
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;



public class TriPhaseBootstrapContextLoader 
    extends MultiPhasePropertyBootstrapContextLoader { 

    static final String PHASE_ONE_KEY="primordial.property.resource";
    static final String PHASE_TWO_KEY="xml.reader.resources";
    static final String PHASE_THREE_KEY="parameteized.resource.generator.pairs";
    static final Pattern commaSeparator = Pattern.compile("\\s*,\\s*");
    static final Pattern equalsSeparator = Pattern.compile("\\s*=\\s*");


   

    public BeanDefinationLoadAction createLoadAction(Properties props) throws BootstrapException {
	BeanDefinationLoadAction compositeAction =null;

	try {

	BeanDefinationLoadAction firstLoadAction =
	    firstPhaseLoadPrimordialProperties(props);

	BeanDefinationLoadAction secondLoadAction =
	    secondPhaseGetLoadAction(props);

	BeanDefinationLoadAction thirdLoadAction = thirdPhaseGetLoadAction(props);

	BeanDefinationLoadAction[] actions = { firstLoadAction,
					       secondLoadAction,
					       thirdLoadAction    };

	compositeAction =  new CompositeBeanDefinationLoadAction(actions) ;

	}
	catch(IOException  e){
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

	return compositeAction;

    }


    protected BeanDefinationLoadAction firstPhaseLoadPrimordialProperties(Properties props)
        throws IOException {
        String primordialPropertiesResource=
            props.getProperty(PHASE_ONE_KEY);

        if(primordialPropertiesResource != null) {
            PrimordialPropertyLoader.loadPrimordialProperties(primordialPropertiesResource);
        }

	return new NullBeanDefinationLoadAction();
    }

    protected BeanDefinationLoadAction secondPhaseGetLoadAction(Properties props)
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

        return new FileXmlBeanDefinationLoadAction(resourcesArry);
    }


    protected BeanDefinationLoadAction thirdPhaseGetLoadAction(Properties props) 
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

        return new FileXmlBeanDefinationLoadAction(resourcesArry);
    }


    protected List<Resource> evaluateClazzNameAndParam(String clazzName, String param) 
        throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        ResourceGenerator rGen = (ResourceGenerator) Class.forName(clazzName).newInstance();
        return Arrays.asList(rGen.getResources(param));
    }


    
}



