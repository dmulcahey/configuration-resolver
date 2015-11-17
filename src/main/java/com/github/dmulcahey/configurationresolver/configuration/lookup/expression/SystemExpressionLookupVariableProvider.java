package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

public class SystemExpressionLookupVariableProvider implements ExpressionLookupVariableProvider {

	@Override
	public String getPrefix() {
		return "System";
	}

	@Override
	public Object getValueProvider() {
		return "Class:java.lang.System";
	}

}
