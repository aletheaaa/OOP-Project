import React, { useEffect, useState } from "react";

export default function CreatePortfolioButton() {
  const [validStocks, setValidStocks] = useState([]);
  const [stock, setStock] = useState("");
  const [numStockFields, setNumStockFields] = useState(1);
  const [chosenStockAllocation, setChosenStockAllocation] = useState([
    { stock: "", allocation: 0, id: 0 },
  ]);
  const [totalStockAlloc, setTotalStockAlloc] = useState(0);

  useEffect(() => {
    setValidStocks(["AAPL", "TSLA"]);
  }, []);

  const handleStockChange = (event, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation[index] = {
      ...updatedStockAllocation[index],
      stock: event.target.value,
    };
    setChosenStockAllocation(updatedStockAllocation);
  };

  const handleAllocationChange = (event, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation[index] = {
      ...updatedStockAllocation[index],
      allocation: event.target.value,
    };
    setChosenStockAllocation(updatedStockAllocation);
  };

  const removeStockField = (index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation.splice(index, 1);
    setChosenStockAllocation(updatedStockAllocation);
  };

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
        <div
          className="modal-dialog"
          style={{ width: "1200px", maxWidth: "1200px" }}
        >
          <div className="modal-content" style={{ width: "1200px" }}>
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
                  <div className="col-3">
                    <label for="name" className="col-form-label">
                      Portfolio Name
                    </label>
                  </div>
                  <div className="col-9">
                    <input type="text" id="name" className="form-control" />
                  </div>
                </div>
                {/* description */}
                <div className="row p-1">
                  <div className="col-3">
                    <label for="description" className="col-form-label">
                      Description
                    </label>
                  </div>
                  <div className="col-9">
                    <textarea
                      type="text"
                      id="description"
                      className="form-control"
                    />
                  </div>
                </div>
                {/* Capital */}
                <div className="row p-1">
                  <div className="col-3">
                    <label for="capital" className="col-form-label">
                      Capital
                    </label>
                  </div>
                  <div className="col-9">
                    <input type="text" id="capital" className="form-control" />
                  </div>
                </div>
                {/* timePeriod */}
                <div className="row p-1">
                  <div className="col-3">
                    <label for="timePeriod" className="col-form-label">
                      Time Period
                    </label>
                  </div>
                  <div className="col-9">
                    <select id="timePeriod" className="form-select">
                      <option value="1">Monthly</option>
                      <option value="2">Quarterly</option>
                      <option value="3">Yearly</option>
                    </select>
                  </div>
                </div>
                {/* Start Date */}
                <div className="row p-1">
                  <div className="col-3">
                    <label for="startDate" className="col-form-label">
                      Start Date
                    </label>
                  </div>
                  <div className="col-9">
                    <input
                      type="text"
                      id="startDate"
                      className="form-control"
                    />
                  </div>
                </div>

                {/* Stock allocation */}
                <div className="mt-3">
                  <span className="fw-bold">Asset Allocation</span>
                  <div className="row p-1">
                    <div className="col"></div>
                    <div className="col">Stock Symbol</div>
                    <div className="col">Allocation</div>
                    <div className="col-1"></div>
                  </div>
                  {chosenStockAllocation.map((ele, index) => {
                    return (
                      <div key={ele.id} className="p-0 m-0">
                        <div className="row p-1">
                          <div className="col">Asset {index + 1}</div>
                          <div className="col">
                            <input
                              type="text"
                              id={`stock-${index}`}
                              className="form-control"
                              onChange={(event) =>
                                handleStockChange(event, index)
                              }
                              placeholder={ele.stock}
                            />
                          </div>
                          <div className="col">
                            <input
                              type="text"
                              id={`allocation-${index}`}
                              className="form-control"
                              onChange={(event) => {
                                handleAllocationChange(event, index);
                                setTotalStockAlloc(
                                  Number(totalStockAlloc) -
                                    Number(ele.allocation) +
                                    Number(event.target.value)
                                );
                              }}
                              placeholder={ele.allocation}
                            />
                          </div>
                          {index !== 0 ? (
                            <button
                              className="col-1 btn btn-secondary"
                              // onClick={() => removeStockField(index)}
                              onClick={() => {
                                setChosenStockAllocation(
                                  chosenStockAllocation.filter(
                                    (a) => a.id != ele.id
                                  )
                                );
                                setTotalStockAlloc(
                                  Number(totalStockAlloc) -
                                    Number(ele.allocation)
                                );
                              }}
                            >
                              X
                            </button>
                          ) : (
                            <div className="col-1"></div>
                          )}
                        </div>
                      </div>
                    );
                  })}
                </div>
                <hr />

                <div className="p-1 d-flex justify-content-between">
                  <span className="fw-bold">Total</span>
                  <span className="fw-bold w-25 text-center">
                    {totalStockAlloc}
                  </span>
                </div>
                <div className="row p-1 d-flex justify-content-end mt-4">
                  <button
                    className="w-25 btn btn-secondary"
                    onClick={() => {
                      setChosenStockAllocation([
                        ...chosenStockAllocation,
                        { stock: "", allocation: 0, id: numStockFields },
                      ]);
                      setNumStockFields(numStockFields + 1);
                    }}
                  >
                    Add Stock
                  </button>
                </div>
              </div>
            </div>
            <div className="modal-footer d-flex justify-content-center">
              <button type="button" className="btn btn-success">
                Create
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
