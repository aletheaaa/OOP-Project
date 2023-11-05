import React, { useState } from "react";
import { comparePortfolio } from "../../api/portfolio";

function ComparePortfolios(props) {
  const [portfolio1, setPortfolio1] = useState();
  const [portfolio2, setPortfolio2] = useState();
  const [isLoading, setIsLoading] = useState(false);

  const portfolios = props.portfolios;

  const handlePortfolio1Change = (event) => {
    setPortfolio1(event.target.value);
    console.log(portfolio1); // undefined
  };

  const handlePortfolio2Change = (event) => {
    setPortfolio2(event.target.value);
  };
  
  const handleCompare = async (event) => {
    event.preventDefault();
    setIsLoading(true);
    let compare = await comparePortfolio(portfolio1, portfolio2);
    setIsLoading(false);
    console.log(compare);
  }

  return (
    <>
    <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#comparePortfolio">
      Compare Portfolios
    </button>

    <div className="modal fade" id="comparePortfolio" tabindex="-1" aria-labelledby="comparePortfolioLabel" aria-hidden="true">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h1 className="modal-title fs-5" id="comparePortfolioLabel">Compare Portfolios</h1>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <div className="container">
              <div className="row row-cols-2">
                <div className="col">
                  <select name="portfolio1" className="form-control" id="portfolio1" onChange={handlePortfolio1Change}>
                    {portfolios &&
                      portfolios.length > 0 &&
                      portfolios.map((portfolio) => (
                      <option value={portfolio.portfolioName}>{portfolio.portfolioName}</option>
                    )
                    )}
                  </select>
                </div>
                <div className="col">
                  <select name="portfolio2" className="form-control" id="portfolio1" onChange={handlePortfolio2Change}>
                    {portfolios &&
                      portfolios.length > 0 &&
                      portfolios.map((portfolio) => (
                      <option value={portfolio.portfolioName}>{portfolio.portfolioName}</option>
                    )
                    )}
                  </select>
                </div>
              </div>
            </div>
            <div className="container">
              
            </div>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            {isLoading ? (
              <button className="btn btn-primary" type="button" disabled>
                <span
                  className="spinner-border spinner-border-sm mr-2"
                  role="status"
                  aria-hidden="true"
                ></span>{" "}
                Loading...
              </button>
            ) : (
              <button
                type="submit"
                className="btn btn-block btn-primary"
                onClick={handleCompare}
              >
                Compare
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
    </>
  )
  
}

export default ComparePortfolios;