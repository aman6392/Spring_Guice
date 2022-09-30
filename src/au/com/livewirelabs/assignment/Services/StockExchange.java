package au.com.livewirelabs.assignment.Services;

import java.math.BigDecimal;
import java.util.Map;

import au.com.livewirelabs.assignment.Entities.InsufficientUnitsException;
import au.com.livewirelabs.assignment.Entities.InvalidCodeException;

//StockExchange interface
public interface StockExchange {
	
	/**
	*sorry but have to create this method as my command line
	*was not functioning, otherwise I would have called buy and sell directly
	*/
	public void trading();
 
	 /**
	 * Buy stock
	 */
	 public void buy(String code, Integer units) throws InsufficientUnitsException, InvalidCodeException;
	 
	 /**
	 * Sell stock
	 */
	 public void sell(String code, Integer units) throws InvalidCodeException;
	 
	 /**
	 * Report aggregate volume available for each code
	 */
	 public Map<String, Integer> getOrderBookTotalVolume();
	 
	 /**
	 * Returns dollar value of trading activity
	 */
	 BigDecimal getTradingCosts(String stExch, String code, int units);
	 
}
