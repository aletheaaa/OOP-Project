import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import AuthRouter from "./router/AuthRouter";
import { getToken } from "./api/authenticate";

function App() {
  const token = getToken();
  console.log("token", token);

  return token && token.length === 147 ? <PageRouter /> : <AuthRouter />;
}

export default App;
