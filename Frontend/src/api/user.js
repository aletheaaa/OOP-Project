import axios from "axios";
import { getToken } from "./authenticate";

export async function getProfile(email) {
  const USER_URL = `http://localhost:8080/users/email/${email}`;
  const token = getToken();
  const config = {
    headers: { Authorization: `Bearer ${token}` }
  }
  
  try {
    const response = await axios.get(USER_URL, config);
    if (response.status < 400) {
      console.log(response);
      return response.data;
    }
  } catch (error) {
    console.log(error);
    return error;
  }
}

// Prepared the code below for once API endpoints are changed to use userId instead of email
/*
export async function getProfile(userId) {
  let URL = "http://localhost:8080/users/" + userId;
  try {
    const response = await axios.get(URL);
    if (response.status == "200") {
      return response;
    }
  } catch (error) {
    console.log("[getProfile] Error");
    console.log(error);
    return error;
  }
}
*/