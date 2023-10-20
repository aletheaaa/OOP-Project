import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Root from "../pages/Root";
import Dashboard from "../pages/Dashboard/index";
import Stocks from "../pages/Stocks/index";
import ErrorPage from "../pages/ErrorPage";
import Portfolios from "../pages/Portfolios/index";
import UserSettings from "../pages/User/UserSettings";
import AccessLogs from "../pages/AccessLogs";

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
        path: "/stocks/:stockSymbol",
        element: <Stocks />,
      },
      {
        path: "/portfolios/:portfolioId",
        element: <Portfolios />,
      },
      {
        path: "/settings",
        element: <UserSettings />,
      },
      {
        path: "/accessLogs",
        element: <AccessLogs />,
      },
    ],
  },
]);

function Router() {
  return <RouterProvider router={router} />;
}

export default Router;
