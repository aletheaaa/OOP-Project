import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

function Register({ setToken }) {
  const [email, setUsername] = useState();
  const [password, setPassword] = useState();
  const navigate = useNavigate();

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
      .post("http://localhost:8080/api/v1/auth/register", {
        email: email,
        password: password,
      })
      .then((response) => {
        console.log(response.data);
        setToken(response.data.token);
        window.location.reload(); // reload page after logging in to exit login page
      })
      .catch((e) => {
        console.log(e.message);
    });
  };
    
  const handleRedirect = (event) => {
    event.preventDefault();
    navigate("/login");
  };
  

  return (
    <div className="d-flex my-2 mx-5 p-3 align-items-center justify-content-center border">
      <form>
        <h3>Register</h3>
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
          <small><button className="btn btn-outline-primary" onClick={handleRedirect}>Already have an account? Login here!</button></small>
        </div>
      </form>
    </div>
  );
}

export default Register;