package org.arjibus.poc.boozy.eloader;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Properties;


import org.springframework.core.io.Resource;

public class TetraPhaseBootstrapContextLoader
    extends MultiPhasePropertyBootstrapContextLoader { 

    static final String PHASE_FOUR_KEY="parented.bean.definations";


    public BeanDefinationLoadAction createLoadAction(Properties props) throws BootstrapException {
        BeanDefinationLoadAction compositeAction =null;

        TriPhaseBootstrapContextLoader threePhaseLoader = new TriPhaseBootstrapContextLoader();

        BeanDefinationLoadAction threePhaseActions = threePhaseLoader.createLoadAction(props);

	BeanDefinationLoadAction fourPhaseActions = createFourthPhaseLoadActions(props);

	BeanDefinationLoadAction[] compoundActions = { threePhaseActions, fourPhaseActions};

	compositeAction = new CompositeBeanDefinationLoadAction(compoundActions);

        return compositeAction;


    }

    public BeanDefinationLoadAction createFourthPhaseLoadActions(Properties props) throws BootstrapException {

        BeanDefinationLoadAction resultAction =null;

        ArrayList<BeanDefinationLoadAction> loadActions =
            new ArrayList<BeanDefinationLoadAction>();

        String parentPopertiesResourcePairValue =
            props.getProperty(PHASE_FOUR_KEY);

        try{
            if(parentPopertiesResourcePairValue != null){
                String[] pairs = commaSeparator.split(parentPopertiesResourcePairValue);
                for(String parentAndPropertyResource : pairs) {

                    String[] parentAndPropertyResourceElements = equalsSeparator.split(parentAndPropertyResource);

                    if(2 ==parentAndPropertyResourceElements.length) {

                        String parentName = parentAndPropertyResourceElements[0];
                        String resourceLocation= parentAndPropertyResourceElements[1];
                        Resource resource = ResourceLocationUtils.convertLocation(resourceLocation);

                        Resource[] resourceSet = { resource };

                        loadActions.add(new ParentedPropertiesBeanDefinationLoadAction(parentName,resourceSet));
                    }
                    else {
                        //TODO - add some error handling
                    }
                }
                // Create a composite action
                resultAction = new CompositeBeanDefinationLoadAction(loadActions);
            }
        }
        catch(FileNotFoundException e){
            handleException(e);
        }

        return resultAction;
    }


}
