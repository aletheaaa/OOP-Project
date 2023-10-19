import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import Authenticate from "./pages/Authenticate/index";
import { getToken, setToken, setId, setEmail } from "./api/authenticate";

function App() {
  const token = getToken();
  console.log("token", token);

  return token && token.length == 147 ? <PageRouter /> : <Authenticate setToken={setToken} setId={setId} setEmail={setEmail} />;
}

export default App;
