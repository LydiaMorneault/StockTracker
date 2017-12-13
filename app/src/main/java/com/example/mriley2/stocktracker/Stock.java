package com.example.mriley2.stocktracker;

/**
 * Created by Mike on 12/5/17.
 */

public class Stock {

    private int id;
    private String symbol;
    private String companyName;
    private Double latestPrice;
    private Double latestPriceChange;
    private Double priceChangePercentage;

    public Stock(String symbol, String companyName){
        id = 0;
        this.symbol = symbol;
        this.companyName = companyName;
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getSymbol(){
        return symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }



}
