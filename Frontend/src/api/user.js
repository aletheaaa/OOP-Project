import axios from "axios";
import { getToken } from "./authenticate";

const USER_URL = "http://localhost:8080/users/email/";
const PORTFOLIO_URL = "http://localhost:8080/"; // Placeholder
const token = getToken();
const email = "userone@gmail.com"; // Placeholder, should make a call based on ID in sessionStorage

export async function getProfile() {
  let URL = USER_URL + email;
  let config = {
    headers: { Authorization: `Bearer ${token}` }
  }
  
  try {
    const response = await axios.get(URL, config);
    if (response.status < 400) {
      console.log(response.data);
      return response.data;
    }
  } catch (error) {
    console.log(error);
    return error;
  }
}

// Placeholder until backend is done
export async function getPortfolios(user) {
  return [{
    portfolioId: "1",
    name: "Safe Retirement",
    description: "Safe Retirement",
    totalCapital: 1000,
  },
  {
    portfolioId: "2",
    name: "Risky Investment",
    description: "Risky Investment",
    totalCapital: 1000,
  },
  {
    portfolioId: "3",
    name: "Sugar Daddy Money",
    description: "Jaden's Sugar Daddy Money",
    totalCapital: 1000,
  }];
}


// Placeholder
/*
export async function getPortfolios(user) {
  let URL = PORTFOLIO_URL + user;
  try {
    const response = await axios.get(URL);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.log("[getPortfolios] Error");
    console.log(error);
    return error;
  }
}
*/