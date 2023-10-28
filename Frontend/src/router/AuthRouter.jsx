import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { setToken, setId } from "../api/authenticate";

import Authenticate from "../pages/Authenticate";
import ResetPassword from "../pages/ResetPassword";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Authenticate setToken={setToken} setId={setId} />,
    errorElement: <Authenticate setToken={setToken} setId={setId} />,
  },
  {
    path: "/resetPassword",
    element: <ResetPassword />,
    errorElement: <Authenticate setToken={setToken} setId={setId} />,
  },
]);

function Router() {
  return <RouterProvider router={router} />;
}

export default Router;