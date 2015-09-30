package com.bms.configurationresolver.configuration.classpath;

import java.util.Set;

import com.bms.configurationresolver.configuration.AbstractConfigurationResolver;
import com.bms.configurationresolver.configuration.ConfigurationDescriptor;
import com.bms.configurationresolver.configuration.ConfigurationDescriptorResolver;
import com.bms.configurationresolver.configuration.FileBasedConfiguration;
import com.bms.configurationresolver.resources.ResourceProvider;
import com.bms.configurationresolver.resources.classpath.ClasspathResource;
import com.google.common.collect.Sets;

public abstract class AbstractClasspathConfigurationResolver<T extends FileBasedConfiguration<ClasspathResource>> extends AbstractConfigurationResolver<ClasspathResource, T>{

	public AbstractClasspathConfigurationResolver(ConfigurationDescriptorResolver<ClasspathResource> configurationDescriptorResolver) {
		super(configurationDescriptorResolver);
	}
	
	protected abstract T resolveClasspathPropertiesConfiguration(ConfigurationDescriptor<ClasspathResource> configurationDescriptor) throws Exception;
	
	@Override
	protected Set<T> doResolution(Set<? extends ResourceProvider<Set<ClasspathResource>>> input) {
		Set<T> combinedClasspathConfigurations = Sets.newHashSet();
		Set<ConfigurationDescriptor<ClasspathResource>> configurationDescriptors = this.getConfigurationDescriptorResolver().resolve(input);
		for(ConfigurationDescriptor<ClasspathResource> configurationDescriptor : configurationDescriptors){
			try {
				combinedClasspathConfigurations.add(resolveClasspathPropertiesConfiguration(configurationDescriptor));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return combinedClasspathConfigurations;
	}

}
