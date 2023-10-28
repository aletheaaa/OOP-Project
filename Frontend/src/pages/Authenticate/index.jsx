import React, { useState } from "react";
import { authenticate, resetPassword } from "../../api/authenticate";
import "bootstrap/dist/css/bootstrap.min.css";

import Alert from "../../components/Common/Alert";

function Authenticate(props) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [resetEmail, setResetEmail] = useState();
  const [errors, setErrors] = useState([]); // For RED Errors
  const [notifications, setNotifications] = useState([]); // For other messages e.g. Registration successful

  // Differentiate between users logging in and users registering
  const [registering, setRegistering] = useState(false);
  const registeringMessage = "Already a User? Login ";
  const loginMessage = "Not a User yet? Register ";

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleResetEmailChange = (event) => {
    setResetEmail(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    let authentication = await authenticate(registering, email, password);
    console.log("thisis authentication return", authentication);
    if (authentication.status <= "400") {
      props.setToken(authentication.data.token);
      props.setId(authentication.data.id);
      setErrors([]);
      setNotifications([]);
      window.location.reload(); // reload page after logging in to exit login page
    } else {
      setErrors([
        ...errors,
        {
          code: "Error " + authentication.response.status,
          message: authentication.response.data,
        },
      ]);
    }
  };

  const handleForgotPassword = async (event) => {
    event.preventDefault();
    let forgotPassword = await resetPassword(resetEmail);
    console.log(forgotPassword);
  };

  const handleToggleRegistering = (event) => {
    event.preventDefault();
    setRegistering(!registering);
    return false;
  };

  return (
    <>
      {errors &&
        errors.length > 0 &&
        errors.map((error) => (
          <Alert color="danger" code={error.code} message={error.message} />
        ))}
      {notifications &&
        notifications.length > 0 &&
        notifications.map((notification) => (
          <Alert
            color="success"
            code={notification.code}
            message={notification.message}
          />
        ))}
      <div className="d-flex my-2 mx-5 p-3 align-items-center justify-content-center border">
        <form>
          <h3>{registering ? "Register" : "Login"}</h3>
          <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">
              Email address
            </label>
            <input
              type="email"
              className="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              onChange={handleEmailChange}
            />
          </div>
          <div className="mb-3">
            <label for="exampleInputPassword1" className="form-label">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="exampleInputPassword1"
              onChange={handlePasswordChange}
            />
          </div>
          <div className="mb-3">
            <button
              type="submit"
              className="btn btn-block btn-primary"
              onClick={handleSubmit}
            >
              Submit
            </button>
          </div>
          <div className="mb-3">
            <p className="form-text">
              Forgot password? Reset password{" "}
              <a
                className="link-primary"
                data-bs-toggle="modal"
                data-bs-target="#forgotPassword"
              >
                here!
              </a>
            </p>
            <p className="form-text">
              {registering ? registeringMessage : loginMessage}
              <a className="link-primary" onClick={handleToggleRegistering}>
                here!
              </a>
            </p>
          </div>
        </form>
      </div>

      <div
        className="modal fade"
        id="forgotPassword"
        tabindex="-1"
        aria-labelledby="forgotPasswordLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h1 className="modal-title fs-5" id="forgotPasswordLabel">
                Forgot Password
              </h1>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <div className="mb-3">
                <label for="resetEmail" className="form-label">
                  Email address
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="resetEmail"
                  onChange={handleResetEmailChange}
                />
                <p className="form-text">
                  We will send you a password reset link via email.
                </p>
              </div>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={handleForgotPassword}
              >
                Reset Password
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Authenticate;
