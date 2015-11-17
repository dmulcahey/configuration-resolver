package com.github.dmulcahey.configurationresolver.configuration.lookup;

import java.util.Set;

import com.github.dmulcahey.resolver.ResolutionTest;
import com.github.dmulcahey.resolver.ResolutionTestResult;
import com.google.common.collect.Sets;

@ConfigurationLookupResolverPostresolutionTest
public class ConfigurationLookupResolverDuplicateRegistrationPostResolutionTest implements ResolutionTest<Set<ConfigurationLookup>> {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public ResolutionTestResult execute(Set<ConfigurationLookup> configurationLookups) {
		ResolutionTestResult resolutionTestResult = new ResolutionTestResult();
		
		if(configurationLookups == null || configurationLookups.isEmpty()){
			resolutionTestResult.setInformationMessage("No configuration lookup instances were registered");
		}else{
			StringBuilder errorMessage = new StringBuilder();
			Set<String> prefixes = Sets.newHashSetWithExpectedSize(configurationLookups.size());
			for(ConfigurationLookup configurationLookup : configurationLookups){
				if(prefixes.contains(configurationLookup.getPrefix())){
					errorMessage.append(configurationLookup.getPrefix());
					errorMessage.append(" has been registered already. Please check the classpath for unintended dependencies or ensure that you didn't use a prefix that is already in use!\n");
				}else{
					prefixes.add(configurationLookup.getPrefix());
				}
			}
			if(errorMessage.length() > 0){
				resolutionTestResult.setErrorMessage(errorMessage.toString());
			}
		}
		return resolutionTestResult;
	}

}
