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

const labels = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

export const data = {
  labels,
  datasets: [
    {
      label: "Actual Performance",
      data: [8, 7, 6, 7, 9, 7, 9, 6, 9, 7, 8, 9],
      borderColor: "rgb(255, 99, 132)",
      backgroundColor: "rgba(255, 99, 132, 0.5)",
    },
    {
      label: "Index",
      data: [2, 3, 5, 2, 3, 4, 1, 5, 1, 3, 2, 7],
      borderColor: "rgb(53, 162, 235)",
      backgroundColor: "rgba(53, 162, 235, 0.5)",
    },
  ],
};

export default function AreaChart() {
  return (
    <div>
      <Line options={options} data={data} />
    </div>
  );
}
