import React, { useState } from "react";
import { updateSettings } from "../../api/settings";

import Alert from "../../components/Common/Alert";

function UserSettings() {
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();

  const [errors, setErrors] = useState([]); // For RED Errors
  const [notifications, setNotifications] = useState([]); // For other messages e.g. Registration successful

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  }

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  }

  const handleSubmit = async (event) => {
    await updateSettings(firstName, lastName);
  }

  return (
    <div>Settings</div>
  );
}

export default UserSettings;