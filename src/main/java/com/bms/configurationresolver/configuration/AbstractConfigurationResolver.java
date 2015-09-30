package com.bms.configurationresolver.configuration;

import java.util.Set;

import com.bms.configurationresolver.resources.AbstractResource;
import com.bms.configurationresolver.resources.ResourceProvider;
import com.bms.resolver.AbstractResolver;

public abstract class AbstractConfigurationResolver<R extends AbstractResource, C extends Configuration<R>> extends AbstractResolver<Set<? extends ResourceProvider<Set<R>>>, Set<C>> {
	
	private ConfigurationDescriptorResolver<R> configurationDescriptorResolver;

	public AbstractConfigurationResolver(ConfigurationDescriptorResolver<R> configurationDescriptorResolver) {
		super();
		this.configurationDescriptorResolver = configurationDescriptorResolver;
	}

	public ConfigurationDescriptorResolver<R> getConfigurationDescriptorResolver() {
		return configurationDescriptorResolver;
	}

	public void setConfigurationDescriptorResolver(ConfigurationDescriptorResolver<R> configurationDescriptorResolver) {
		this.configurationDescriptorResolver = configurationDescriptorResolver;
	}

}
