import "./styles/App.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import Dashboard from "./pages/Dashboard/index";
import Login from "./pages/Login/index";
import Stocks from "./pages/Stocks/index";
import SideNavBar from "./components/Common/SideNavBar";
import ErrorPage from "./pages/ErrorPage";
import Portfolios from "./pages/Portfolios/index";

const authenticated = false;
// const authenticated = true;

const router = createBrowserRouter([
  {
    path: "/",
    element: <Dashboard />,
    errorElement: <ErrorPage />,
  },
  {
    path: "/stocks",
    element: <Stocks />,
  },
  {
    path: "/portfolios",
    element: <Portfolios />,
  },
]);

function App() {
  // DEV NOTES: Need to implement logic for authentication
  // Most likely will use a state variable to keep track of authentication
  // Then use a conditional to render the appropriate page
  return (
    <div className="App">
      {authenticated ? (
        <>
          <div className="App-header">Portfolio Management</div>
          <div className="container-fluid">
            <div className="row">
              <div className="col-1 col-sm-3 col-lg-2 px-0">
                <SideNavBar />
              </div>
              <div className="col ">
                <RouterProvider router={router} />
              </div>
            </div>
          </div>
        </>
      ) : (
        <Login />
      )}
    </div>
  );
}

export default App;
