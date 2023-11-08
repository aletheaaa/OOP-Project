import React from "react";

export default function ChangePasswordButton({ className }) {
  return (
    <button
      type="button"
      className={className != null ? className : "btn btn-success"}
      data-bs-toggle="modal"
      data-bs-target="#changePasswordModal"
    >
      Change Password
    </button>
  );
}
