import React, { useEffect, useState } from "react";
import { getAccessLogsAPI } from "../../api/accessLogs";

export default function AccessLogs() {
  const [accessLogs, setAccessLogs] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const getAccessLogs = async () => {
      const email = "usertwo@gmail.com";
      const response = await getAccessLogsAPI(email);
      console.log("this is response from acfesslogs", response);
      if (response.status != 200) {
        setError(response.data);
        return;
      }
      setAccessLogs(response.data);
    };
    getAccessLogs();
    // setAccessLogs([
    //   {
    //     timeStamp: "2021-09-30 13:00:00",
    //     status: "Success",
    //     user: "userone@gmail.com",
    //     description: "User logged in",
    //   },
    // ]);
  }, []);

  return (
    <>
      <div className="container my-3">
        <h3 className="fw-bold">Logs</h3>
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
      </div>
    </>
  );
}
