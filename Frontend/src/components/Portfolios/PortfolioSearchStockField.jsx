import React, { useEffect, useState } from "react";
import { getValidStockSymbolsAPI } from "../../api/portfolio";

export default function PortfolioSearchStockField({}) {
  useEffect(() => {
    const getAllValidStockSymbols = async () => {
      const validStockSymbols = await getValidStockSymbolsAPI();
      console.log("validStockSymbols", validStockSymbols);
    };
    getAllValidStockSymbols();
  }, []);

  return <div className="card position-static shadow mb-4"></div>;
}
