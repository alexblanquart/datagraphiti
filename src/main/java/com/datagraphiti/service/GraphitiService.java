package com.datagraphiti.service;

import com.datagraphiti.Graphiti;
import com.datagraphiti.utils.Transformer;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

public abstract class GraphitiService<T extends Graphiti> {

	protected TransactionalGraph graph;

	public GraphitiService(TransactionalGraph graph) {
		this.graph = graph;
	}

	public T create() {
		T newInstance = getNewInstance();
		graph.commit();
		return newInstance;
	}

	public void delete(T toDelete) {
		toDelete.delete();
	}

	protected abstract T getNewInstance();

	protected abstract Class<T> getType();

	public Iterable<T> findAll() {
		return Transformer.all(startsFromAll(), graph);
	}

	public T findById(String id) {
		return Transformer.single(startsFromOneWithId(id), graph);
	}

	/**
	 * Index-Graphitid starting point to all instances
	 * 
	 * @return
	 */
	protected GremlinPipeline<Vertex, Vertex> startsFromAll() {
		return new GremlinPipeline<Vertex, Vertex>(graph.getVertices(
				Graphiti.TYPE_PROPERTY, getType().getName()));
	}

	/**
	 * Index-Graphitid starting point to unique instance with specific id
	 * 
	 * @return
	 */
	protected GremlinPipeline<Vertex, Vertex> startsFromOneWithId(String id) {
		return new GremlinPipeline<Vertex, Vertex>(graph.getVertices(
				Graphiti.ID_PROPERTY, id));
	}

}
