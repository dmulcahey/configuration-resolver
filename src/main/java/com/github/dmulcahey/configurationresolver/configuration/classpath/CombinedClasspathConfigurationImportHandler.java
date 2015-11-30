package com.github.dmulcahey.configurationresolver.configuration.classpath;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration2.ex.ConfigurationException;

import com.github.dmulcahey.configurationresolver.configuration.classpath.annotation.CombinedClasspathConfigurationResolverPostresolutionActivity;
import com.github.dmulcahey.configurationresolver.configuration.classpath.util.CommonsConfigurationUtil;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClasspathResource;
import com.github.dmulcahey.resolver.ResolutionActivity;
import com.google.common.collect.Maps;

@CombinedClasspathConfigurationResolverPostresolutionActivity
@Slf4j
public class CombinedClasspathConfigurationImportHandler implements ResolutionActivity<Set<CombinedClasspathConfiguration>> {
	
	private static final String IMPORT_KEY = "importConfiguration";
	private static final String DEPRECATED_IMPORT_KEY = "importConfig";

	@Override
	public void perform(Set<CombinedClasspathConfiguration> input) {
		Map<String, CombinedClasspathConfiguration> configurationsByName = Maps.newHashMap();
		for(CombinedClasspathConfiguration configuration : input){
			configurationsByName.put(configuration.getConfigurationDescriptor().getName(), configuration);
		}
		for(CombinedClasspathConfiguration configuration : input){
			for(ClasspathResource resource : configuration.getConfigurationDescriptor().getResources()){
				try {
					List<String> configurationsToImport = CommonsConfigurationUtil.buildConfiguration(resource).getList(String.class, IMPORT_KEY, Collections.<String>emptyList());
					configurationsToImport.addAll(CommonsConfigurationUtil.buildConfiguration(resource).getList(String.class, DEPRECATED_IMPORT_KEY, Collections.<String>emptyList()));
					if(!configurationsToImport.isEmpty()){
						for(String configurationToImport : configurationsToImport){
							if(!configurationsByName.containsKey(configurationToImport)){
								log.error("Error processing configuration import! Attempted to import: {} into {} and {} could not be found!", configurationToImport, configuration.getConfigurationDescriptor().getName(), configurationToImport);
								throw new RuntimeException("Error processing configuration import! Attempted to import: " + configurationToImport + " into " + configuration.getConfigurationDescriptor().getName() + " and " + configurationToImport + " could not be found!");
							}else{
								configuration.importConfiguration(configurationsByName.get(configurationToImport));
							}
						}
					}
				} catch (ConfigurationException e) {
					throw new RuntimeException("Experienced an issue attempting to process a configuration import", e);
				}
			}
		}
	}

	@Override
	public int getOrder() {
		return ResolutionActivity.HIGHEST_PRECEDENCE;
	}

}
