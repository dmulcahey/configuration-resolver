package com.bms.configurationresolver.configuration;

import com.bms.configurationresolver.resources.AbstractResource;

public interface Configuration<R extends AbstractResource> extends org.apache.commons.configuration2.Configuration {

	ConfigurationDescriptor<R> getConfigurationDescriptor();
	
}
