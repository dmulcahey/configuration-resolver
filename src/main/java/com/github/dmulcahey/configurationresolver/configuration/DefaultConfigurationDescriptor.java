package com.github.dmulcahey.configurationresolver.configuration;

import java.util.Set;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;
import com.google.common.collect.Sets;

public class DefaultConfigurationDescriptor<T extends AbstractResource> implements ConfigurationDescriptor<T> {
	
	private String name;
	private Set<T> resources;
	
	public DefaultConfigurationDescriptor(String name) {
		super();
		this.name = name;
	}
	
	public DefaultConfigurationDescriptor() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Set<T> getResources() {
		if(resources == null){
			resources = Sets.newHashSet();
		}
		return resources;
	}

	@Override
	public String toString() {
		return "ConfigurationDescriptor [name=" + name + ", resources="
				+ resources + "]";
	}
	
}
