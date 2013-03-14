package com.datagraphiti.model.relation;

import com.datagraphiti.Graphiti;
import com.datagraphiti.utils.Transformer;
import com.tinkerpop.blueprints.Direction;

public class SingleRelation<Related extends Graphiti> extends
		AbstractRelation<Graphiti> {

	public SingleRelation(String label, Graphiti on, Direction direction) {
		super(label, on, direction);
	}

	public void set(Related related) {
		this.addRelation(related);
	}

	public Related get() {
		return Transformer.single(this.getRelated(), on.getGraph());
	}

}
