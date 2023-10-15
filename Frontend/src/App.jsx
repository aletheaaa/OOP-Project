import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import LoginRouter from "./router/LoginRouter";

function getToken() {
  return sessionStorage.getItem("token");
}

function App() {
  const token = getToken();
  console.log("token", token);

  return token ? <PageRouter /> : <LoginRouter />;
}

export default App;
