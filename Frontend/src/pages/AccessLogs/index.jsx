import React, { useEffect, useState } from "react";

export default function AccessLogs() {
  const [accessLogs, setAccessLogs] = useState([]);

  useEffect(() => {
    setAccessLogs([
      {
        timeStamp: "2021-09-30 13:00:00",
        status: "Success",
        user: "userone@gmail.com",
        description: "User logged in",
      },
    ]);
  }, []);

  return (
    <>
      <div className="container my-3">
        <h3 className="fw-bold">Logs</h3>
        <table className="table mt-4">
          <thead>
            <tr>
              <th>Timestamp</th>
              <th>Status</th>
              <th>User</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            {accessLogs &&
              accessLogs.length > 0 &&
              accessLogs.map((element, index) => {
                return (
                  <tr key={index}>
                    <td>{element.timeStamp}</td>
                    <td>{element.status}</td>
                    <td>{element.user}</td>
                    <td>{element.description}</td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </div>
    </>
  );
}
