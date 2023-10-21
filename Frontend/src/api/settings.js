import axios from "axios";

export function updateSettings(firstName, lastName) {
  console.log("[updateSettings]");
  console.log("First Name: " + firstName);
  console.log("Last Name: " + lastName);
}

/*
export async function updateSettings() {
  
}
*/

export async function changePassword() {
  console.log("[changePassword]");
  //return { status: 200, message: "Password changed successfully!" };
  return { status: 403, message: "Error changing password!" };
}