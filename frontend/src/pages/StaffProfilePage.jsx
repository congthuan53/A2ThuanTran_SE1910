import { useEffect, useState } from "react";
import * as accountApi from "../api/accountApi";

function StaffProfilePage() {
  const [accountName, setAccountName] = useState("");
  const [accountEmail, setAccountEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    accountApi
      .getMyProfile()
      .then((profile) => {
        setAccountName(profile.accountName);
        setAccountEmail(profile.accountEmail);
      })
      .finally(() => setIsLoading(false));
  }, []);

  async function handleSubmit(event) {
    event.preventDefault();
    setErrorMessage("");
    setSuccessMessage("");
    setIsSaving(true);

    try {
      await accountApi.updateMyProfile({
        accountName: accountName,
        accountPassword: newPassword,
      });
      setSuccessMessage("Cap nhat ho so thanh cong");
      setNewPassword("");
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Cap nhat ho so that bai";
      setErrorMessage(message);
    } finally {
      setIsSaving(false);
    }
  }

  if (isLoading) {
    return <p className="loading-text">Dang tai...</p>;
  }

  return (
    <div className="page-container">
      <h1>Ho so cua toi</h1>

      <div className="white-box" style={{ maxWidth: 480 }}>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        {successMessage && <div className="success-message">{successMessage}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-field">
            <label htmlFor="accountEmail">Email (khong the thay doi)</label>
            <input id="accountEmail" type="email" value={accountEmail} disabled />
          </div>

          <div className="form-field">
            <label htmlFor="accountName">Ten tai khoan</label>
            <input
              id="accountName"
              type="text"
              value={accountName}
              onChange={(event) => setAccountName(event.target.value)}
              required
            />
          </div>

          <div className="form-field">
            <label htmlFor="newPassword">Mat khau moi (de trong neu khong doi)</label>
            <input
              id="newPassword"
              type="password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
            />
          </div>

          <button type="submit" className="button button-primary" disabled={isSaving}>
            {isSaving ? "Dang luu..." : "Luu thay doi"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default StaffProfilePage;
