import React from "react";

function ErrorPage() {
  return (
    <>
      <div className="container my-3 d-flex justify-content-center">
        <div className="row">
          <div className="col-12 text-center">
            <h1>Error 404: Page Not Found</h1>
          </div>
          <div className="col-12 text-center">
            <a href="/" className="text-center">Return to Dashboard</a>
          </div>
          
        </div>
      </div>
    </>
  );
}

export default ErrorPage;