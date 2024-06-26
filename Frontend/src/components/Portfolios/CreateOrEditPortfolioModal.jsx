import React, { useEffect, useState } from "react";
import {
  createPortfolioAPI,
  getValidStockSymbolsAPI,
  updatePortfolioAPI,
} from "../../api/portfolio";
import { getPortfolios } from "../../api/user";

export default function CreateOrEditPortfolioModal({
  id,
  mode,
  originalPortfolioDetails,
  assetList,
}) {
  const [validStocks, setValidStocks] = useState([]);
  const [portfolioDetails, setPortfolioDetails] = useState({
    Capital: 0,
    StartDate: "",
    PortfolioName: "",
    Description: "",
    UserId: 1,
    AssetList: [],
  });
  const [numStockFields, setNumStockFields] = useState(1);
  const [chosenStockAllocation, setChosenStockAllocation] = useState([
    { Symbol: "", Allocation: 0, id: 0 },
  ]);
  const [totalStockAlloc, setTotalStockAlloc] = useState(0);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [modeOfModal, setModeOfModal] = useState("Create");
  const [portfolioId, setPortfolioId] = useState(0);

  useEffect(() => {
    // load user id from session storage
    const userId = sessionStorage.getItem("id");
    setPortfolioDetails({
      Capital: 0,
      StartDate: "",
      PortfolioName: "",
      Description: "",
      UserId: 1,
      AssetList: [],
    });
    // loading portfolio details if any if mode is edit
    if (mode) {
      setModeOfModal(mode);
      if (mode == "Edit") {
        const formattedPortfolioDetails = {
          UserId: Number(userId),
          PortfolioName: originalPortfolioDetails.portfolio_name,
          Capital: originalPortfolioDetails.capital,
          Description: originalPortfolioDetails.description,
          StartDate: originalPortfolioDetails.start_date,
          AssetList: assetList,
        };
        console.log(
          "this is formtted portfolio detailssss",
          formattedPortfolioDetails
        );
        setPortfolioDetails(formattedPortfolioDetails);
        setChosenStockAllocation(assetList);
        setTotalStockAlloc(
          assetList.reduce((acc, curr) => {
            return acc + Number(curr.Allocation);
          }, 0)
        );
        setPortfolioId(originalPortfolioDetails.portfolioId);
      } else {
        setPortfolioDetails({
          Capital: 0,
          StartDate: "",
          PortfolioName: "",
          Description: "",
          UserId: 1,
          AssetList: [],
          UserId: Number(userId),
        });
        setChosenStockAllocation([{ Symbol: "", Allocation: 0, id: 0 }]);
        setTotalStockAlloc(0);
      }
    } else {
      setModeOfModal("Create");
    }

    // get all valid stock symbols
    const getValidStocks = async () => {
      const response = await getValidStockSymbolsAPI();
      if (response.status == 200) {
        setValidStocks(response.data);
      } else {
        setErrorMessage("Error getting valid stock symbols: " + response.data);
      }
    };
    getValidStocks();
  }, [mode, originalPortfolioDetails, assetList]);

  const handleAllocationChange = (event, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation[index] = {
      ...updatedStockAllocation[index],
      Allocation: event.target.value,
    };
    setChosenStockAllocation(updatedStockAllocation);
  };

  const handleSubmit = async () => {
    setErrorMessage("");
    // validation
    let isValid = true;
    if (portfolioDetails.PortfolioName.length === 0) {
      isValid = false;
      let node = document.getElementById(`portfolioNameField`);
      node.classList.add("is-invalid");
    }
    if (portfolioDetails.Description.length === 0) {
      isValid = false;
      let node = document.getElementById(`description`);
      node.classList.add("is-invalid");
    }
    if (Number(portfolioDetails.Capital) <= 100) {
      isValid = false;
      let node = document.getElementById(`capital`);
      node.classList.add("is-invalid");
    }
    if (
      // format of startdate
      portfolioDetails.StartDate.length === 0 ||
      portfolioDetails.StartDate.split("-").length !== 2 ||
      portfolioDetails.StartDate.split("-")[0].length !== 4 ||
      portfolioDetails.StartDate.split("-")[1].length !== 2
    ) {
      isValid = false;
      let node = document.getElementById(`startDate`);
      node.classList.add("is-invalid");
    }
    // startDate cannot be in the future
    if (portfolioDetails.StartDate) {
      const currDate = new Date();
      // getting month and year from curr date
      const currMonth = currDate.getMonth() + 1;
      const currYear = currDate.getFullYear();
      if (currYear < Number(portfolioDetails.StartDate.split("-")[0])) {
        isValid = false;
        let node = document.getElementById(`startDate`);
        node.classList.add("is-invalid");
      }
      if (
        currYear === Number(portfolioDetails.StartDate.split("-")[0]) &&
        currMonth < Number(portfolioDetails.StartDate.split("-")[1])
      ) {
        isValid = false;
        let node = document.getElementById(`startDate`);
        node.classList.add("is-invalid");
      }
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
        Allocation: Number(ele.Allocation) / 100,
      };
    });
    // adding allocation of cash if the total allocation is less than 100
    if (totalStockAlloc < 100) {
      chosenStockAllocationFiltered.push({
        Symbol: "CASHALLOCATION",
        Allocation: (100 - totalStockAlloc) / 100,
      });
    }
    const requestBody = {
      ...portfolioDetails,
      AssetList: chosenStockAllocationFiltered,
    };
    console.log("reqbody", requestBody);
    // send to backend
    setIsLoading(true);
    if (modeOfModal == "Edit") {
      const response = await updatePortfolioAPI(
        originalPortfolioDetails.portfolioId,
        requestBody
      );
      if (response.status === 200) {
        setErrorMessage("");
        setSuccessMessage("Portfolio updated successfully.");
      } else {
        setErrorMessage("Error updating portfolio: " + response.data);
        setIsLoading(false);
        return;
      }
    } else {
      const response = await createPortfolioAPI(requestBody);
      if (response.status === 200) {
        setErrorMessage("");
        setSuccessMessage("Portfolio created successfully.");
      } else {
        setErrorMessage("Error creating portfolio: " + response.data);
        setIsLoading(false);
        return;
      }
    }
    setTimeout(() => {
      window.location.reload();
    }, 1000);
    setIsLoading(false);
    return;
  };

  // function to validate the input fields of the form
  const validateField = (e, condition, elementId) => {
    console.log("condition", condition, elementId);
    if (condition) {
      console.log("hererererererere");
      let node = document.getElementById(elementId);
      node.classList.remove("is-invalid");
      return true;
    } else {
      let node = document.getElementById(elementId);
      node.classList.add("is-invalid");
      console.log("this is node", node);
      return false;
    }
  };

  // function to add stock allocation to portfolioDetails
  const addStockAllocToPortfolio = async (stockSymbol, index) => {
    const updatedStockAllocation = [...chosenStockAllocation];
    updatedStockAllocation.forEach((ele) => {
      if (ele.id === index) {
        ele.Symbol = stockSymbol.toUpperCase();
      }
    });
    setChosenStockAllocation(updatedStockAllocation);
  };

  return (
    <div
      className="modal fade"
      id={id ? id : "createPortfolio"}
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
            {modeOfModal == "Edit" ? (
              <h1 className="modal-title fs-5" id="createPortfolioLabel">
                Edit Portfolio
              </h1>
            ) : (
              <h1 className="modal-title fs-5" id="createPortfolioLabel">
                Create Portfolio
              </h1>
            )}
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
                  {portfolioDetails && (
                    <input
                      type="text"
                      id="portfolioNameField"
                      value={portfolioDetails.PortfolioName}
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
                          `portfolioNameField`
                        );
                      }}
                    />
                  )}
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
                    value={portfolioDetails.Description}
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
                    value={portfolioDetails.Capital}
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
                      validateField(event, event.target.value > 100, `capital`);
                    }}
                  />
                  <div
                    id="validationServerCapitalFeedback"
                    className="invalid-feedback"
                  >
                    Please enter a valid positive capital above $100.
                  </div>
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
                    value={portfolioDetails.StartDate}
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
                            placeholder={ele.Symbol.toUpperCase()}
                            onBlur={(event) => {
                              console.log(
                                "currstocks list",
                                chosenStockAllocation
                              );
                              let isValid = validateField(
                                event,
                                validStocks.includes(
                                  event.target.value.toUpperCase()
                                ) &&
                                  !chosenStockAllocation.some(
                                    (obj) =>
                                      obj.Symbol ==
                                      event.target.value.toUpperCase()
                                  ),
                                `stock-${index}`
                              );
                              chosenStockAllocation.forEach((ele) => {
                                if (ele.id == index) {
                                  if (ele.Symbol == event.target.value) {
                                    isValid = true;
                                    validateField(
                                      event,
                                      true,
                                      `stock-${index}`
                                    );
                                  }
                                }
                              });
                              if (event.target.value.length === 0) {
                                isValid = true;
                              }
                              if (isValid) {
                                addStockAllocToPortfolio(
                                  event.target.value.toUpperCase(),
                                  index
                                );
                              }
                            }}
                          />
                          <div
                            id="validationServerStockFeedback"
                            className="invalid-feedback"
                          >
                            Please enter a valid, non-duplicate stock symbol.
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
                              className="btn btn-secondary"
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
                  {totalStockAlloc} %
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
                {modeOfModal == "Edit" ? "Update" : "Create"}
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
