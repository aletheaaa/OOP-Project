import axios from "axios";

const AUTH_URL = "http://localhost:8080/api/v1/auth/authenticate";
const REGISTER_URL = "http://localhost:8080/api/v1/auth/register";

// Placeholder values for code and message
let code = "500";
let message = "Unknown Error";

export function getToken() {
  return sessionStorage.getItem("token");
}

export function setToken(userToken) {
  let token = JSON.stringify(userToken).slice(1, -1);
  sessionStorage.setItem("token", token);
}

export function getId() {
  return sessionStorage.getItem("id");
}

export function setId(id) {
  sessionStorage.setItem("id", JSON.stringify(id));
}

export function getEmail() {
  return sessionStorage.getItem("email");
}

export function setEmail(userEmail) {
  let email = JSON.stringify(userEmail).slice(1, -1);
  sessionStorage.setItem("email", email);
}

export function logout() {
    sessionStorage.clear();
    window.location.href = "/";
}

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

export async function resetPassword(email) {
  console.log("[Forgot Password] " + email);
  try {
    const response = await axios.post("http://localhost:8080/api/v1/auth/sendPasswordResetLink", email);

    if (response.status == "200") {
      console.log(response);
      alert("Password reset link sent to " + email);
      return response;
    }
    return { code: code, message: message }
  } catch (error) {
    console.log(error);
    return error;
  }
}

export async function confirmResetPassword(email, token) {
  console.log("[Confirm Reset Password] " + email + " " + token);
  let requestBody = {
    email: email,
    token: token,
  }
  try {
    const response = await axios.post("http://localhost:8080/api/v1/auth/resetPassword", requestBody);

    if (response.status == "200") {
      console.log(response);
      return response;
    }
    return { code: code, message: message }
  } catch (error) {
    console.log(error);
    return error;
  }
}