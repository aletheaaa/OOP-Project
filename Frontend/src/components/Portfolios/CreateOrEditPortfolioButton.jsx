import React, { useEffect, useState } from "react";

export default function CreateOrEditPortfolioButton({
  target,
  className,
  mode,
  setModeOfModal,
  style,
}) {
  return (
    <button
      type="button"
      className={className != null ? className : "btn btn-success"}
      data-bs-toggle="modal"
      data-bs-target={target ? target : "#createPortfolio"}
      style={style ? style : { borderRadius: "20px" }}
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
