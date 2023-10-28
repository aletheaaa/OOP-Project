import React, { useEffect, useState } from "react";

export default function CreateOrEditPortfolioButton({
  target,
  className,
  mode,
  setModeOfModal,
}) {
  return (
    <button
      type="button"
      className={className != null ? className : "btn btn-success"}
      data-bs-toggle="modal"
      data-bs-target={target ? target : "#createPortfolio"}
      onClick={() => {
        if (setModeOfModal) {
          setModeOfModal(mode);
        }
      }}
    >
      {mode} Portfolio
    </button>
  );
}
