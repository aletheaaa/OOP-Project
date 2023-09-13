import "./styles/App.css";
import Dashboard from "./pages/Dashboard/index.js";
import SideNavBar from "./components/SideNavBar";

function App() {
  return (
    <div className="App">
      <div className="App-header">Portfolio Management</div>
      <div>
        <div className="container-fluid">
          <div className="row">
            <div className="col-1 col-sm-3 col-lg-2 px-0">
              <SideNavBar />
            </div>
            <div className="col ">
              <Dashboard />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
