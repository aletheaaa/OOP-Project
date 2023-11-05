import React, { useEffect, useState } from "react";

export default function DeletePortfolioButton({ className }) {
  return (
    <button
      type="button"
      className={className != null ? className : "btn btn-danger"}
      data-bs-toggle="modal"
      data-bs-target="#deletePortfolio"
      style={{ borderRadius: "20px" }}
    >
      Delete Portfolio
    </button>
  );
}
