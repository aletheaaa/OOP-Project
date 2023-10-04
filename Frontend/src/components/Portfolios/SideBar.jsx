import React, { useState, useEffect } from "react";

function NavBarItem({}) {
  const [userPortfolios, setUserPortfolios] = useState([]);
  const [chosenPortfolio, setChosenPortfolio] = useState([]);

  useEffect(() => {
    // getting the user's portfolio
    setUserPortfolios([
      {
        portfolioId: "1",
        name: "Retirement",
        description: "retirement plan",
        totalCapital: 1000,
        Trades: [],
      },
      {
        portfolioId: "2",
        name: "Retirement 2",
        description: "retirement plan",
        totalCapital: 1000,
        Trades: [],
      },
      {
        portfolioId: "3",
        name: "Retirement 3",
        description: "retirement plan",
        totalCapital: 1000,
        Trades: [],
      },
    ]);
  }, []);

  return (
    <div className="bg-light sidebar pt-5">
      {userPortfolios.map((portfolio) => {
        return (
          <div
            key={portfolio.portfolioId}
            className={`text-center mt-3 border mx-2 ${
              chosenPortfolio.includes(portfolio.portfolioId)
                ? "bg-secondary"
                : ""
            }`}
            style={{ cursor: "pointer" }}
            onClick={() => {
              console.log(chosenPortfolio);
              console.log(portfolio.portfolioId);
              if (chosenPortfolio.includes(portfolio.portfolioId)) {
                console.log("i cam here");
                setChosenPortfolio(
                  chosenPortfolio.filter(
                    (portfolioId) => portfolioId != portfolio.portfolioId
                  )
                );
              } else {
                console.log([...chosenPortfolio, portfolio.portfolioId]);
                setChosenPortfolio([...chosenPortfolio, portfolio.portfolioId]);
              }
            }}
          >
            {portfolio.name}
          </div>
        );
      })}
    </div>
  );
}

export default NavBarItem;