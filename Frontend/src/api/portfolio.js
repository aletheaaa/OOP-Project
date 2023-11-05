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
    console.log("Error in createPortfolio API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}

export async function getValidStockSymbolsAPI() {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL + "/stock/allStockSymbols",
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getValidStockSymbols API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
      BASE_URL + "/portfolio/getPortfolioDetails/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioDetails API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
      BASE_URL + "/portfolio/assetsAllocation/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getAssetAllocationBySector API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}

// asset allocation by industry
export async function getAssetAllocationByIndustryAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.get(
      BASE_URL + "/portfolio/getIndustries/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getAssetAllocationByIndustry API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}

// get asset allocation by country
export async function getAssetAllocationByCountryAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };

    const response = await axios.get(
      BASE_URL + "/portfolio/getCountries/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getAssetAllocationByCountry API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
      BASE_URL + "/portfolio/performanceSummary/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioPerformanceSummary API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
        userId +
        "/" +
        portfolioId +
        "/" +
        startYear,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioGrowthByYear API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
        userId +
        "/" +
        portfolioId +
        "/" +
        startYear +
        "/" +
        monthIndex,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioGrowthByMonth API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
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
        userId +
        "/" +
        portfolioId +
        "/" +
        startYear,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in getPortfolioAnnualReturns API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}

// delete portfolio
export async function deletePortfolioAPI(portfolioId) {
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };
    const response = await axios.delete(
      BASE_URL + "/portfolio/deletePortfolio/" + userId + "/" + portfolioId,
      config
    );
    return response;
  } catch (error) {
    console.log("Error in deletePortfolioAPI API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}

// compare portfolio
export async function comparePortfolio(portfolio1Id, portfolio2Id) {
  try {
    let portfolio1Info = getPortfolioPerformanceSummaryAPI(portfolio1Id);
    let portfolio2Info = getPortfolioPerformanceSummaryAPI(portfolio2Id);
    let response = portfolio1Info.data;
    console.log(response);
    return response;
  }
  catch (error) {
    console.log("Error in comparePortfolio API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}