package com.bms.configurationresolver.configuration.combined;

import java.util.Set;
import java.util.logging.Logger;

import lombok.SneakyThrows;

import org.junit.Test;

import com.bms.configurationresolver.configuration.ConfigurationDescriptorResolver;
import com.bms.configurationresolver.configuration.FileBasedConfiguration;
import com.bms.configurationresolver.configuration.classpath.CombinedClasspathConfiguration;
import com.bms.configurationresolver.configuration.classpath.CombinedClasspathConfigurationResolver;
import com.bms.configurationresolver.resources.classpath.ClassPath;
import com.bms.configurationresolver.resources.classpath.ClasspathResource;
import com.bms.configurationresolver.resources.classpath.FilteredClasspathResourceResourceProvider;
import com.bms.configurationresolver.resources.classpath.filter.ExtensionFilter;
import com.bms.configurationresolver.resources.classpath.filter.NotFilter;
import com.bms.configurationresolver.resources.classpath.filter.PathFilter;
import com.google.common.collect.Sets;

public class CombinedClasspathConfigurationResolverTest {

	
	@Test
	@SneakyThrows
	public void testCombinedClasspathConfigurationResolver(){
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
		
		CombinedClasspathConfigurationResolver combinedClasspathConfigurationResolver = new CombinedClasspathConfigurationResolver(combinedConfigurationDescriptorResolver);
		
		Set<CombinedClasspathConfiguration> combinedClasspathConfigurations = combinedClasspathConfigurationResolver.resolve(resourceProviders);
		
		for(FileBasedConfiguration<ClasspathResource> combinedClasspathConfiguration : combinedClasspathConfigurations){
			Logger.getAnonymousLogger().info(combinedClasspathConfiguration.toString());
		}
	}
	
}
