import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Root from "../pages/Root";
import Dashboard from "../pages/Dashboard/index";
import Login from "../pages/Login/index";
import Stocks from "../pages/Stocks/index";
import ErrorPage from "../pages/ErrorPage";
import Portfolios from "../pages/Portfolios/index";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Dashboard />,
      },
      {
        path: "/stocks",
        element: <Stocks />,
      },
      {
        path: "/portfolios/:portfolioId",
        element: <Portfolios />,
      }
    ],
  },
]);

function Router() {
  return <RouterProvider router={router} />;
}

export default Router;