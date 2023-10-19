import axios from "axios";
import { getToken } from "./authenticate";

const USER_URL = "http://localhost:8080/users/email/";
const PORTFOLIO_URL = "http://localhost:8080/"; // Placeholder
const token = getToken();

export async function getProfile(email) {
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

// Placeholder
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