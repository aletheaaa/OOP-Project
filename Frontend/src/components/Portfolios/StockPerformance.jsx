import React, { useEffect, useState } from "react";

export default function StockPerformanceTable() {
  const [trades, setTrades] = useState([]);

  useEffect(() => {
    // TODO: get the user's trades from backend
    const getUserTrades = async () => {
      // const tradesFromServer = await fetchTrades();
      const tradesFromServer = [
        {
          name: "AAPL",
          price: 100,
          quantity: 1,
          capitalGain: 100,
          returns: 100,
        },
        {
          name: "TSLA",
          price: 200,
          quantity: 2,
          capitalGain: 200,
          returns: 200,
        },
      ];
      setTrades(tradesFromServer);
    };
    getUserTrades();
  }, []);

  // Individual stocks performance table
  return (
    <table className="px-5 w-75 mx-auto text-center border">
      <thead style={{ backgroundColor: "lightgray" }}>
        <tr>
          <th className="px-3">NASDAQ</th>
          <th className="px-3">PRICE</th>
          <th className="px-3">QTY</th>
          <th className="px-3">CAPITAL GAINS</th>
          <th className="px-3">RETURNS</th>
          <th className="px-3">CAPITAL ALLOCATION</th>
        </tr>
      </thead>
      <tbody>
        {trades.map((element, index) => {
          return (
            <tr key={index}>
              <td className="px-3">{element.name}</td>
              <td className="px-3">{element.price}</td>
              <td className="px-3">{element.quantity}</td>
              <td className="px-3">{element.capitalGain}</td>
              <td className="px-3">{element.returns}</td>
              <td className="px-3">100%</td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
}
