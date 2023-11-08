import axios from "axios";
import { getToken, getId } from "./authenticate";

const PORTFOLIO_URL = "http://localhost:8080/portfolio";
const token = getToken();
const user = getId();

// get user portfolios
export async function getPortfolios() {
  try {
    let URL = PORTFOLIO_URL + "/user/" + user;
    let config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const response = await axios.get(URL, config);
    return response;
  } catch (error) {
    console.log("Error in getPortfolios API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}
