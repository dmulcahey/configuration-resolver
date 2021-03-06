package com.github.dmulcahey.configurationresolver.configuration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;
import com.github.dmulcahey.configurationresolver.resources.ResourceProvider;
import com.github.dmulcahey.resolver.AbstractResolver;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ConfigurationDescriptorResolver<T extends AbstractResource> extends AbstractResolver<Set<? extends ResourceProvider<Set<T>>>, Set<ConfigurationDescriptor<T>>> {

	@Override
	protected Set<ConfigurationDescriptor<T>> doResolution(Set<? extends ResourceProvider<Set<T>>> input) {
		Set<ConfigurationDescriptor<T>> combinedConfigurationDescriptors = Sets.newHashSet();
		Map<String, Set<T>> allResourcesByName = arrangeResourcesByName(input);
		
		for(Entry<String, Set<T>> resourceSetEntry : allResourcesByName.entrySet()){
			ConfigurationDescriptor<T> combinedConfigurationDescriptor = new DefaultConfigurationDescriptor<T>(resourceSetEntry.getKey());
			combinedConfigurationDescriptor.getResources().addAll(resourceSetEntry.getValue());
			combinedConfigurationDescriptors.add(combinedConfigurationDescriptor);
		}
		
		return combinedConfigurationDescriptors;
	}
	
	private Map<String, Set<T>> arrangeResourcesByName(Set<? extends ResourceProvider<Set<T>>> input){
		Map<String, Set<T>> allResourcesByName = Maps.newHashMap();
		
		for(ResourceProvider<Set<T>> resourceProvider : input){
			for(T resource : resourceProvider.getResources()){
				String name = getResourceName(resource);
				if(!allResourcesByName.containsKey(name)){
					allResourcesByName.put(name, Sets.<T>newHashSet());
				}
				allResourcesByName.get(name).add(resource);
			}
		}
		
		return allResourcesByName;
	}
	
	private String getResourceName(T resource){
		String name = resource.getName();
		if(name.contains(".")){
			name = name.substring(0, name.lastIndexOf("."));
		}
		return name;
	}

}
