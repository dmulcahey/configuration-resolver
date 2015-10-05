package com.github.dmulcahey.configurationresolver.configuration;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;

public interface Configuration<R extends AbstractResource> extends org.apache.commons.configuration2.Configuration {

	ConfigurationDescriptor<R> getConfigurationDescriptor();
	
}
