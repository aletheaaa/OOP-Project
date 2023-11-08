import axios from "axios";

// Placeholder values for code and message
let code = "500";
let message = "Unknown Error";

let token = sessionStorage.getItem("token");

export async function changePassword(email, oldPassword, newPassword) {
  // console.log("[changePassword]");
  // console.log("Email: " + email);
  // console.log("Old Password: " + oldPassword);
  // console.log("New Password: " + newPassword);
  try {
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      }
    };
    const requestBody = {
      email: email,
      currentPassword: oldPassword,
      newPassword: newPassword
    };
    const response = await axios.post(
      "http://localhost:8080/users/change-password",
      requestBody,
      config,
    );

    if (response.status == "200") {
      console.log(response);
      return response;
    }
    return { code: code, message: message };
  } catch (error) {
    console.log(error);
    return error;
  }
  
}