package com.dj.guava;

import com.dj.graph.Graph;
import com.dj.model.NodeType;
import com.dj.model.Relationship;
import com.dj.utils.FileReader;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Darshan on 7/2/2017.
 */
public class GuavaGraphSpeed {
	public static void main(String[] args) throws Exception {
		new GuavaGraphSpeed().start();
	}

	private MutableValueGraph<Node, String> graph;
//	private FileReader fileReader;
	private static final String cobDate = "2017-07-02";
	private String lastAgreementNumber = null;
	private int numTradesInLastAgreement = 0;
	private NodeRepository nodeRepository;

	private void start() throws Exception {
//		fileReader = new FileReader("/trades_by_agreement.csv");
		graph = ValueGraphBuilder.directed().build();
		nodeRepository = new NodeRepository();

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
			graph.addNode(agreement);
			nodeRepository.put(agreement, agreement);
			agreementCounter++;
			for (int i = 1; i <= numberOfTrades; i++) {
				String tradeNumber = agreementNumber + "T" + i;
				Node trade = new Node(cobDate, NodeType.Trade.name(), tradeNumber);
				graph.addNode(trade);
				nodeRepository.put(trade, trade);
				graph.putEdgeValue(agreement, trade, "depends");
				tradeCounter++;
			}

		}
		stopWatch.stop();
		System.out.println("Create Graph for " + agreementCounter + " agreement and " + tradeCounter + " trades took millis: " + stopWatch.toString());
	}

	private void searchAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Node agreementKey = new Node(cobDate, NodeType.Agreement.name(), "A1");
		if (graph.nodes().contains(agreementKey)) {
			Node agreement = nodeRepository.get(agreementKey);
			System.out.println("Agreement found: " + agreement);
			System.out.println("Agreement Id found: " + agreement.getId());
			System.out.println("CobDate found: " + agreement.getCobDate());
		}
		stopWatch.stop();
		System.out.println("Searching agreement took: " + stopWatch.toString());
	}

	private void updateSingleTradeAvailableToTrue() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Node tradeKey = new Node(cobDate, NodeType.Trade.name(), "A1T1");
		if (graph.nodes().contains(tradeKey)) {
			Node trade = nodeRepository.get(tradeKey);
			System.out.println("Trade status before: " + trade.isAvailable());
			trade.setAvailable(true);
		}
		stopWatch.stop();
		System.out.println("Updating single trade took: " + stopWatch.toString());
	}


	private void checkTradeInGraph() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Node tradeKey = new Node(cobDate, NodeType.Trade.name(), "A1T1");
		if (graph.nodes().contains(tradeKey)) {
			Node trade = nodeRepository.get(tradeKey);
			System.out.println("Trade status in checkTradeInGraph: " + trade.isAvailable());
		}
		stopWatch.stop();
		System.out.println("Check single trade took: " + stopWatch.toString());
	}

	private void checkAllTradesForSingleAgreement() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		long tradeWithAvailableFalse = 0;
		long tradeWithAvailableTrue = 0;

		Node agreementKey = new Node(cobDate, NodeType.Agreement.name(), "A1");
		if (graph.nodes().contains(agreementKey)) {
			tradeWithAvailableFalse = graph.successors(agreementKey).stream().filter(node -> !nodeRepository.get(node).isAvailable()).count();
			tradeWithAvailableTrue = graph.successors(agreementKey).stream().filter(node -> nodeRepository.get(node).isAvailable()).count();
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
			Node tradeKey = new Node(cobDate, NodeType.Trade.name(), tradeNumber);
			if (graph.nodes().contains(tradeKey)) {
				Node trade = nodeRepository.get(tradeKey);
				trade.setAvailable(true);
			}
		}
		stopWatch.stop();
		System.out.println("Updating " + numTradesInLastAgreement + " trades for A1 took: " + stopWatch.toString());
	}


	private void findAgreementForGivenTrade() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Node tradeKey = new Node(cobDate, NodeType.Trade.name(), "A1T1");
		if (graph.nodes().contains(tradeKey)) {
			graph.predecessors(tradeKey).forEach(node -> {
				stopWatch.stop();
				System.out.println("Incoming relationship finding " + node + " took " + stopWatch.toString());
			});
		}
	}

	private void createAllThenUpdateThenTestEachAgreement() throws Exception {
		graph = ValueGraphBuilder.directed().build();
		nodeRepository = new NodeRepository();

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
			graph.addNode(agreement);
			nodeRepository.put(agreement, agreement);
			agreementCounter++;
			for (int i = 1; i <= numberOfTrades; i++) {
				String tradeNumber = agreementNumber + "T" + i;
				Node trade = new Node(cobDate, NodeType.Trade.name(), tradeNumber);
				graph.addNode(trade);
				nodeRepository.put(trade, trade);
				graph.putEdgeValue(agreement, trade, "depends");
				tradeCounter++;
			}
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
				Node trade = nodeRepository.get(new Node(cobDate, NodeType.Trade.name(), tradeNumber));
				trade.setAvailable(true);
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
		Optional<Node> node = graph.successors(new Node(cobDate, NodeType.Agreement.name(), id))
				.stream()
				.filter(n -> !nodeRepository.get(n).isAvailable())
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
