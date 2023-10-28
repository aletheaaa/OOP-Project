import React, { useState, useEffect } from "react";
import { getPortfolios } from "../../api/user";
import CreateOrEditPortfolioButton from "../../components/Portfolios/CreateOrEditPortfolioButton";
import ChangePasswordModal from "../../components/User/ChangePasswordModal";

export default function Dashboard() {
  const [portfolios, setPortfolios] = useState([]);
  const [userName, setUserName] = useState("");
  useEffect(() => {
    const getUserInfo = async () => {
      const portfolios = await getPortfolios();
      if (portfolios.status != 200) {
        console.log("error getting user portfolios");
        return;
      }
      setUserName(userName);
      if (portfolios === null) {
        return;
      }
      console.log("portfolios", portfolios.userPortfolios);
      setPortfolios(portfolios.data.userPortfolios);
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
      <CreateOrEditPortfolioButton mode="Create" />
    </div>
  );
}
