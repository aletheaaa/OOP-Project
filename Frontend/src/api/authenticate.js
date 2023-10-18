import axios from "axios";

const AUTH_URL = "http://localhost:8080/api/v1/auth/authenticate";
const REGISTER_URL = "http://localhost:8080/api/v1/auth/register";

// Placeholder values for code and message
let code = "500";
let message = "Unknown Error";

export async function authenticate(registering, email, password) {
  let URL = registering ? REGISTER_URL : AUTH_URL;
  try {
    const response = await axios.post(URL, {
      email: email,
      password: password,
    });

    if (response.status == "200") {
      return response;
    }
    return { code: code, message: message }
  } catch (error) {
    return error;
  }
}