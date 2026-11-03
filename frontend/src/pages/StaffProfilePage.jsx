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
      setSuccessMessage("Cập nhật hồ sơ thành công");
      setNewPassword("");
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Cập nhật hồ sơ thất bại";
      setErrorMessage(message);
    } finally {
      setIsSaving(false);
    }
  }

  if (isLoading) {
    return <p className="loading-text">Đang tải...</p>;
  }

  return (
    <div className="page-container">
      <h1>Hồ sơ của tôi</h1>

      <div className="white-box" style={{ maxWidth: 480 }}>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        {successMessage && <div className="success-message">{successMessage}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-field">
            <label htmlFor="accountEmail">Email (không thể thay đổi)</label>
            <input id="accountEmail" type="email" value={accountEmail} disabled />
          </div>

          <div className="form-field">
            <label htmlFor="accountName">Tên tài khoản</label>
            <input
              id="accountName"
              type="text"
              value={accountName}
              onChange={(event) => setAccountName(event.target.value)}
              required
            />
          </div>

          <div className="form-field">
            <label htmlFor="newPassword">Mật khẩu mới (để trống nếu không đổi)</label>
            <input
              id="newPassword"
              type="password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
            />
          </div>

          <button type="submit" className="button button-primary" disabled={isSaving}>
            {isSaving ? "Đang lưu..." : "Lưu thay đổi"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default StaffProfilePage;
