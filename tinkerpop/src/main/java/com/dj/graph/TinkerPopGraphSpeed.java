package com.dj.graph;

import com.dj.utils.FileReader;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.util.Optional;

/**
 * Created by Darshan on 7/2/2017.
 */
public class TinkerPopGraphSpeed {
	public static void main(String[] args) throws Exception {
		new TinkerPopGraphSpeed().start();
	}

	private TinkerGraph graph;
//	private FileReader fileReader;
	private static final String cobDate = "2017-07-02";
	private String lastAgreementNumber = null;
	private int numTradesInLastAgreement = 0;

	private void start() throws Exception {
//		fileReader = new FileReader("/trades_by_agreement.csv");
		graph = TinkerGraph.open();
//		graph.createIndex(T.id.name(), Vertex.class);
//		graph.createIndex("available", Vertex.class);
//		System.out.println("Indexed keys: " + graph.getIndexedKeys(Vertex.class));
//		System.out.println(graph.features());
		createGraph();
		printMemoryStats();
		searchAgreement();
		updateSingleTradeAvailableToTrue();
		checkTradeInGraph();
		checkAllTradesForSingleAgreement();
		updateAllTradesAsAvailableForSingleAgreement();
		checkAllTradesForSingleAgreement();
	}

	private void createGraph() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		int agreementCounter = 0;
		int tradeCounter = 0;
		FileReader fileReader = new FileReader("/trades_by_agreement.csv");
		while (fileReader.hasNext()) {
			String line = fileReader.next();
			String split[] = line.split(",");
			String agreementNumber = split[1];
			int numberOfTrades = Integer.parseInt(split[0]);
			if ("A1".equals(agreementNumber)) {
				lastAgreementNumber = agreementNumber;
				numTradesInLastAgreement = numberOfTrades;
			}

			Vertex agreement = graph.addVertex(T.id, cobDate + agreementNumber, T.label, "agreement", "cobDate", cobDate, "agreementId", agreementNumber);
			agreementCounter++;
			for (int i = 1; i <= numberOfTrades; i++) {
				String tradeNumber = agreementNumber + "T" + i;
				Vertex trade = graph.addVertex(T.id, cobDate + tradeNumber, T.label, "trade", "cobDate", cobDate, "tradeId", tradeNumber, "available", false);
				agreement.addEdge("depends", trade);
				tradeCounter++;
			}
		}
		stopWatch.stop();
		System.out.println("Create Graph for " + agreementCounter + " agreement and " + tradeCounter + " trades took millis: " + stopWatch.toString());
	}

	private void searchAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		GraphTraversalSource g = graph.traversal();
		Optional<Vertex> agreement = g.V().hasLabel("agreement").hasId(cobDate + "A1").tryNext();
		if (agreement.isPresent()) {
			System.out.println("Agreement found: " + agreement);
			System.out.println("Agreement Id found: " + agreement.get().property("agreementId").value());
			System.out.println("CobDate found: " + agreement.get().property("cobDate").value());
		}
		stopWatch.stop();
		System.out.println("Searching agreement took: " + stopWatch.toString());
	}

	private void updateSingleTradeAvailableToTrue() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		GraphTraversalSource g = graph.traversal();
		Optional<Vertex> trade = g.V().hasLabel("trade").hasId(cobDate + "A1T1").tryNext();
		if (trade.isPresent()) {
			System.out.println("Trade status before: " + trade.get().property("available").value());
			trade.get().property("available", true);
		}
		stopWatch.stop();
		System.out.println("Updating single trade took: " + stopWatch.toString());
	}

	private void checkTradeInGraph() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		GraphTraversalSource g = graph.traversal();
		Optional<Vertex> trade = g.V().hasLabel("trade").hasId(cobDate + "A1T1").tryNext();
		if (trade.isPresent()) {
			System.out.println("Trade status in checkTradeInGraph: " + trade.get().property("available").value());
		}
		stopWatch.stop();
		System.out.println("Check single trade took: " + stopWatch.toString());
	}

	private void checkAllTradesForSingleAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		GraphTraversalSource g = graph.traversal();
		GraphTraversal<Vertex, Vertex> out = g.V()
				.hasLabel("agreement")
				.hasId(cobDate + "A1")
				.out("depends");

		int tradeWithAvailableFalse = 0;
		int tradeWithAvailableTrue = 0;
		while (out.hasNext()) {
			Vertex trade = out.next();
			if (!(boolean)trade.property("available").value()) {
				tradeWithAvailableFalse++;
			} else {
				tradeWithAvailableTrue++;
			}
		}
		stopWatch.stop();
		System.out.println("Traversing out relationships of A1 to find all available,not ("
				+ tradeWithAvailableTrue + "," + tradeWithAvailableFalse + ") trades: " + stopWatch.toString());
	}

	private void updateAllTradesAsAvailableForSingleAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		GraphTraversalSource g = graph.traversal();

		for (int i = 1; i <= numTradesInLastAgreement; i++) {
			String tradeNumber = lastAgreementNumber + "T" + i;
			Optional<Vertex> trade = g.V().hasLabel("trade").hasId(cobDate + tradeNumber).tryNext();
			if (trade.isPresent()) {
				trade.get().property("available", true);
			}
		}
		stopWatch.stop();
		System.out.println("Updating " + numTradesInLastAgreement + " trades for A1 took: " + stopWatch.toString());
	}

	private void printMemoryStats() throws Exception {
		int mb = 1024*1024;

		//Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics [MB] #####");

		//Print used memory
		System.out.println("Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		//Print free memory
		System.out.println("Free Memory:"
				+ runtime.freeMemory() / mb);

		//Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		//Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}
}
