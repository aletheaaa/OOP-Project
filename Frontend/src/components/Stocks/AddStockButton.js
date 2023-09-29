import React from "react";

export default function AddStockButton(stockPrice) {
  console.log(stockPrice);
  return (
    <>
      <button type="button" className="btn btn-success" data-bs-toggle="modal" data-bs-target="#addToPortfolio">
        Add to Portfolio
      </button>
      
      <div className="modal fade" id="addToPortfolio" tabindex="-1" aria-labelledby="addToPortfolioLabel" aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h1 className="modal-title fs-5" id="addToPortfolioLabel">Add Stock to Portfolio</h1>
              <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div className="modal-body">
              <div className="container">
                <div className="row">
                  <div className="col-12">
                    <h2 className="fs-6 fw-bold">StockName</h2>
                  </div>
                </div>
                <div className="row p-1">
                  <div class="col-3">
                    <label for="price" class="col-form-label">Price</label>
                  </div>
                  <div class="col-9">
                    <input type="text" id="price" class="form-control" value={stockPrice.stockPrice} disabled />
                  </div>
                </div>
                <div className="row p-1">
                  <div class="col-3">
                    <label for="quantity" class="col-form-label">Quantity</label>
                  </div>
                  <div class="col-9">
                    <input type="text" id="quantity" class="form-control" />
                  </div>
                </div>
              </div>
            </div>
            <div className="modal-footer d-flex justify-content-center">
              <button type="button" className="btn btn-secondary">Create Portfolio</button>
              <button type="button" className="btn btn-primary">Add to Existing</button>
            </div>
          </div>
        </div>
      </div>
    </>
  ); 
};