package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

import java.util.Set;

import com.github.dmulcahey.resolver.ResolutionTest;
import com.github.dmulcahey.resolver.ResolutionTestResult;
import com.google.common.collect.Sets;

@ExpressionLookupVariableProviderResolverPostresolutionTest
public class ExpressionLookupVariableProviderResolverDuplicateRegistrationPostResolutionTest implements ResolutionTest<Set<ExpressionLookupVariableProvider>>{

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public ResolutionTestResult execute(Set<ExpressionLookupVariableProvider> expressionLookupVariableProviders) {
		ResolutionTestResult resolutionTestResult = new ResolutionTestResult();
		
		if(expressionLookupVariableProviders == null || expressionLookupVariableProviders.isEmpty()){
			resolutionTestResult.setInformationMessage("No expression lookup variable provider instances were registered");
		}else{
			StringBuilder errorMessage = new StringBuilder();
			Set<String> prefixes = Sets.newHashSetWithExpectedSize(expressionLookupVariableProviders.size());
			for(ExpressionLookupVariableProvider expressionLookupVariableProvider : expressionLookupVariableProviders){
				if(prefixes.contains(expressionLookupVariableProvider.getPrefix())){
					errorMessage.append(expressionLookupVariableProvider.getPrefix());
					errorMessage.append(" has been registered already. Please check the classpath for unintended dependencies or ensure that you didn't use a prefix that is already in use!\n");
				}else{
					prefixes.add(expressionLookupVariableProvider.getPrefix());
				}
			}
			if(errorMessage.length() > 0){
				resolutionTestResult.setErrorMessage(errorMessage.toString());
			}
		}
		return resolutionTestResult;
	}

}
