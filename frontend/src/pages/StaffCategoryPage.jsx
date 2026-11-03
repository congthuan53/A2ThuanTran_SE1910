import { useEffect, useState } from "react";
import * as categoryApi from "../api/categoryApi";
import Modal from "../components/Modal";
import ConfirmDialog from "../components/ConfirmDialog";

const EMPTY_FORM_DATA = {
  categoryName: "",
  categoryDescription: "",
  parentCategoryId: "",
  isActive: true,
};

function StaffCategoryPage() {
  const [categories, setCategories] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  const [editingCategory, setEditingCategory] = useState(null);
  const [formData, setFormData] = useState(EMPTY_FORM_DATA);
  const [formErrorMessage, setFormErrorMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const [deleteTargetId, setDeleteTargetId] = useState(null);
  const [listErrorMessage, setListErrorMessage] = useState("");

  useEffect(() => {
    loadCategories();
  }, []);

  function loadCategories() {
    setIsLoading(true);
    categoryApi
      .getAllCategories(keyword)
      .then(setCategories)
      .finally(() => setIsLoading(false));
  }

  function handleSearchSubmit(event) {
    event.preventDefault();
    loadCategories();
  }

  function openCreateModal() {
    setEditingCategory({});
    setFormData(EMPTY_FORM_DATA);
    setFormErrorMessage("");
  }

  function openEditModal(category) {
    setEditingCategory(category);
    setFormData({
      categoryName: category.categoryName,
      categoryDescription: category.categoryDescription || "",
      parentCategoryId: category.parentCategoryId || "",
      isActive: category.isActive,
    });
    setFormErrorMessage("");
  }

  function closeModal() {
    setEditingCategory(null);
  }

  function handleFormFieldChange(fieldName, value) {
    setFormData((previousData) => ({ ...previousData, [fieldName]: value }));
  }

  async function handleFormSubmit(event) {
    event.preventDefault();
    setFormErrorMessage("");
    setIsSaving(true);

    const requestData = {
      categoryName: formData.categoryName,
      categoryDescription: formData.categoryDescription,
      parentCategoryId: formData.parentCategoryId === "" ? null : Number(formData.parentCategoryId),
      isActive: formData.isActive === true || formData.isActive === "true",
    };

    try {
      if (editingCategory.categoryId) {
        await categoryApi.updateCategory(editingCategory.categoryId, requestData);
      } else {
        await categoryApi.createCategory(requestData);
      }

      closeModal();
      loadCategories();
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Luu danh muc that bai";
      setFormErrorMessage(message);
    } finally {
      setIsSaving(false);
    }
  }

  async function handleConfirmDelete() {
    setListErrorMessage("");

    try {
      await categoryApi.deleteCategory(deleteTargetId);
      setDeleteTargetId(null);
      loadCategories();
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Xoa danh muc that bai";
      setListErrorMessage(message);
      setDeleteTargetId(null);
    }
  }

  return (
    <div className="page-container">
      <h1>Quan ly danh muc</h1>

      {listErrorMessage && <div className="error-message">{listErrorMessage}</div>}

      <form onSubmit={handleSearchSubmit} className="toolbar-row">
        <div className="search-box">
          <input
            type="text"
            placeholder="Tim theo ten danh muc..."
            value={keyword}
            onChange={(event) => setKeyword(event.target.value)}
          />
        </div>

        <div style={{ display: "flex", gap: 10 }}>
          <button type="submit" className="button button-secondary">
            Tim kiem
          </button>
          <button type="button" className="button button-primary" onClick={openCreateModal}>
            + Them danh muc
          </button>
        </div>
      </form>

      <div className="white-box">
        {isLoading ? (
          <p className="loading-text">Dang tai...</p>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>Ten danh muc</th>
                <th>Mo ta</th>
                <th>Trang thai</th>
                <th>Hanh dong</th>
              </tr>
            </thead>
            <tbody>
              {categories.map((category) => (
                <tr key={category.categoryId}>
                  <td>{category.categoryName}</td>
                  <td>{category.categoryDescription}</td>
                  <td>
                    <span
                      className={`status-badge ${
                        category.isActive ? "status-badge-active" : "status-badge-inactive"
                      }`}
                    >
                      {category.isActive ? "Hoat dong" : "Ngung hoat dong"}
                    </span>
                  </td>
                  <td className="action-buttons">
                    <button
                      type="button"
                      className="button button-secondary"
                      onClick={() => openEditModal(category)}
                    >
                      Sua
                    </button>
                    <button
                      type="button"
                      className="button button-danger"
                      onClick={() => setDeleteTargetId(category.categoryId)}
                    >
                      Xoa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {editingCategory !== null && (
        <Modal
          title={editingCategory.categoryId ? "Sua danh muc" : "Them danh muc moi"}
          onClose={closeModal}
        >
          {formErrorMessage && <div className="error-message">{formErrorMessage}</div>}

          <form onSubmit={handleFormSubmit}>
            <div className="form-field">
              <label htmlFor="categoryName">Ten danh muc</label>
              <input
                id="categoryName"
                type="text"
                value={formData.categoryName}
                onChange={(event) => handleFormFieldChange("categoryName", event.target.value)}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="categoryDescription">Mo ta</label>
              <textarea
                id="categoryDescription"
                value={formData.categoryDescription}
                onChange={(event) =>
                  handleFormFieldChange("categoryDescription", event.target.value)
                }
              />
            </div>

            <div className="form-field">
              <label htmlFor="isActive">Trang thai</label>
              <select
                id="isActive"
                value={formData.isActive}
                onChange={(event) => handleFormFieldChange("isActive", event.target.value)}
              >
                <option value="true">Hoat dong</option>
                <option value="false">Ngung hoat dong</option>
              </select>
            </div>

            <div className="modal-footer">
              <button type="button" className="button button-secondary" onClick={closeModal}>
                Huy
              </button>
              <button type="submit" className="button button-primary" disabled={isSaving}>
                {isSaving ? "Dang luu..." : "Luu"}
              </button>
            </div>
          </form>
        </Modal>
      )}

      {deleteTargetId !== null && (
        <ConfirmDialog
          message="Ban co chac muon xoa danh muc nay?"
          onConfirm={handleConfirmDelete}
          onCancel={() => setDeleteTargetId(null)}
        />
      )}
    </div>
  );
}

export default StaffCategoryPage;
