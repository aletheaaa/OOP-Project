import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Login from "../pages/Login/index";
import Register from "../pages/Register/index";

function setToken(userToken) {
  sessionStorage.setItem("token", JSON.stringify(userToken));
}

const router = createBrowserRouter([
  {
    path: "/",
    element: <Login setToken={setToken} />,
    errorElement: <Login setToken={setToken} />,
  },
  {
    path: "/register",
    element: <Register setToken={setToken} />,
  }
]);

function Router() {
  return <RouterProvider router={router} />;
}

export default Router;