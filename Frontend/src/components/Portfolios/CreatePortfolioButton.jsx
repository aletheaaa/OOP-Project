import React, { useEffect, useState } from "react";

export default function CreatePortfolioButton() {
  return (
    <button
      type="button"
      className="btn btn-success"
      data-bs-toggle="modal"
      data-bs-target="#createPortfolio"
    >
      Create Portfolio
    </button>
  );
}
