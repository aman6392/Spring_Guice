package au.com.livewirelabs.assignment.Module;

import com.google.inject.AbstractModule;

import au.com.livewirelabs.assignment.Services.*;

//Binding Module
public class TradingModule extends AbstractModule {

 @Override
 protected void configure() {
    bind(StockExchange.class).to(StockExchangeImpl.class);
 } 
}