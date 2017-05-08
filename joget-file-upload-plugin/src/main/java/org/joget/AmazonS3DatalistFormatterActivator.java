package org.joget;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Darshan on 12/17/2016.
 */
public class AmazonS3DatalistFormatterActivator implements BundleActivator {

    protected Collection<ServiceRegistration> registrations;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registrations = new ArrayList<>();
        registrations.add(bundleContext.registerService(AmazonS3DatalistFormatter.class.getName(), new AmazonS3DatalistFormatter(), null));
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        for (ServiceRegistration registration : this.registrations) {
            registration.unregister();
        }
    }
}
