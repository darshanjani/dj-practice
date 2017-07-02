package com.dj.graph;

import com.dj.model.Node;
import com.dj.model.NodeType;
import com.dj.utils.FileReader;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Darshan on 7/2/2017.
 */
public class CustomGraphSpeed {
	public static void main(String[] args) throws Exception {
		new CustomGraphSpeed().start();
	}

	private Graph graph;
//	private FileReader fileReader;
	private static final String cobDate = "2017-07-02";
	private String lastAgreementNumber = null;
	private int numTradesInLastAgreement = 0;

	private void start() throws Exception {
//		fileReader = new FileReader("/trades_by_agreement.csv");
		graph = Graph.open();

		createGraph();
		printMemoryStats();
		searchAgreement();
		updateSingleTradeAvailableToTrue();
		checkTradeInGraph();
		checkAllTradesForSingleAgreement();
		updateAllTradesAsAvailableForSingleAgreement();
		checkAllTradesForSingleAgreement();
		findAgreementForGivenTrade();
		createAllThenUpdateThenTestEachAgreement();
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

			Node agreement = new Node(cobDate, NodeType.Agreement.name(), agreementNumber);
			agreementCounter++;
			for (int i = 1; i <= numberOfTrades; i++) {
				String tradeNumber = agreementNumber + "T" + i;
				Node trade = new Node(cobDate, NodeType.Trade.name(), tradeNumber);
				agreement.addEdge("depends", trade);
				graph.addVertex(trade);
				tradeCounter++;
			}
			graph.addVertex(agreement);
		}
		stopWatch.stop();
		System.out.println("Create Graph for " + agreementCounter + " agreement and " + tradeCounter + " trades took millis: " + stopWatch.toString());
	}

	private void searchAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<Node> agreement = graph.getNode(cobDate, NodeType.Agreement.name(), "A1");
		if (agreement.isPresent()) {
			System.out.println("Agreement found: " + agreement);
			System.out.println("Agreement Id found: " + agreement.get().getId());
			System.out.println("CobDate found: " + agreement.get().getCobDate());
		}
		stopWatch.stop();
		System.out.println("Searching agreement took: " + stopWatch.toString());
	}

	private void updateSingleTradeAvailableToTrue() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<Node> trade = graph.getNode(cobDate, NodeType.Trade.name(), "A1T1");
		if (trade.isPresent()) {
			System.out.println("Trade status before: " + trade.get().isAvailable());
			trade.get().setAvailable(true);
		}
		stopWatch.stop();
		System.out.println("Updating single trade took: " + stopWatch.toString());
	}

	private void checkTradeInGraph() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<Node> trade = graph.getNode(cobDate, NodeType.Trade.name(), "A1T1");
		if (trade.isPresent()) {
			System.out.println("Trade status in checkTradeInGraph: " + trade.get().isAvailable());
		}
		stopWatch.stop();
		System.out.println("Check single trade took: " + stopWatch.toString());
	}

	private void checkAllTradesForSingleAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<Node> agreement = graph.getNode(cobDate, NodeType.Agreement.name(), "A1");
		long tradeWithAvailableFalse = 0;
		long tradeWithAvailableTrue = 0;

		if (agreement.isPresent()) {
			tradeWithAvailableFalse = agreement.get().out("depends").filter(node -> !node.isAvailable()).count();
			tradeWithAvailableTrue = agreement.get().out("depends").filter(node -> node.isAvailable()).count();
		}
		stopWatch.stop();
		System.out.println("Traversing out relationships of A1 to find all available,not ("
				+ tradeWithAvailableTrue + "," + tradeWithAvailableFalse + ") trades: " + stopWatch.toString());
	}

	private void updateAllTradesAsAvailableForSingleAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (int i = 1; i <= numTradesInLastAgreement; i++) {
			String tradeNumber = lastAgreementNumber + "T" + i;
			Optional<Node> trade = graph.getNode(cobDate, NodeType.Trade.name(), tradeNumber);
			if (trade.isPresent()) {
				trade.get().setAvailable(true);
			}
		}
		stopWatch.stop();
		System.out.println("Updating " + numTradesInLastAgreement + " trades for A1 took: " + stopWatch.toString());
	}

	private void findAgreementForGivenTrade() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Optional<Node> trade = graph.getNode(cobDate, NodeType.Trade.name(), "A1T1");
		if (trade.isPresent()) {
			trade.get().in("depends").forEach(node -> {
				stopWatch.stop();
				System.out.println("Incoming relationship finding " + node + " took " + stopWatch.toString());
			});
		}
	}

	private void createAllThenUpdateThenTestEachAgreement() throws Exception {
		graph.clear();
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

			Node agreement = new Node(cobDate, NodeType.Agreement.name(), agreementNumber);
			agreementCounter++;
			for (int i = 1; i <= numberOfTrades; i++) {
				String tradeNumber = agreementNumber + "T" + i;
				Node trade = new Node(cobDate, NodeType.Trade.name(), tradeNumber);
				agreement.addEdge("depends", trade);
				graph.addVertex(trade);
				tradeCounter++;
			}
			graph.addVertex(agreement);
		}
		fileReader.close();

		fileReader = new FileReader("/trades_by_agreement.csv");
		long agreementReadyCheckTime = 0;
		long tradeUpdateTime = 0;
		long numberOfAgreementsReady = 0;
		int tradesIterated = 0;
		System.out.println(new Date() + ":: Trades iterated: " + tradesIterated);
		while (fileReader.hasNext()) {
			String line = fileReader.next();
			String split[] = line.split(",");
			String agreementNumber = split[1];
			int numberOfTrades = Integer.parseInt(split[0]);

			for (int i = 1; i <= numberOfTrades; i++) {
				tradesIterated++;
				String tradeNumber = agreementNumber + "T" + i;
				long startTradeUpdate = System.currentTimeMillis();
				Optional<Node> trade = graph.getNode(cobDate, NodeType.Trade.name(), tradeNumber);
				trade.get().setAvailable(true);
				long endTradeUpdate = System.currentTimeMillis();
				tradeUpdateTime += (endTradeUpdate - startTradeUpdate);

				long start = System.currentTimeMillis();
				if (isAgreementReady(cobDate, agreementNumber)) {
					if (numberOfTrades > 100000) {
						System.out.println(new Date() + ":: Agreement ready: " + agreementNumber);
					}
					numberOfAgreementsReady++;
				}
				long end = System.currentTimeMillis();
				agreementReadyCheckTime += (end - start);
				if (tradesIterated % 100000 == 0) {
					System.out.println(new Date() + ":: Trades iterated: " + tradesIterated);
				}
			}
		}
		fileReader.close();
		stopWatch.stop();
		System.out.printf(
					"Agreements added: %s, " +
					"Trades added: %s, " +
					"Trade update time: %s, " +
					"Agreement check time: %s," +
					"Agreements Ready: %s, " +
					"Total time: %s " +
					"%n",
			agreementCounter, tradeCounter, tradeUpdateTime, agreementReadyCheckTime, numberOfAgreementsReady, stopWatch.toString()
		);
	}

	private boolean isAgreementReady(String cobDate, String id) {
//		return graph.getNode(cobDate, NodeType.Agreement.name(), id)
//				.get()
//				.out()
//				.filter(node -> !node.isAvailable())
//				.count() == 0;
//		return graph.getNode(cobDate, NodeType.Agreement.name(), id)
//				.get().isReadyToProcess();
		Optional<Node> node = graph.getNode(cobDate, NodeType.Agreement.name(), id).get()
				.out()
				.filter(n -> !n.isAvailable())
				.findFirst();
		return !node.isPresent();
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
