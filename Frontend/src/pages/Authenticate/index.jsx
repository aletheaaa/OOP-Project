import React, { useState } from "react";
import { authenticate } from "../../api/authenticate";
import "bootstrap/dist/css/bootstrap.min.css";

import Alert from "../../components/Common/Alert";

function Authenticate(props) {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
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

  const handleSubmit = async (event) => {
    event.preventDefault();
    let authentication = await authenticate(registering, email, password);
    console.log(authentication);
    if (authentication.status == "200") {
      props.setToken(authentication.data.token);
      props.setId(authentication.data.id);
      setErrors([]);
      setNotifications([]);
      window.location.reload(); // reload page after logging in to exit login page
    }
    else {
      setErrors([...errors, {
        code: "Successful!",
        message: authentication.message
      }]);
    }
  };

  const handleToggleRegistering = (event) => {
    event.preventDefault();
    setRegistering(!registering);
    return false;
  }

  return (
    <>
      { errors && errors.length > 0 && errors.map( error => (
        <Alert color="danger" code={error.code} message={error.message} />
      )) }
      { notifications && notifications.length > 0 && notifications.map( notification => (
        <Alert color="success" code={notification.code} message={notification.message} />
      )) }
      <div className="d-flex my-2 mx-5 p-3 align-items-center justify-content-center border">
        <form>
          <h3>{ registering ? "Register" : "Login" }</h3>
          <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">Email address</label>
            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" onChange={handleEmailChange} />
          </div>
          <div className="mb-3">
            <label for="exampleInputPassword1" className="form-label">Password</label>
            <input type="password" className="form-control" id="exampleInputPassword1" onChange={handlePasswordChange} />
          </div>
          <div className="mb-3">
            <button type="submit" className="btn btn-block btn-primary" onClick={handleSubmit}>Submit</button>
          </div>
          <div className="mb-3">
            <p className="form-text" >
              { registering ? registeringMessage : loginMessage }
              <a className="link-primary" onClick={handleToggleRegistering}>here!</a>
            </p>
          </div>
        </form>
      </div>
    </>
  );
}

export default Authenticate;