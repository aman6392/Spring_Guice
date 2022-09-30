package au.com.livewirelabs.assignment.Main;
import com.google.inject.Guice;
import com.google.inject.Injector;

import au.com.livewirelabs.assignment.Module.TradingModule;
import au.com.livewirelabs.assignment.Request.AssignmentInj;

public class Assignment {
	
   public static void main(String[] args) {
	   
      Injector injector = Guice.createInjector(new TradingModule());
      AssignmentInj assignmentInj = injector.getInstance(AssignmentInj.class);
      assignmentInj.trade();      
   } 
}