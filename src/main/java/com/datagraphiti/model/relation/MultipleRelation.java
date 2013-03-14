package com.datagraphiti.model.relation;

import com.datagraphiti.Graphiti;
import com.datagraphiti.utils.Transformer;
import com.tinkerpop.blueprints.Direction;

public class MultipleRelation <Related extends Graphiti> extends
		AbstractRelation<Graphiti> {

	public MultipleRelation(String label, Graphiti on, Direction direction) {
		super(label, on, direction);
	}

	public void add(Related related) {
		this.addRelation(related);
	}

	public void remove(Related related) {
		this.removeRelation(related);
	}

	public Iterable<Related> getAll() {
		return Transformer.all(getRelated(), on.getGraph());
	}

}

