package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

public class MathExpressionLookupVariableProvider implements ExpressionLookupVariableProvider {

	@Override
	public String getPrefix() {
		return "Math";
	}

	@Override
	public Object getValueProvider() {
		return "Class:java.lang.Math";
	}

}
