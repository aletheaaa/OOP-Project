import React from "react";
import DoughnutChart from "../Common/DoughnutChart";

function PortfolioDoughnutChart({ asssetAllocation, title, tableHeaders }) {
  return (
    <div className="card shadow position-static mb-4">
      <div className="card-header py-3">
        <h6 className="m-0 font-weight-bold text-primary">{title}</h6>
      </div>
      <div className="card-body row">
        <div className="chart-area w-50">
          {asssetAllocation && Object.keys(asssetAllocation).length > 0 && (
            <DoughnutChart data={asssetAllocation} />
          )}
        </div>
        <div className="w-50 d-flex align-items-center">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">{tableHeaders[0]}</th>
                <th scope="col">{tableHeaders[1]}</th>
              </tr>
            </thead>
            <tbody>
              {asssetAllocation &&
                asssetAllocation.labels &&
                asssetAllocation.labels.length > 0 &&
                asssetAllocation.labels.map((element, index) => {
                  return (
                    <tr key={index}>
                      <td>{element}</td>
                      <td>
                        {asssetAllocation.datasets[0].data[index].toFixed(2)}%
                      </td>
                    </tr>
                  );
                })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default PortfolioDoughnutChart;
