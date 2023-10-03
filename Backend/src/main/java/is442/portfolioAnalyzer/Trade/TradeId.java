package is442.portfolioAnalyzer.Trade;

import java.io.Serializable;
import java.security.Timestamp;

import is442.portfolioAnalyzer.Portfolio.Portfolio;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import is442.portfolioAnalyzer.Stock.Stock;


@Embeddable
public class TradeId implements Serializable {
    

    @ManyToOne
    @JoinColumn (name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn (name = "stock_symbol")
    private Stock stock;

    private Timestamp purchaseDateTime;

    
    public Timestamp getPurchaseDateTime() {
        return purchaseDateTime;
    }


    public Portfolio getPortfolio() {
        return portfolio;
    }


    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }


    public Stock getStock() {
        return stock;
    }


    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public void setPurchaseDateTime(Timestamp purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((portfolio == null) ? 0 : portfolio.hashCode());
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
        result = prime * result + ((purchaseDateTime == null) ? 0 : purchaseDateTime.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TradeId other = (TradeId) obj;
        if (portfolio == null) {
            if (other.portfolio != null)
                return false;
        } else if (!portfolio.equals(other.portfolio))
            return false;
        if (stock == null) {
            if (other.stock != null)
                return false;
        } else if (!stock.equals(other.stock))
            return false;
        if (purchaseDateTime == null) {
            if (other.purchaseDateTime != null)
                return false;
        } else if (!purchaseDateTime.equals(other.purchaseDateTime))
            return false;
        return true;
    }

    
    



}
