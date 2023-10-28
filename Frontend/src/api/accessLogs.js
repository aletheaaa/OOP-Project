import axios from "axios";

const BASE_URL = "http://localhost:8080";
let token = sessionStorage.getItem("token");
let userId = sessionStorage.getItem("id");

// get access logs
export async function getAccessLogsAPI(email) {
  try {
    let URL = BASE_URL + "/logs/accessLogs?" + "email=" + email;
    let config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const response = await axios.get(URL, config);
    return response;
  } catch (error) {
    console.log("Error in getAccessLogs API: ", error);
    return {
      status: error.response.status,
      data: error.response.data.message,
    };
  }
}
