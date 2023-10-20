import React from "react";
import StockData from "../../components/Stocks/StockData";
import StockSearchBox from "../../components/Stocks/StockSearchBox";
import { useParams } from "react-router-dom";

function Stocks() {
  const { stockSymbol } = useParams();

  return stockSymbol ? <StockData stockSymbol={ stockSymbol } /> : <StockSearchBox />;
};


export default Stocks;