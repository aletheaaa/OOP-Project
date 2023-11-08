# IS442 Goldman Sachs SMU Portfolio Analyzer Application

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
  - [Full Feature List](#full-feature-list)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Credits](#credits)

## Introduction

Our Portfolio Analyzer Application is a tool designed to help individuals manage and analyze their investment portfolios. Whether you're a casual investor or a seasoned trader, this application provides a user-friendly interface to track and evaluate your investments.

## Features

- Portfolio Management: Create, Read, Update & Delete (CRUD) of user portfolios.
- Real-Time Updates: Fetching of real-time market data via AlphaVantage API.
- Performance Metrics: Present Value, Net Profit, CAGR & Sharpe Ratio of user portfolios.
- Portfolio Comparison: Side-by-side comparison of 2 or more portfolios owned by user.
- Security Features: Secure authentication and password protection and password resetting functionalities.
- Dynamic Frontend UI: Responsive and intuitive User Interface for ease of use.

### Full Feature List

- Create Portfolio
- Add Stocks to Portfolio
- Update Portfolio
- Delete Portfolio
- Compare Portfolios
- Login
- Register
- Change Password
- Forgot (Reset) Password

## Getting Started

Follow these steps to get the Portfolio Analyzer Application up and running on your local machine.

### Prerequisites

- [Node.js](https://nodejs.org/)
- [npm](https://www.npmjs.com/)
- API key for AlphaVantage

### Installaton

Run the below command in your terminal upon loading up our application.

```
cd Frontend
npm install
```

## Usage

In order to run the application locally, please ensure that the folllowing additional prerequisites are present on your system:

- Some form of SQL server, we use MySQL
- Java Runtime Version 8 or above
- Open JDK Version 17 or above

1. Run the setup.sql file located in the "Database" folder
2. Open a terminal and navigate to the backend folder and run the mvnw file with the following commands to deploy the backend database:

```
cd Backend
./mvnw spring-boot:run
```

3. Open another terminal and navigate to the frontend folder and start the application using npm start:

```
cd Frontend
npm start
```

## Credits

G1T6 - Douglas, Zhi Wei, Jaden, Joseph, Alethea, Anushka
