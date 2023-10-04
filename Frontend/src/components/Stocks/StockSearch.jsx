import React from "react";
import { useNavigate } from "react-router-dom";

function StockSearch() {
  const navigate = useNavigate();
  let path = "/stocks";

  const redirect = (event) => {
    event.preventDefault();
    path = "/stocks/" + document.getElementById("stockSymbol").value;
    navigate(path);
  };

  return (
    <div className="container-fluid mt-3 d-flex align-items-center justify-content-center">
      <div className="row">
        <div className="col border p-3">
          <form>
            <div className="mb-3">
              <h5>Please Search for a Stock</h5>
            </div>
            <div className="mb-3">
              <label for="stockSymbol" className="form-label">Stock Symbol</label>
              <input type="text" className="form-control" id="stockSymbol" placeholder="e.g. AAPL" />
            </div>
            <button type="submit" className="btn btn-outline-danger" onClick={ redirect } >Search</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default StockSearch;