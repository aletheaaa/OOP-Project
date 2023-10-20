import React from "react";
import { useNavigate } from "react-router-dom";

function StockSearchButton() {
  const navigate = useNavigate();
  let path = "/stocks";

  const redirect = (event) => {
    event.preventDefault();
    path = "/stocks/" + document.getElementById("stockSymbol").value;
    navigate(path);
    window.location.reload();
  };

  return (
    <button type="submit" className="btn btn-outline-success" onClick={ redirect } >Search</button>
  );
}

export default StockSearchButton;