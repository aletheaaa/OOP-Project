import React from "react";
import StockSearchButton from "./StockSearchButton";

function StockSearchBox() {
  return (
    <div className="container-fluid mt-3 d-flex align-items-center justify-content-center">
      <div className="row">
        <div className="col border p-3">
          <form>
            <div className="mb-3">
              <label for="stockSymbol" className="form-label">Find Symbol</label>
              <input type="text" className="form-control" id="stockSymbol" placeholder="e.g. AAPL" />
            </div>
            <StockSearchButton />
          </form>
        </div>
      </div>
    </div>
  );
};

export default StockSearchBox;