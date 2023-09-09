import "./styles/App.css";
import Dashboard from "./pages/Dashboard/index.js";
import SideNavBar from "./components/SideNavBar";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h2>Portfolio Management</h2>
      </header>
      <body class="row">
        <div class="col">
          <SideNavBar />
        </div>
        <div class="col">
          <Dashboard />
        </div>
      </body>
    </div>
  );
}

export default App;
