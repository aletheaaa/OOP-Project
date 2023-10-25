import axios from "axios";

const BASE_URL = "http://localhost:8080";
let token = sessionStorage.getItem("token");
let userId = sessionStorage.getItem("id");

export async function createPortfolioAPI(requestBody) {
  try {
    // console.log("this is req body", requestBody);
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.post(
      BASE_URL + "/portfolio/createPortfolio/" + userId,
      requestBody,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in createPortfolio API: ");
    console.log(error);
    return error;
  }
}

// TODO: to connect to finalied API
export async function getStockSectorAPI(stockSymbol) {
  try {
    console.log("this is stocksymbol from api", stockSymbol);
    // const config = {
    //   headers: {
    //     Authorization: `Bearer ${token}`,
    //     "Content-Type": "application/json",
    //   },
    // };
    // const response = await axios.get(
    //   BASE_URL + "/getStockSector/" + stockSymbol,
    //   config
    // );
    // return response;
    return "Technology";
  } catch (error) {
    console.log("Error in createPortfolio API: ");
    console.log(error);
    return error;
  }
}

// TODO: to connect to finalied API
export async function getValidStockSymbolsAPI() {
  try {
    // const config = {
    //   headers: {
    //     Authorization: `Bearer ${token}`,
    //     "Content-Type": "application/json",
    //   },
    // };
    // const response = await axios.get(
    //   BASE_URL + "/portfolio/getValidStockSymbols",
    //   config
    // );
    const response = {
      status: 200,
      data: ["AAPL", "MSFT", "GOOG", "AMZN", "FB", "TSLA"],
    };
    return response;
  } catch (error) {
    console.log("Error in getValidStockSymbols API: ");
    console.log(error);
    return error;
  }
}

// TODO: to connect to finalied API
export async function getPortfolioDetailsAPI(portfolioId) {
  return {
    portfolioId: "1",
    name: "Retirement",
    description: "retirement plan",
    totalCapital: 1000,
    Trades: [],
  };
}

// asset allocation for indiv stocks & sectors
export async function getAssetAllocationAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL + "/portfolio/assetsAllocation/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getAssetAllocationBySector API: ");
    console.log(error);
    return error;
  }
}

// this performance summary is used in the cards in the portfolio page
export async function getPortfolioPerformanceSummaryAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL + "/portfolio/performanceSummary/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioPerformanceSummary API: ");
    console.log(error);
    return error;
  }
}

// get portfolio growth for the line chart
export async function getPortfolioGrowthByYearAPI(portfolioId, startYear) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL +
        "/portfolio/getPortfolioAnnualGrowth/" +
        portfolioId +
        "/" +
        startYear,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioGrowthByYear API: ");
    console.log(error);
    return error;
  }
}
