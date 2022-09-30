package au.com.livewirelabs.assignment.Request;

import com.google.inject.Inject;

import au.com.livewirelabs.assignment.Services.*;

public class AssignmentInj {
	
	private StockExchange stockExchange;

   @Inject
   public AssignmentInj(StockExchange stockExchange) {
      this.stockExchange = stockExchange;
   }

   public void trade(){
	   stockExchange.trading();
   }
}