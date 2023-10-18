import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import Authenticate from "./pages/Authenticate/index";

function getToken() {
  return sessionStorage.getItem("token");
}

function setToken(userToken) {
  sessionStorage.setItem("token", JSON.stringify(userToken));
}

function setId(id) {
  sessionStorage.setItem("id", JSON.stringify(id));
}

function App() {
  const token = getToken();
  console.log("token", token);

  return token ? <PageRouter /> : <Authenticate setToken={setToken} setId={setId} />;
}

export default App;
