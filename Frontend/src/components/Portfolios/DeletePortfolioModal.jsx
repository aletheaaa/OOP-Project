import React, { useEffect, useState } from "react";
import { deletePortfolioAPI } from "../../api/portfolio";

export default function DeletePortfolioModal({ portfolioId }) {
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleDeletePortfolio = async () => {
    setIsLoading(true);
    console.log("deleting portfolio");
    let response = await deletePortfolioAPI(portfolioId);
    if (response.status != 200) {
      console.log("error deleting portfolio: ", response.data);
      setErrorMessage(response.data);
      setIsLoading(false);
      return;
    }
    setErrorMessage("");
    setSuccessMessage("Portfolio deleted successfully!");
    setIsLoading(false);
    setTimeout(() => {
      window.location.href = "/";
    }, 1000);
  };

  return (
    <div
      className="modal fade"
      id="deletePortfolio"
      tabIndex="-1"
      aria-labelledby="deletePortfolio"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h1 className="modal-title fs-5" id="createPortfolioLabel">
              Delete Portfolio
            </h1>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">Are you sure?</div>
          <div class="modal-footer">
            {successMessage && (
              <div className="text-success w-100 text-center mb-1" role="alert">
                {successMessage}
              </div>
            )}
            {errorMessage && (
              <div className="text-danger w-100 text-center mb-1" role="alert">
                {errorMessage}
              </div>
            )}
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
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
                class="btn btn-primary"
                onClick={handleDeletePortfolio}
              >
                Confirm
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
