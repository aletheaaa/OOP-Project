import React, { useEffect, useState } from "react";

export default function CreatePortfolioButton() {
  const [validStocks, setValidStocks] = useState([]);
  const [stock, setStock] = useState("");

  useEffect(() => {
    setValidStocks(["AAPL", "TSLA"]);
  }, []);

  return (
    <>
      <button
        type="button"
        className="btn btn-success"
        data-bs-toggle="modal"
        data-bs-target="#createPortfolio"
      >
        Create Portfolio
      </button>

      <div
        className="modal fade"
        id="createPortfolio"
        tabindex="-1"
        aria-labelledby="createPortfolio"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content" style={{ width: "800px" }}>
            <div className="modal-header">
              <h1 className="modal-title fs-5" id="createPortfolioLabel">
                Create Portfolio
              </h1>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <div className="container">
                {/* portfolio name */}
                <div className="row p-1">
                  <div class="col-3">
                    <label for="name" class="col-form-label">
                      Portfolio Name
                    </label>
                  </div>
                  <div class="col-9">
                    <input type="text" id="name" class="form-control" />
                  </div>
                </div>
                {/* description */}
                <div className="row p-1">
                  <div class="col-3">
                    <label for="description" class="col-form-label">
                      Description
                    </label>
                  </div>
                  <div class="col-9">
                    <textarea
                      type="text"
                      id="description"
                      class="form-control"
                    />
                  </div>
                </div>
                {/* Capital */}
                <div className="row p-1">
                  <div class="col-3">
                    <label for="capital" class="col-form-label">
                      Capital
                    </label>
                  </div>
                  <div class="col-9">
                    <input type="text" id="capital" class="form-control" />
                  </div>
                </div>
                {/* Stock */}
                <div className="row p-1">
                  <div class="col-3">
                    <label for="stock" class="col-form-label">
                      Stock
                    </label>
                  </div>
                  <div class="col-9">
                    <input
                      type="text"
                      id="stock"
                      class="form-control"
                      onChange={(value) => {
                        setStock(value.target.value);
                      }}
                    />
                  </div>
                </div>

                {validStocks.includes(stock) ? (
                  <>
                    <div className="row p-1">
                      <div class="col-3">
                        <label for="price" class="col-form-label">
                          Price
                        </label>
                      </div>
                      <div class="col-9">
                        <input type="text" id="price" class="form-control" />
                      </div>
                    </div>
                    <div className="row p-1">
                      <div class="col-3">
                        <label for="quantity" class="col-form-label">
                          Quantity
                        </label>
                      </div>
                      <div class="col-9">
                        <input type="text" id="quantity" class="form-control" />
                      </div>
                    </div>
                  </>
                ) : (
                  <div></div>
                )}
              </div>
            </div>
            <div className="modal-footer d-flex justify-content-center">
              <button type="button" className="btn btn-secondary">
                Create
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
