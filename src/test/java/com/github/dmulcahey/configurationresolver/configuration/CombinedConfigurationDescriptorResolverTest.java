package com.github.dmulcahey.configurationresolver.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import lombok.SneakyThrows;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;

import com.github.dmulcahey.configurationresolver.configuration.ConfigurationDescriptor;
import com.github.dmulcahey.configurationresolver.configuration.ConfigurationDescriptorResolver;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClasspathResource;
import com.github.dmulcahey.configurationresolver.resources.classpath.FilteredClasspathResourceResourceProvider;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.ExtensionFilter;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.NotFilter;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.PathFilter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CombinedConfigurationDescriptorResolverTest {

	@Test
	@SneakyThrows
	public void testCombinedConfigurationDescriptorResolver(){
		ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
		FilteredClasspathResourceResourceProvider componentResourcesProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(300)
			.withResourceFilter(new PathFilter("ComponentResources/Configuration"))
			.withResourceFilter(new NotFilter(new PathFilter("EnvironmentOverrides")))
			.withResourceFilter(ExtensionFilter.PROPERTIES_FILTER)
			.withClassPath(classPath)
			.build();
		
		FilteredClasspathResourceResourceProvider environmentOverridesResourcesProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(400)
			.withResourceFilter(new PathFilter("ComponentResources/Configuration/EnvironmentOverrides/JUNIT"))
			.withResourceFilter(ExtensionFilter.PROPERTIES_FILTER)
			.withClassPath(classPath)
			.build();
		
		FilteredClasspathResourceResourceProvider environmentResourcesProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(200)
			.withResourceFilter(new PathFilter("EnvironmentResources/JUNIT"))
			.withResourceFilter(ExtensionFilter.PROPERTIES_FILTER)
			.withClassPath(classPath)
			.build();
		
		FilteredClasspathResourceResourceProvider sharedResourcesProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(100)
			.withResourceFilter(new PathFilter("SharedResources"))
			.withResourceFilter(ExtensionFilter.PROPERTIES_FILTER)
			.withClassPath(classPath)
			.build();
		
		FilteredClasspathResourceResourceProvider secureResourcesProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(500)
			.secure(true)
			.withResourceFilter(new PathFilter("SecureResources/JUNIT/Configuration"))
			.withResourceFilter(ExtensionFilter.PROPERTIES_FILTER)
			.withClassPath(classPath)
			.build();
		
		Set<FilteredClasspathResourceResourceProvider> resourceProviders = Sets.newHashSetWithExpectedSize(4);
		resourceProviders.add(componentResourcesProvider);
		resourceProviders.add(secureResourcesProvider);
		resourceProviders.add(sharedResourcesProvider);
		resourceProviders.add(environmentResourcesProvider);
		resourceProviders.add(environmentOverridesResourcesProvider);
		
		ConfigurationDescriptorResolver<ClasspathResource> combinedConfigurationDescriptorResolver = new ConfigurationDescriptorResolver<ClasspathResource>();
		
		Set<ConfigurationDescriptor<ClasspathResource>> configurationDescriptors =  combinedConfigurationDescriptorResolver.resolve(resourceProviders);
		
		assertNotNull(configurationDescriptors);
		
		for(ConfigurationDescriptor<ClasspathResource> configurationDescriptor : configurationDescriptors){
			Logger.getAnonymousLogger().info("\n\n" + configurationDescriptor + "\n\n");
		}
		
	}
	
	@Test
	public void testStringSubstitutor(){
		Map<String,String> variables = Maps.newHashMap();
		variables.put("componentName", "Configuration");
		variables.put("environment", "JUNIT");
		String template = "ComponentResources/${componentName}/EnvironmentOverrides/${environment}";
		String afterSubstitution = StrSubstitutor.replace(template, variables);
		
		assertEquals("ComponentResources/Configuration/EnvironmentOverrides/JUNIT", afterSubstitution);
		
		Logger.getAnonymousLogger().info(template + " -> " + afterSubstitution);
		
	}
	
}
