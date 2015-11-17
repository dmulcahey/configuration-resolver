package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

public interface ExpressionLookupVariableProvider {
	
	String getPrefix();
	
	Object getValueProvider();
	
}
