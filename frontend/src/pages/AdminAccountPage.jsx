import { useEffect, useState } from "react";
import * as accountApi from "../api/accountApi";
import Modal from "../components/Modal";
import ConfirmDialog from "../components/ConfirmDialog";

const ROLE_ADMIN = 1;
const ROLE_STAFF = 2;

const EMPTY_FORM_DATA = {
  accountName: "",
  accountEmail: "",
  accountPassword: "",
  accountRole: ROLE_STAFF,
};

function AdminAccountPage() {
  const [accounts, setAccounts] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  // null = modal is closed. An object = modal is open (empty object means "create new")
  const [editingAccount, setEditingAccount] = useState(null);
  const [formData, setFormData] = useState(EMPTY_FORM_DATA);
  const [formErrorMessage, setFormErrorMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const [deleteTargetId, setDeleteTargetId] = useState(null);
  const [listErrorMessage, setListErrorMessage] = useState("");

  useEffect(() => {
    loadAccounts();
  }, []);

  function loadAccounts() {
    setIsLoading(true);
    accountApi
      .getAllAccounts(keyword)
      .then(setAccounts)
      .finally(() => setIsLoading(false));
  }

  function handleSearchSubmit(event) {
    event.preventDefault();
    loadAccounts();
  }

  function openCreateModal() {
    setEditingAccount({});
    setFormData(EMPTY_FORM_DATA);
    setFormErrorMessage("");
  }

  function openEditModal(account) {
    setEditingAccount(account);
    setFormData({
      accountName: account.accountName,
      accountEmail: account.accountEmail,
      accountPassword: "",
      accountRole: account.accountRole,
    });
    setFormErrorMessage("");
  }

  function closeModal() {
    setEditingAccount(null);
  }

  function handleFormFieldChange(fieldName, value) {
    setFormData((previousData) => ({ ...previousData, [fieldName]: value }));
  }

  async function handleFormSubmit(event) {
    event.preventDefault();
    setFormErrorMessage("");
    setIsSaving(true);

    const requestData = {
      accountName: formData.accountName,
      accountEmail: formData.accountEmail,
      accountPassword: formData.accountPassword,
      accountRole: Number(formData.accountRole),
    };

    try {
      if (editingAccount.accountId) {
        await accountApi.updateAccount(editingAccount.accountId, requestData);
      } else {
        await accountApi.createAccount(requestData);
      }

      closeModal();
      loadAccounts();
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Lưu tài khoản thất bại";
      setFormErrorMessage(message);
    } finally {
      setIsSaving(false);
    }
  }

  async function handleConfirmDelete() {
    setListErrorMessage("");

    try {
      await accountApi.deleteAccount(deleteTargetId);
      setDeleteTargetId(null);
      loadAccounts();
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Xóa tài khoản thất bại";
      setListErrorMessage(message);
      setDeleteTargetId(null);
    }
  }

  return (
    <div className="page-container">
      <h1>Quản lý tài khoản</h1>

      {listErrorMessage && <div className="error-message">{listErrorMessage}</div>}

      <form onSubmit={handleSearchSubmit} className="toolbar-row">
        <div className="search-box">
          <input
            type="text"
            placeholder="Tìm theo tên hoặc email..."
            value={keyword}
            onChange={(event) => setKeyword(event.target.value)}
          />
        </div>

        <div style={{ display: "flex", gap: 10 }}>
          <button type="submit" className="button button-secondary">
            Tìm kiếm
          </button>
          <button type="button" className="button button-primary" onClick={openCreateModal}>
            + Thêm tài khoản
          </button>
        </div>
      </form>

      <div className="white-box">
        {isLoading ? (
          <p className="loading-text">Đang tải...</p>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>Tên</th>
                <th>Email</th>
                <th>Vai trò</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {accounts.map((account) => (
                <tr key={account.accountId}>
                  <td>{account.accountName}</td>
                  <td>{account.accountEmail}</td>
                  <td>{account.accountRole === ROLE_ADMIN ? "Admin" : "Staff"}</td>
                  <td className="action-buttons">
                    <button
                      type="button"
                      className="button button-secondary"
                      onClick={() => openEditModal(account)}
                    >
                      Sửa
                    </button>
                    <button
                      type="button"
                      className="button button-danger"
                      onClick={() => setDeleteTargetId(account.accountId)}
                    >
                      Xóa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {editingAccount !== null && (
        <Modal
          title={editingAccount.accountId ? "Sửa tài khoản" : "Thêm tài khoản mới"}
          onClose={closeModal}
        >
          {formErrorMessage && <div className="error-message">{formErrorMessage}</div>}

          <form onSubmit={handleFormSubmit}>
            <div className="form-field">
              <label htmlFor="accountName">Tên tài khoản</label>
              <input
                id="accountName"
                type="text"
                value={formData.accountName}
                onChange={(event) => handleFormFieldChange("accountName", event.target.value)}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="accountEmail">Email</label>
              <input
                id="accountEmail"
                type="email"
                value={formData.accountEmail}
                onChange={(event) => handleFormFieldChange("accountEmail", event.target.value)}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="accountPassword">
                Mật khẩu {editingAccount.accountId && "(để trống nếu không đổi)"}
              </label>
              <input
                id="accountPassword"
                type="password"
                value={formData.accountPassword}
                onChange={(event) => handleFormFieldChange("accountPassword", event.target.value)}
              />
            </div>

            <div className="form-field">
              <label htmlFor="accountRole">Vai trò</label>
              <select
                id="accountRole"
                value={formData.accountRole}
                onChange={(event) => handleFormFieldChange("accountRole", event.target.value)}
              >
                <option value={ROLE_ADMIN}>Admin</option>
                <option value={ROLE_STAFF}>Staff</option>
              </select>
            </div>

            <div className="modal-footer">
              <button type="button" className="button button-secondary" onClick={closeModal}>
                Hủy
              </button>
              <button type="submit" className="button button-primary" disabled={isSaving}>
                {isSaving ? "Đang lưu..." : "Lưu"}
              </button>
            </div>
          </form>
        </Modal>
      )}

      {deleteTargetId !== null && (
        <ConfirmDialog
          message="Bạn có chắc muốn xóa tài khoản này?"
          onConfirm={handleConfirmDelete}
          onCancel={() => setDeleteTargetId(null)}
        />
      )}
    </div>
  );
}

export default AdminAccountPage;
