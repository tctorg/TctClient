package com.tct;

import com.tct.utilities.Configuration;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;

/**
 * Created by binl on 5/5/2015.
 */
public class TCTApplication extends ResourceConfig {
    public TCTApplication() {
        File path = new File(getClass().getClassLoader().getResource("/").getPath());
        Configuration.load(path.getParent() + "/configuration.properties");
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        packages("com.tct.resource");
    }
}
