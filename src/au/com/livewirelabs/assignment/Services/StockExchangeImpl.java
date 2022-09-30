package au.com.livewirelabs.assignment.Services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import au.com.livewirelabs.assignment.Entities.Counter;
import au.com.livewirelabs.assignment.Entities.InsufficientUnitsException;
import au.com.livewirelabs.assignment.Entities.InvalidCodeException;

//StockExchange implementation
public class StockExchangeImpl implements StockExchange {
	
	Counter valCnt = new Counter();
	
	final String sNAB = "NAB";
	final String sCBA = "CBA";
	final String sQAN = "QAN";
	final String sExchnName1 = "ASX";
	final String sExchnName2 = "CSX";
	String stckExchn;
	String trdOpt = "";
	String stCode;
	int stUnits;
	String contInp="y";
	
	Map<String, Integer> orgQnty = getOrderBookTotalVolume();
	Map<String, Integer> updQnty = new HashMap<String, Integer>(orgQnty);
	

 @Override
 public void trading() {
	 
	 //To display the total volume of stocks
	 System.out.println("Total Volume of units available: " + updQnty);
	       
	while(contInp.contentEquals("y")) {
		
		Scanner scSlctExchn = new Scanner(System.in);
		System.out.println("Enter Stock Exchange you want to trade in? (ASX/CSX): ");
		stckExchn = scSlctExchn.next().toUpperCase();
		//System.out.println("Stock Exchange selected: "+ stckExchn);
		
		if(stckExchn.equalsIgnoreCase(sExchnName1)||stckExchn.equalsIgnoreCase(sExchnName2)) {
			Scanner scTrdOpt = new Scanner(System.in);
	 	   	System.out.println("Enter trade option (buy/sell): ");
	 	   	trdOpt = scTrdOpt.next();
	 	   	//System.out.println("Trade option selected: "+ trdOpt);
	 	   	
	 	   	if(trdOpt.equalsIgnoreCase("BUY")) {
	   			Scanner scstCode = new Scanner(System.in);
	   	 	   	System.out.println("Enter stock code (CBA,NAB,QAN): ");
	   	 	   	stCode = scstCode.nextLine().toUpperCase();
	   	 	   	
		   	 	Scanner scstUnits = new Scanner(System.in);
	   	 	   	System.out.println("Enter how much stock units you want to purchase: ");
	   	 	   	stUnits = scstUnits.nextInt();
	   	 	   	
	   	 	   	try {
	   	 	   		buy(stCode.toUpperCase(), stUnits);
	   	 	   		BigDecimal TrdCost = getTradingCosts(stckExchn, stCode, stUnits);
	   	 	   		System.out.println("Trading cost for "+stCode+" with "+stckExchn+" would charge you: "+ TrdCost+"c per trade");
		   	 	   	Scanner scCont = new Scanner(System.in);
		   	   		System.out.println("Do you want to continue trading(y/n):");
		   	   		contInp = scCont.nextLine().toLowerCase();
	   	 	   	}
	   	 	   	catch(InsufficientUnitsException e) {
	   	 	   		System.out.println(e);
	   	 	   	}
	   	 	   	catch(InvalidCodeException e) {
	   	 		   System.out.println(e);
	   	 	   	}	   	 	   	
	   	 	}
	 	   	else if(trdOpt.equalsIgnoreCase("SELL")) {
	   			Scanner scstCode = new Scanner(System.in);
	   	 	   	System.out.println("Enter stock code (CBA,NAB,QAN): ");
	   	 	   	stCode = scstCode.nextLine().toUpperCase();
	   	 	   	
		   	 	Scanner scstUnits = new Scanner(System.in);
	   	 	   	System.out.println("Enter how much stock units you want to sell: ");
	   	 	   	stUnits = scstUnits.nextInt();
	   	 	   	
	   	 	   	try {
	   	 	   		sell(stCode.toUpperCase(), stUnits);
	   	 	   		BigDecimal TrdCost = getTradingCosts(stckExchn, stCode, stUnits);
	   	 	   		System.out.println("Trading cost for "+stCode+" with "+stckExchn+" would charge you: "+ TrdCost+"c per trade");
		   	 	   	Scanner scCont = new Scanner(System.in);
		   	   		System.out.println("Do you want to continue trade(y/n):");
		   	   		contInp = scCont.nextLine().toLowerCase();
	   	 	   	}
	   	 	   	catch(InvalidCodeException e) {
	   	 		   System.out.println(e);
	   	 	   	}
	   	 	}
		}
	 } 	
 }

@Override
public void buy(String code, Integer units) throws InsufficientUnitsException, InvalidCodeException {
	
	int updTotQtyCBA = valCnt.getValueCBA();
	int updTotQtyNAB = valCnt.getValueNAB();
	int updTotQtyQAN = valCnt.getValueQAN();
	int updUnitVol = 0;
	
	String s = code.substring(0, code.length());
	
	if(s==sCBA||s==sNAB||s==sQAN)//(!code.equals("QAN")))// || !code.contains(sQAN))
	{
		throw new InvalidCodeException("Entered Code is invalid!! Please enter a valid stock code");
	}
	
	if(s.equals(sCBA) && units.intValue()<=updTotQtyCBA) {
		valCnt.setValueCBA(updTotQtyCBA-units.intValue());
		updUnitVol = valCnt.getValueCBA();
	}
	else if(s.equals(sNAB)&&units.intValue()<=updTotQtyNAB) {
		valCnt.setValueNAB(updTotQtyNAB-units.intValue());
		updUnitVol = valCnt.getValueNAB();
	}
	else if(s.equals(sQAN)&&units.intValue()<=updTotQtyQAN) {
		valCnt.setValueQAN(updTotQtyQAN-units.intValue());
		updUnitVol = valCnt.getValueQAN();
	}
	else
	{
		throw new InsufficientUnitsException("Insufficient units available to buy!!");
	}
	
	//Display stock code selected and units desired to buy
	//System.out.println("stock code is: "+ code + ", Units bought: "+units);
	
	//update the record for the entered stock code and units	
	if(updUnitVol>=0)
		{
		updQnty.put(code, updUnitVol);
		//valCnt.setValueCBA(updUnitVol);
		//updTotQtyCBA = valCnt.getValueCBA();
		System.out.println("Updated total volume of units available: "+updQnty);
	}	
}

@Override
public void sell(String code, Integer units) throws InvalidCodeException {
	
	//Counter updCnt = new Counter();
	int updTotQtyCBA = valCnt.getValueCBA();
	int updTotQtyNAB = valCnt.getValueNAB();
	int updTotQtyQAN = valCnt.getValueQAN();
	int updUnitVol = 0;
	
	String s = code.substring(0, code.length());
	
	if(s==sCBA||s==sNAB||s==sQAN)
	{
		throw new InvalidCodeException("Entered Code is invalid!! Please enter a valid stock code");
	}
	
	if(s.equals(sCBA)) {
		valCnt.setValueCBA(updTotQtyCBA+units);
		updUnitVol = valCnt.getValueCBA();
	}
	else if(s.equals(sNAB)) {
		valCnt.setValueNAB(updTotQtyNAB+units);
		updUnitVol = valCnt.getValueNAB();
	}
	else if(s.equals(sQAN)) {
		valCnt.setValueQAN(updTotQtyQAN+units);
		updUnitVol = valCnt.getValueQAN();
	}
	
	//Display stock code selected and units desired to buy
	//System.out.println("stock code is: "+ code + ", Units sold: "+units);
	
	//update the record for the entered stock code and units	
		updQnty.put(code, updUnitVol);
		System.out.println("Updated total volume of units available: "+updQnty);
}

@Override
public Map<String, Integer> getOrderBookTotalVolume() {
	
	Map<String, Integer> TotQnty = new HashMap<String, Integer>();
	TotQnty.put(sCBA, valCnt.getValueCBA());
	TotQnty.put(sNAB, valCnt.getValueNAB());
	TotQnty.put(sQAN, valCnt.getValueQAN());
	
	return TotQnty;
}

@Override
public BigDecimal getTradingCosts(String stExch, String code, int units) {
	
	BigDecimal asxChrg = BigDecimal.valueOf(0.07);
	BigDecimal csxChrg = BigDecimal.valueOf(0.05);
	BigDecimal trdPrice = BigDecimal.ZERO;
	
	if (stExch.contentEquals("ASX")) {
		trdPrice = asxChrg;
	}
	else if (stExch.contentEquals("CSX")) {
		trdPrice = csxChrg;
	}
	
	return trdPrice;
}
}