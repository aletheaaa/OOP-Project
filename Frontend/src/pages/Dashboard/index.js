import React from "react";
import AreaChart from "../../components/AreaChart";
import DoughnutChart from "../../components/DoughnutChart";
import DashboardCard from "../../components/DashboardCard";

export default function Dashboard() {
  return (
    <>
      <div className="container my-3">
        <div className="row">
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Earnings (Monthly)"
              value="$40,000"
              iconClassName="bi-coin"
              colorClassName="primary"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Earnings (Annual)"
              value="$360,000"
              iconClassName="bi-coin"
              colorClassName="success"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Task Completion Status"
              value="50%"
              iconClassName="bi-clipboard"
              colorClassName="secondary"
            />
          </div>
          <div className="col-xl-3 col-md-6 mb-4">
            <DashboardCard
              title="Pending Requests"
              value="18"
              iconClassName="bi-chat"
              colorClassName="warning"
            />
          </div>
        </div>
        <div className="row">
          <div className="col-xl-8 col-lg-7">
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Returns</h6>
              </div>
              <div class="card-body">
                <div class="chart-area">
                  <AreaChart />
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-4 col-lg-5">
            <div class="card shadow mb-4">
              <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">
                  % of Capital Allocated to Different Stocks
                </h6>
              </div>
              <div class="card-body">
                <div class="chart-pie pt-4">
                  <DoughnutChart />
                </div>
              </div>
            </div>
          </div>
          {/* <div className="col-6 ml-3 d-flex justify-content-center">
        </div> */}
        </div>
      </div>
    </>
  );
}
