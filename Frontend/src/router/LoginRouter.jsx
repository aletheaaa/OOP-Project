import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Login from "../pages/Login/index";
import Register from "../pages/Register/index";

function setToken(userToken) {
  sessionStorage.setItem("token", JSON.stringify(userToken));
}

function setId(id) {
  sessionStorage.setItem("id", JSON.stringify(id));
}

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login setToken={setToken} setId={setId} />,
    errorElement: <Login setToken={setToken} setId={setId} />,
  },
  {
    path: "/register",
    element: <Register setToken={setToken} setId={setId} />,
  }
]);

function Router() {
  return <RouterProvider router={router} />;
}

export default Router;