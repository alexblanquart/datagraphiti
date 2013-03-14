package com.datagraphiti;

import java.util.UUID;

import com.google.common.base.Objects;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Basic class to every domain model. When creating such an object, it is
 * attached to graph and then the modifications are directly reported to the
 * graph. Notice that there is no presumption of any kind on the graph like the
 * indexing or transactions.
 */
public class Graphiti {

	/**
	 * The properties that will be indexed at the end.
	 */
	public static final String TYPE_PROPERTY = "type";
	public static final String ID_PROPERTY = "customId";

	/**
	 * Should not use vertex id but custom one as the vertex id could change
	 * during export/import times or raw graph has the right to handle it as it
	 * wants.
	 */
	protected String customId;

	/**
	 * Direct access to primitives like {@link Graph#addVertex(Object)},
	 * {@link Graph#addEdge(Object, Vertex, Vertex, String)}, etc.
	 */
	protected Graph graph;

	/**
	 * Direct access to primitives like
	 * {@link Vertex#setProperty(String, Object)},
	 * {@link Vertex#removeProperty(String)}, {@link Vertex#query()}
	 */
	private Vertex vertex;

	/**
	 * Used to transform from vertex to object of this class
	 */
	public Graphiti() {

	}

	/**
	 * One must use this constructor to create an instance of this type and
	 * attached it to the graph
	 * 
	 * @param graph
	 */
	public Graphiti(Graph graph) {
		this.setGraph(graph);
		this.setVertex(createVertex());
	}

	/**
	 * Create a new vertex corresponding to this object with all relevant
	 * properties that will be later used to instantiate/find it. This implies
	 * the object to be attached to the graph.
	 * 
	 * @return
	 */
	private Vertex createVertex() {
		Vertex vertex = this.graph.addVertex(null /* no use */);
		vertex.setProperty("customId", UUID.randomUUID().toString());
		vertex.setProperty("type", this.getClass().getName());
		return vertex;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public Vertex asVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	/**
	 * Get id only on demand
	 * 
	 * @return
	 */
	public String getId() {
		if (customId == null) {
			customId = (String) asVertex().getProperty("customId");
		}
		return customId;
	}

	public void delete() {
		graph.removeVertex(asVertex());
	}

	/**
	 * The id is the real unique identifier
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Graphiti other = (Graphiti) obj;
		return Objects.equal(this.getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getId());
	}

	/**
	 * Not using other properties of the vertex like the type to avoid to load
	 * too much properties from graph
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("customId", this.getId())
				.add("vertexId", vertex.getId()).toString();
	}

}
