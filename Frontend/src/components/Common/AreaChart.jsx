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

// export const options = {
//   responsive: true,
//   plugins: {
//     legend: {
//       position: "top",
//     },
//     // title: {
//     //   display: true,
//     //   text: "Returns",
//     // },
//   },
// };

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

export default function AreaChart({ data, options }) {
  // const data = {
  //   labels: ["2021-01", "2021-02", "2021-03", "2022-01", "2022-02", "2022-03"],
  //   datasets: [
  //     {
  //       label: "Data Points",
  //       data: [10, 20, 15, 30, 25, 40],
  //       borderColor: "blue",
  //       fill: false,
  //     },
  //   ],
  // };

  // const options = {
  //   scales: {
  //     x: {
  //       ticks: {
  //         // Include a dollar sign in the ticks
  //         callback: function (value, index, ticks) {
  //           console.log("value", value);
  //           let dataPoint = this.getLabelForValue(value).split("-");
  //           if (dataPoint[1] == "01") {
  //             return dataPoint[0];
  //           } else {
  //             return "";
  //           }
  //         },
  //       },
  //     },
  //   },
  // };
  console.log("topions", options);
  return (
    <div>
      <Line options={options} data={data} />
    </div>
  );
}
