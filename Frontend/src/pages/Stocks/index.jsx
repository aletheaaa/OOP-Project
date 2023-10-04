import React from "react";
import StockData from "../../components/Stocks/StockData";
import StockSearch from "../../components/Stocks/StockSearch";
import { useParams } from "react-router-dom";

function Stocks() {
  const { stockSymbol } = useParams();

  return stockSymbol ? <StockData stockSymbol={ stockSymbol } /> : <StockSearch />;
};


export default Stocks;