import "./styles/App.css";
import React from "react";

import PageRouter from "./router/PageRouter";
import Authenticate from "./pages/Authenticate";
import { getToken,setToken, setId } from "./api/authenticate";

function App() {
  const token = getToken();
  console.log("token", token);

  return token && token.length === 147 ? <PageRouter /> : <Authenticate setToken={setToken} setId={setId} />;
}

export default App;
