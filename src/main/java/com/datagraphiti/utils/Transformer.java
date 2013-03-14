package com.datagraphiti.utils;

import com.datagraphiti.Graphiti;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

public class Transformer {
	
	@SuppressWarnings("unchecked")
	public static <T extends Graphiti> T single(Iterable<Vertex> vertices,
			Graph graph) {
		return (T) transformVertices(vertices, graph).iterator().next();
	}

	public static <T extends Graphiti> Iterable<T> all(
			Iterable<Vertex> vertices, Graph graph) {
		return transformVertices(vertices, graph);
	}

	public static <T extends Graphiti> Iterable<T> transformVertices(
			Iterable<Vertex> vertices, final Graph graph) {
		return new GremlinPipeline<Vertex, Vertex>(vertices)
				.transform(new PipeFunction<Vertex, T>() {

					@SuppressWarnings("unchecked")
					@Override
					public T compute(Vertex vertex) {
						try {
							/*
							 * Instantiation is done dynamically with the "type"
							 * property
							 */
							String type = vertex
									.getProperty(Graphiti.TYPE_PROPERTY);
							T instance = (T) Class.forName(type).newInstance();
							/*
							 * Do not forget to attach instance to graph
							 */
							instance.setGraph(graph);
							instance.setVertex(vertex);
							return instance;
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						return null;
					}

				});
	}
}
