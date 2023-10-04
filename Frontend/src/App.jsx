import "./styles/App.css";
import React from "react";

import Router from "./router/Router";
import Login from "./pages/Login/index";

function setToken(userToken) {
  sessionStorage.setItem("token", JSON.stringify(userToken));
}

function getToken() {
  return sessionStorage.getItem("token");
}

function App() {
  const token = getToken();
  console.log("token", token);

  return token ? <Router /> : <Login setToken={setToken} />;
}

export default App;
