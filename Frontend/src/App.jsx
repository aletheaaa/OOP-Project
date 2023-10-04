import "./styles/App.css";
import React, { useState } from "react";

import Router from "./router/Router";
import Login from "./pages/Login/index";

function App() {
  const [token, setToken] = useState();

  if (!token) {
    return <Login setToken={setToken} />;
  }
  
  return <Router />;
}

export default App;
