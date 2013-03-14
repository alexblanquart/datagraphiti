package com.datagraphiti.model.property;

import com.datagraphiti.Graphiti;

public class Property<T> {

	private String property;
	private Graphiti on;

	public Property(String property, Graphiti on) {
		this.property = property;
		this.on = on;
	}

	public void set(T value) {
		if (null == value) {
			on.asVertex().removeProperty(property);
		} else {
			on.asVertex().setProperty(property, value);
		}
	}

	public T get() {
		return on.asVertex().getProperty(property);
	} 

}
