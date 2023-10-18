import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { authenticate } from "../../api/authToken";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

import Alert from "../../components/Common/Alert";

function Login(props) {
  const [email, setUsername] = useState();
  const [password, setPassword] = useState();
  const [alerts, setAlerts] = useState();
  const navigate = useNavigate();

  const handleEmailChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log(`Email: ${email}, Password: ${password}`);
    let authentication = await authenticate(email, password);
    console.log(authentication);
    if (authentication.status === "success") {
      props.setToken(authentication.data.token);
      props.setId(authentication.data.id);
      navigate("/", {replace: true});
      window.location.reload(); // reload page after logging in to exit login page
    } else {
      setAlerts(<Alert color="danger" code={authentication.code} message={authentication.message} />);
    }
  };

  const handleRedirect = (event) => {
    event.preventDefault();
    setAlerts(null);
    navigate("/register");
  };

  return (
    <>
      { alerts }
      <div className="d-flex my-2 mx-5 p-3 align-items-center justify-content-center border">
        <form>
          <h3>Login</h3>
          <div className="mb-3">
            <label for="exampleInputEmail1" className="form-label">Email address</label>
            <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" onChange={handleEmailChange} />
          </div>
          <div className="mb-3">
            <label for="exampleInputPassword1" className="form-label">Password</label>
            <input type="password" className="form-control" id="exampleInputPassword1" onChange={handlePasswordChange} />
          </div>
          <div className="mb-3">
            <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Submit</button>
          </div>
          <div className="mb-3">
            <small><button className="btn btn-outline-primary" onClick={handleRedirect}>Not a User yet? Register here!</button></small>
          </div>
        </form>
      </div>
    </>
  );
}

export default Login;