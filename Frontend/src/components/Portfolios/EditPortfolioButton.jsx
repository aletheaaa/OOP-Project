import React, { useEffect, useState } from "react";

export default function CreatePortfolioButton({ className }) {
  return (
    <button
      type="button"
      className={className != null ? className : "btn btn-success"}
      data-bs-toggle="modal"
      data-bs-target="#editPortfolio"
    >
      Edit Portfolio
    </button>
  );
}
