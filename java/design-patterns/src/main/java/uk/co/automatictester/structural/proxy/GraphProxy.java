package uk.co.automatictester.structural.proxy;

import java.util.HashMap;
import java.util.Map;

public class GraphProxy implements Graph {

    private BaseGraph graph = new BaseGraph();
    private Map<String, Integer> shortestPaths = new HashMap<>();

    @Override
    public int shortestPathCost(String path) {
        if (!shortestPaths.containsKey(path)) {
            int cost = graph.shortestPathCost(path);
            shortestPaths.put(path, cost);
        }
        return shortestPaths.get(path);
    }
}
