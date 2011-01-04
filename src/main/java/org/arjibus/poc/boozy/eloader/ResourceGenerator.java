package org.arjibus.poc.boozy.eloader;

import org.springframework.core.io.Resource;

public interface ResourceGenerator { 

    Resource[] getResources(String param);

}
