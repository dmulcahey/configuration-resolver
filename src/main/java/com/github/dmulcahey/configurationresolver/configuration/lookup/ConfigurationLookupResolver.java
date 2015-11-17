package com.github.dmulcahey.configurationresolver.configuration.lookup;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

import lombok.SneakyThrows;

import com.github.dmulcahey.resolver.AbstractResolver;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

public class ConfigurationLookupResolver extends AbstractResolver<Object, Set<ConfigurationLookup>> {

	@Override
	@SneakyThrows
	protected Set<ConfigurationLookup> doResolution(Object input) {
		Set<Class<? extends ConfigurationLookup>> configurationLookupClasses = getSubTypesOf(ConfigurationLookup.class);
		if(configurationLookupClasses == null || configurationLookupClasses.size() == 0){
			return Collections.emptySet();
		}
		Set<ConfigurationLookup> configurationLookups = Sets.newHashSetWithExpectedSize(configurationLookupClasses.size());
		for(Class<? extends ConfigurationLookup> configurationLookupClass : configurationLookupClasses){
			configurationLookups.add(configurationLookupClass.newInstance());
		}
		return configurationLookups;
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPostresolutionTestAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(ConfigurationLookupResolverPostresolutionTest.class);
	}

}
