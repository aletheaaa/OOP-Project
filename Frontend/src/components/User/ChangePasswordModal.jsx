import React, {useState} from "react";
import {changePassword} from "../../api/settings";

export default function ChangePasswordModal() {
  const [oldPassword, setOldPassword] = useState();
  const [newPassword, setNewPassword] = useState();
  const [confirmPassword, setConfirmPassword] = useState();

  const [errors, setErrors] = useState(""); // For RED Errors
  const [notifications, setNotifications] = useState(""); // For other messages e.g. Registration successful

  const handleOldPasswordChange = (event) => {
    setOldPassword(event.target.value);
  }

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  }

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    let changePasswordStatus = await changePassword(oldPassword, newPassword, confirmPassword);
    console.log(changePasswordStatus);
    if (changePasswordStatus.status == "200") {
      setErrors("");
      setNotifications(changePasswordStatus.message);
    } else {
      setNotifications("");
      setErrors(changePasswordStatus.message);
    }
  }

  return (
    <>
      <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changePasswordModal">
        Change Password
      </button>

      <div className="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h1 className="modal-title fs-5" id="changePasswordModalLabel">Change Password</h1>
              <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div className="modal-body">
              <div className="text-danger">{errors}</div>
              <div className="text-success">{notifications}</div>
              <form>
                <div className="mb-3">
                  <label for="oldPassword" className="form-label">Old Password</label>
                  <input type="email" className="form-control" id="oldPassword" aria-describedby="emailHelp" onChange={handleOldPasswordChange} />
                </div>
                <div className="mb-3">
                  <label for="newPassword" className="form-label">New Password</label>
                  <input type="password" className="form-control" id="newPassword" onChange={handleNewPasswordChange} />
                </div>
                <div className="mb-3">
                  <label for="confirmPassword" className="form-label">Confirm New Password</label>
                  <input type="password" className="form-control" id="confirmPassword" onChange={handleConfirmPasswordChange} />
                </div>
                <div className="mb-3">
                  <button type="submit" className="btn btn-block btn-primary" onClick={handleSubmit}>Submit</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}