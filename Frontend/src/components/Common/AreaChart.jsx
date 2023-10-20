import React, { useState, useEffect } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    // title: {
    //   display: true,
    //   text: "Returns",
    // },
  },
};

// export const options = {
//   maintainAspectRatio: false,
//   plugins: {
//     legend: {
//       position: "top",
//     },
//     title: {
//       display: true,
//       text: "Returns",
//     },
//   },
//   layout: {
//     padding: {
//       left: 10,
//       right: 25,
//       top: 25,
//       bottom: 0,
//     },
//   },
//   legend: {
//     display: false,
//   },
//   tooltips: {
//     backgroundColor: "rgb(255,255,255)",
//     bodyFontColor: "#858796",
//     titleMarginBottom: 10,
//     titleFontColor: "#6e707e",
//     titleFontSize: 14,
//     borderColor: "#dddfeb",
//     borderWidth: 1,
//     xPadding: 15,
//     yPadding: 15,
//     displayColors: false,
//     intersect: false,
//     mode: "index",
//     caretPadding: 10,
//   },
// };

export default function AreaChart(data) {
  return (
    <div>
      <Line options={options} data={data.data} />
    </div>
  );
}
