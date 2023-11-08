import React, { useEffect, useState } from "react";
import { getAccessLogsAPI } from "../../api/accessLogs";

export default function AccessLogs() {
  const [accessLogs, setAccessLogs] = useState([]);
  const [error, setError] = useState("");

  const email = sessionStorage.getItem("email");
  useEffect(() => {
    if (!email) {
      setError("Please login to view access logs");
      return;
    }
    const getAccessLogs = async () => {
      const response = await getAccessLogsAPI(email);
      console.log("this is response from acfesslogs", response);
      if (response.status != 200) {
        setError(response.data);
        return;
      }
      setAccessLogs(response.data);
    };
    getAccessLogs();
  }, [email]);

  return (
    <>
      <div className="container my-3 mt-5">
        <h3 className="fw-bold">Logs</h3>
        {error ? (
          <div className="text text-danger">{error}</div>
        ) : (
          <table className="table mt-4">
            <thead>
              <tr>
                <th scope="col">IP Address</th>
                <th scope="col">Method</th>
                <th scope="col">Route</th>
                <th scope="col">Status</th>
                <th scope="col">Time</th>
              </tr>
            </thead>
            <tbody>
              {accessLogs &&
                accessLogs.length > 0 &&
                accessLogs.map((element, index) => {
                  const splitData = element.split(" ");
                  const IPAddress = splitData[0];
                  const method = splitData[1];
                  const route = splitData[2];
                  const status = splitData[3];
                  const time = splitData[4];
                  return (
                    <tr key={index}>
                      <td>{IPAddress}</td>
                      <td>{method}</td>
                      <td>{route}</td>
                      <td>{status}</td>
                      <td>{time}</td>
                    </tr>
                  );
                })}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}
