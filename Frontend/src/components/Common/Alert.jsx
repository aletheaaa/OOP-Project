import React from "react";

function Alert(props) {
  return (
    <div className={"alert alert-dismissible fade show alert-" + props.color} role="alert">
      <strong>{props.code}</strong> {props.message}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  );
}

export default Alert;