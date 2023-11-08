import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Root from "../pages/Root";
import ErrorPage from "../pages/ErrorPage";
import Landing from "../pages/Landing/index";
import Portfolios from "../pages/Portfolios/index";
import UserSettings from "../pages/User/UserSettings";
import AccessLogs from "../pages/AccessLogs";
import ComparePortfolios from "../pages/Portfolios/ComparePortfolios";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Landing />,
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
      {
        path: "/comparePortfolios",
        element: <ComparePortfolios />,
      },
    ],
  },
]);

export default function Router() {
  return <RouterProvider router={router} />;
}