package com.datagraphiti.model.relation;

import com.datagraphiti.Graphiti;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class AbstractRelation<Related extends Graphiti> {

	protected String label;
	protected Direction direction;
	protected Graphiti on;

	public AbstractRelation(String label, Graphiti on, Direction direction) {
		this.label = label;
		this.direction = direction;
		this.on = on;
	}

	public void addRelation(Related related) {
		if (Direction.OUT.equals(direction)) {
			on.asVertex().addEdge(label, related.asVertex());
		} else if (Direction.IN.equals(direction)) {
			related.asVertex().addEdge(label, on.asVertex());
		}
	}

	public void removeRelation(Related related) {
		for (final Edge edge : on.asVertex().getEdges(direction, label)) {
			if (null == related
					|| edge.getVertex(direction.opposite()).equals(related)) {
				on.getGraph().removeEdge(edge);
			}
		}
	}

	protected Iterable<Vertex> getRelated() {
		return on.asVertex().query().direction(direction).labels(label)
				.vertices();
	}
}