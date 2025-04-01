package com.rany.cake.toolkit.nebula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SceneGraph {
    private String spaceName;
    private Collection<BaseGraphModel> nodes = new HashSet<>();
    private Collection<BaseGraphModel> edges = new HashSet<>();

    public SceneGraph merge(SceneGraph graph, Boolean isRef) {
        nodes.addAll(graph.nodes);
        edges.addAll(graph.getEdges());
        return this;
    }

    public SceneGraph merge(SceneGraph graph) {
//        nodes = CollUtil.unionDistinct(nodes, graph.getNodes());
//        edges = CollUtil.unionDistinct(edges, graph.getEdges());

        // 合并节点和边



        return this;
    }
}
