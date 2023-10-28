import axios from "axios";

const BASE_URL = "http://localhost:8080";
let token = sessionStorage.getItem("token");
let userId = sessionStorage.getItem("id");

export async function createPortfolioAPI(requestBody) {
  try {
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

export async function getPortfolioDetailsAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL + "/portfolio/getPortfolioDetails/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioDetails API: ");
    console.log(error);
    return error;
  }
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

// get annual portfolio growth for the line chart
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

// get monthly portfolio growth for the line chart
export async function getPortfolioGrowthByMonthAPI(
  portfolioId,
  startYear,
  monthIndex
) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL +
        "/portfolio/getPortfolioMonthlyGrowth/" +
        portfolioId +
        "/" +
        startYear +
        "/" +
        monthIndex,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioGrowthByMonth API: ");
    console.log(error);
    return error;
  }
}

export async function getPortfolioAnnualReturnsAPI(portfolioId, startYear) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL +
        "/portfolio/getPortfolioAnnualReturns/" +
        portfolioId +
        "/" +
        startYear,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioAnnualReturns API: ");
    console.log(error);
    return error;
  }
}
