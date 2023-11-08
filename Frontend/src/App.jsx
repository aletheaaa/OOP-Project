import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import Authenticate from "./pages/Authenticate";
import { getToken,setToken, setId, setEmail } from "./api/authenticate";

function App() {
  const token = getToken();
  console.log("token", token);

  return token ? <PageRouter /> : <Authenticate setToken={setToken} setId={setId} setEmail={setEmail} />;
}

export default App;
