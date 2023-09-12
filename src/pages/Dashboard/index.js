import React from "react";
import DashboardCard from "../../components/DashboardCard";

export default function Dashboard() {
  return (
    <>
      <div className="container my-3">
        <div className="row">
            <DashboardCard title="Earnings (Monthly)" value="$40,000" iconClassName="bi-coin" colorClassName="primary" />
            <DashboardCard title="Earnings (Annual)" value="$360,000" iconClassName="bi-coin" colorClassName="success" />
            <DashboardCard title="Task Completion Status" value="50%" iconClassName="bi-clipboard" colorClassName="secondary" />
            <DashboardCard title="Pending Requests" value="18" iconClassName="bi-chat" colorClassName="warning" />
        </div>
      </div>
    </>
  );
}
