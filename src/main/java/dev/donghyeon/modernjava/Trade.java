package dev.donghyeon.modernjava;


public class Trade {
    @Override
    public String toString() {
        return this.tradeId;
    }

    public Trade(String tradeId, String trader, double notional, String currency, String region) {
        this.tradeId = tradeId;
        this.trader = trader;
        this.notional = notional;
        this.currency = currency;
        this.region = region;
    }

    private String tradeId;
    private String trader;
    private double notional;
    private String currency;
    private String region;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public double getNotional() {
        return notional;
    }

    public void setNotional(double notional) {
        this.notional = notional;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
