package com.dj.controller;

import com.dj.model.Response;
import com.dj.model.StatsByType;
import com.dj.model.Trade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/angular")
public class AngularController {

	@GetMapping("/single")
	public Response getSingleObjectData() {
		StatsByType stats = new StatsByType();
		stats.setPending((int)(Math.random() * 8000));
		stats.setCompleted((int)(Math.random() * 2000));
		stats.setTotal((int)(Math.random() * 10000));
		return Response.builder().success(true).total(1).data(stats).build();
	}

	@GetMapping("/grid")
	public Response getGridDataArray(
			@RequestParam(required = false) String page,
			@RequestParam(required = false) String limit) {
		int iLimit = 10, iPage = 1;
		if (limit != null && !limit.isEmpty()) {
			iLimit = Integer.parseInt(limit);
		}
		if (page != null && !page.isEmpty()) {
			iPage = Integer.parseInt(page);
		}
		List<Trade> trades = new ArrayList<>();
		for (int i = 0; i < iLimit; i++) {
			Trade trade = new Trade();
			trade.setTradeId("Trade-" + iPage + "-" + i);
			trade.setAgreementId("Agmt-" + iPage + "-" + i);
			trade.setFeedId("Feed-" + iPage + "-" + i);
			trade.setAvailable("Y");
			trades.add(trade);
		}
		return Response.builder().total((int)(Math.random() * 200)).success(true).data(trades).build();
	}
}
