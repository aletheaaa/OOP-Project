import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css"; // Bootstrap CSS
import "bootstrap/dist/js/bootstrap.bundle.min"; // Bootstrap Bundle JS
import "./styles/index.css";
import "bootstrap-icons/font/bootstrap-icons.css"; // Bootstrap Icons
import App from "./App";
import { RouterProvider } from "react-router-dom";
import router from "./router/PageRouter";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router}>
      <App />
    </RouterProvider>
  </React.StrictMode>
);
