import React, { useState, useEffect } from "react";
import { getPortfolios } from "../../api/user";
import CreatePortfolioButton from "../../components/Portfolios/CreatePortfolioButton";
import ChangePasswordModal from "../../components/User/ChangePasswordModal";

export default function Dashboard() {
  const [portfolios, setPortfolios] = useState([]);
  const [userName, setUserName] = useState("");
  useEffect(() => {
    const getUserInfo = async () => {
      const portfolios = await getPortfolios();
      setUserName(userName);
      if (portfolios === null) {
        return;
      }
      console.log("portfolios", portfolios.userPortfolios);
      setPortfolios(portfolios.userPortfolios);
    };
    getUserInfo();
  }, []);

  return portfolios && portfolios.length != 0 ? (
    <div className="">
      Please select portfolio to view from side menu <ChangePasswordModal />
    </div>
  ) : (
    <div className="">
      No portfolios found. Create a portfolio to get started.{" "}
      <CreatePortfolioButton />
    </div>
  );
}
