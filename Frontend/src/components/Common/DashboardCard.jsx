import React from "react";

function DashboardCard({title, value, iconClassName, colorClassName}) {
    return (
      <div className={"card shadow py-2 border border-5 border-top-0 border-end-0 border-bottom-0 border-" + colorClassName}>
        <div className="card-body">
          <div className="row g-0 align-items-center">
            <div className="col mr-2">
              <div className={"text-uppercase small text-" + colorClassName}>
              {title}</div>
              <div className="fs-5 fw-bolder mb-0">{value}</div>
            </div>
            <div className="col-auto">
              <i className={"fs-1 fw-bold text-secondary text-opacity-50 bi " + iconClassName}></i>
            </div>
          </div>
        </div>
      </div>
    );
}

export default DashboardCard;