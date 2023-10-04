import React, { useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

function Login({ setToken }) {
  const [email, setUsername] = useState();
  const [password, setPassword] = useState();

  const handleEmailChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log(`Email: ${email}, Password: ${password}`);
    axios
      .post("http://localhost:8080/api/v1/auth/authenticate", {
        email: email,
        password: password,
      })
      .then((response) => {
        console.log(response.data);
        setToken(response.data.token);
        window.location.reload(); // reload page after logging in to exit login page
      })
      .catch((error) => {
        console.log(error);
    });
  };

  return (
    <div className="container-fluid m-2 d-flex align-items-center justify-content-center">
      <div className="row">
        <div className="col border p-3">
          <form>
            <div class="mb-3">
              <label for="exampleInputEmail1" class="form-label">Email address</label>
              <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" onChange={handleEmailChange} />
            </div>
            <div class="mb-3">
              <label for="exampleInputPassword1" class="form-label">Password</label>
              <input type="password" class="form-control" id="exampleInputPassword1" onChange={handlePasswordChange} />
            </div>
            <button type="submit" class="btn btn-primary" onClick={handleSubmit}>Submit</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;