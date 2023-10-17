import React, { useEffect, useState } from "react";
import axios from "axios";

export default function CreatePortfolioButton() {
  const [validStocks, setValidStocks] = useState([]);
  const [portfolioDetails, setPortfolioDetails] = useState({
    Capital: 0,
    TimePeriod: "Monthly",
    StartDate: "",
    PortfolioName: "",
    Description: "",
    UserId: 1,
    AssetList: [],
  });
  const [numStockFields, setNumStockFields] = useState(1);
  const [chosenStockAllocation, setChosenStockAllocation] = useState([
    { Symbol: "", Allocation: 0, id: 0, Sector: "" },
  ]);
  const [totalStockAlloc, setTotalStockAlloc] = useState(0);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  // TODO: connect to api to get all the valid stock symbols
  useEffect(() => {
    setValidStocks(["AAPL", "TSLA"]);
  }, []);

  const handleStockChange = (event, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation[index] = {
      ...updatedStockAllocation[index],
      Symbol: event.target.value,
    };
    setChosenStockAllocation(updatedStockAllocation);
  };

  // TODO: to connect to api to get the sector from stock symbol
  const getStockSector = (stock) => {
    console.log("this is stock", stock);
    axios
      .get(
        `https://www.alphavantage.co/query?function=OVERVIEW&symbol=${stock}&apikey=${process.env.REACT_APP_API_KEY}`
      )
      .then((response) => {
        console.log("this is response", response);
      });
    return "Technology";
  };

  const handleAllocationChange = (event, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation[index] = {
      ...updatedStockAllocation[index],
      Allocation: event.target.value,
    };
    setChosenStockAllocation(updatedStockAllocation);
  };

  const handleSubmit = () => {
    console.log("hre");
    // console.log(portfolioDetails);
    // console.log(chosenStockAllocation);

    // validation
    let isValid = true;
    if (portfolioDetails.PortfolioName.length === 0) {
      isValid = false;
      let node = document.getElementById(`name`);
      node.classList.add("is-invalid");
    }
    if (portfolioDetails.Description.length === 0) {
      isValid = false;
      let node = document.getElementById(`description`);
      node.classList.add("is-invalid");
    }
    if (portfolioDetails.Capital <= 0) {
      isValid = false;
      let node = document.getElementById(`capital`);
      node.classList.add("is-invalid");
    }
    if (portfolioDetails.StartDate.length === 0) {
      isValid = false;
      let node = document.getElementById(`startDate`);
      node.classList.add("is-invalid");
    }
    if (chosenStockAllocation === 0) {
      isValid = false;
      let node = document.getElementById(`stock-0`);
      node.classList.add("is-invalid");
      setErrorMessage("Please add at least one stock.");
    }
    if (totalStockAlloc > 100) {
      isValid = false;
      setErrorMessage(
        "Total % of allocation should add up to a maximum of 100."
      );
    }
    chosenStockAllocation.forEach((ele, index) => {
      if (ele.Symbol.length === 0) {
        isValid = false;
        let node = document.getElementById(`stock-${index}`);
        node.classList.add("is-invalid");
      }
      if (ele.Allocation <= 0) {
        isValid = false;
        let node = document.getElementById(`allocation-${index}`);
        node.classList.add("is-invalid");
      }
    });
    if (!isValid) {
      return;
    }

    // filtering out id field from chosenStockAllocation
    const chosenStockAllocationFiltered = chosenStockAllocation.map((ele) => {
      return {
        Symbol: ele.Symbol,
        Allocation: Number(ele.Allocation),
        Sector: ele.Sector,
      };
    });
    // adding allocation of cash if the total allocation is less than 100
    if (totalStockAlloc < 100) {
      chosenStockAllocationFiltered.push({
        Symbol: "CASHALLOCATION",
        Allocation: 100 - totalStockAlloc,
        Sector: "CASH",
      });
    }
    const requestBody = {
      ...portfolioDetails,
      AssetList: chosenStockAllocationFiltered,
    };
    // send to backend
    let token = sessionStorage.getItem("token");
    token = token.slice(1, token.length - 1);
    const config = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json", // You can set other headers as needed.
      },
    };
    setIsLoading(true);
    console.log("this is req body", requestBody);
    axios
      .post(
        "http://localhost:8080/portfolio/createPortfolio",
        requestBody,
        config
      )
      .then((response) => {
        console.log("This is the response");
        console.log(response.data);
        if (response.status === 200) {
          setErrorMessage("");
          setSuccessMessage("Portfolio created successfully.");
        }
        setIsLoading(false);
      })
      .catch((e) => {
        console.log(e.message);
        setErrorMessage(e.message);
        setIsLoading(false);
      });

    return;
  };

  // function to validate the input fields of the form
  const validateField = (e, condition, elementId) => {
    if (condition) {
      let node = document.getElementById(elementId);
      node.classList.remove("is-invalid");
      return true;
    } else {
      let node = document.getElementById(elementId);
      node.classList.add("is-invalid");
      return false;
    }
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
        tabIndex="-1"
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
                    <label htmlFor="name" className="col-form-label">
                      Portfolio Name
                    </label>
                  </div>
                  <div className="col-9">
                    <input
                      type="text"
                      id="name"
                      className="form-control"
                      onChange={(event) => {
                        setPortfolioDetails({
                          ...portfolioDetails,
                          PortfolioName: event.target.value,
                        });
                      }}
                      onBlur={(event) => {
                        validateField(
                          event,
                          event.target.value.length > 0,
                          `name`
                        );
                      }}
                    />
                    <div
                      id="validationServerPortfolioNameFeedback"
                      className="invalid-feedback"
                    >
                      Please enter a unique portfolio name.
                    </div>
                  </div>
                </div>
                {/* description */}
                <div className="row p-1">
                  <div className="col-3">
                    <label htmlFor="description" className="col-form-label">
                      Description
                    </label>
                  </div>
                  <div className="col-9">
                    <textarea
                      type="text"
                      id="description"
                      className="form-control"
                      onChange={(event) => {
                        setPortfolioDetails({
                          ...portfolioDetails,
                          Description: event.target.value,
                        });
                      }}
                      onBlur={(event) => {
                        validateField(
                          event,
                          event.target.value.length > 0,
                          `description`
                        );
                      }}
                    />
                    <div
                      id="validationServerDescriptionFeedback"
                      className="invalid-feedback"
                    >
                      Please enter a description.
                    </div>
                  </div>
                </div>
                {/* Capital */}
                <div className="row p-1">
                  <div className="col-3">
                    <label htmlFor="capital" className="col-form-label">
                      Capital
                    </label>
                  </div>
                  <div className="col-9">
                    <input
                      type="number"
                      id="capital"
                      className="form-control"
                      onChange={(event) => {
                        setPortfolioDetails({
                          ...portfolioDetails,
                          Capital: Number(event.target.value),
                        });
                      }}
                      onBlur={(event) => {
                        validateField(event, event.target.value > 0, `capital`);
                      }}
                    />
                    <div
                      id="validationServerCapitalFeedback"
                      className="invalid-feedback"
                    >
                      Please enter a capital.
                    </div>
                  </div>
                </div>
                {/* timePeriod */}
                <div className="row p-1">
                  <div className="col-3">
                    <label htmlFor="timePeriod" className="col-form-label">
                      Time Period
                    </label>
                  </div>
                  <div className="col-9">
                    <select
                      id="timePeriod"
                      className="form-select"
                      onSelect={(event) => {
                        setPortfolioDetails({
                          ...portfolioDetails,
                          TimePeriod: event.target.value,
                        });
                      }}
                    >
                      <option value="Monthly">Monthly</option>
                      <option value="Quarterly">Quarterly</option>
                      <option value="Yearly">Yearly</option>
                    </select>
                  </div>
                </div>
                {/* Start Date */}
                <div className="row p-1">
                  <div className="col-3">
                    <label htmlFor="startDate" className="col-form-label">
                      Start Date
                    </label>
                  </div>
                  <div className="col-9">
                    <input
                      type="text"
                      id="startDate"
                      placeholder="YYYY-MM"
                      className="form-control"
                      onChange={(event) => {
                        setPortfolioDetails({
                          ...portfolioDetails,
                          StartDate: event.target.value,
                        });
                      }}
                      onBlur={(event) => {
                        validateField(
                          event,
                          event.target.value.length > 0,
                          `startDate`
                        );
                      }}
                    />
                    <div
                      id="validationServerCapitalFeedback"
                      className="invalid-feedback"
                    >
                      Please enter a valid start date.
                    </div>
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
                              className={`form-control`}
                              onChange={(event) =>
                                handleStockChange(event, index)
                              }
                              placeholder={ele.Symbol.toUpperCase()}
                              onBlur={(event) => {
                                const isValid = validateField(
                                  event,
                                  validStocks.includes(
                                    event.target.value.toUpperCase()
                                  ),
                                  `stock-${index}`
                                );
                                if (isValid) {
                                  // setting sector to portfolioDetails to relevant stock
                                  const sector = getStockSector(
                                    event.target.value.toUpperCase()
                                  );

                                  const updatedStockAllocation = [
                                    ...chosenStockAllocation,
                                  ];
                                  updatedStockAllocation.forEach((ele) => {
                                    if (ele.id === index) {
                                      ele.Symbol =
                                        event.target.value.toUpperCase();
                                      ele.Sector = sector;
                                    }
                                  });
                                  setChosenStockAllocation(
                                    updatedStockAllocation
                                  );
                                }
                              }}
                            />
                            <div
                              id="validationServerStockFeedback"
                              className="invalid-feedback"
                            >
                              Please enter a valid stock symbol.
                            </div>
                          </div>
                          <div className="col">
                            <div className="input-group">
                              <input
                                type="number"
                                id={`allocation-${index}`}
                                className="form-control col-4"
                                onChange={(event) => {
                                  handleAllocationChange(event, index);
                                  setTotalStockAlloc(
                                    Number(totalStockAlloc) -
                                      Number(ele.Allocation) +
                                      Number(event.target.value)
                                  );
                                }}
                                placeholder={ele.Allocation}
                                onBlur={(event) => {
                                  validateField(
                                    event,
                                    event.target.value > 0,
                                    `allocation-${index}`
                                  );
                                }}
                              />
                              <span
                                className="input-group-text col-2"
                                id="inputGroupPrepend"
                              >
                                %
                              </span>
                              <div
                                id="validationServerStockFeedback"
                                className="invalid-feedback"
                              >
                                Allocation should be more than 0%.
                              </div>
                            </div>
                          </div>
                          {index !== 0 ? (
                            <div className="col-1 d-flex justify-content-center">
                              <button
                                className="btn btn-secondary h-75"
                                onClick={() => {
                                  setChosenStockAllocation(
                                    chosenStockAllocation.filter(
                                      (a) => a.id !== ele.id
                                    )
                                  );
                                  setTotalStockAlloc(
                                    Number(totalStockAlloc) -
                                      Number(ele.Allocation)
                                  );
                                }}
                              >
                                X
                              </button>
                            </div>
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
                  <span
                    className={`fw-bold w-25 text-center rounded ${
                      totalStockAlloc > 100 ? "text-danger" : ""
                    }`}
                  >
                    {totalStockAlloc}
                  </span>
                </div>
                <div className="text-end">
                  {totalStockAlloc > 100 ? (
                    <span className="text-danger">
                      Total % of allocation should add up to a maximum of 100.
                    </span>
                  ) : (
                    <></>
                  )}
                </div>
                <div className="row p-1 d-flex justify-content-end mt-4">
                  <button
                    className="w-25 btn btn-secondary"
                    onClick={() => {
                      setChosenStockAllocation([
                        ...chosenStockAllocation,
                        { Symbol: "", Allocation: 0, id: numStockFields },
                      ]);
                      setNumStockFields(numStockFields + 1);
                    }}
                  >
                    Add Stock
                  </button>
                </div>
              </div>
            </div>
            <div className="text-danger text-center">{errorMessage}</div>
            <div className="text-success text-center">{successMessage}</div>
            <div className="modal-footer d-flex justify-content-center border-0">
              {isLoading ? (
                <button className="btn btn-success" type="button" disabled>
                  <span
                    className="spinner-border spinner-border-sm mr-2"
                    role="status"
                    aria-hidden="true"
                  ></span>{" "}
                  Loading...
                </button>
              ) : (
                <button
                  type="button"
                  className="btn btn-success"
                  onClick={() => {
                    handleSubmit();
                  }}
                >
                  Create
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
