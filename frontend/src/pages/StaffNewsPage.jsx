import { useEffect, useState } from "react";
import * as newsApi from "../api/newsApi";
import * as categoryApi from "../api/categoryApi";
import Modal from "../components/Modal";
import ConfirmDialog from "../components/ConfirmDialog";

const EMPTY_FORM_DATA = {
  newsTitle: "",
  headline: "",
  newsContent: "",
  newsSource: "",
  categoryId: "",
  newsStatus: true,
  tagNamesText: "",
};

// turn "Sport, Technology" into ["Sport", "Technology"]
function splitTagNamesText(tagNamesText) {
  return tagNamesText
    .split(",")
    .map((tagName) => tagName.trim())
    .filter((tagName) => tagName.length > 0);
}

function StaffNewsPage() {
  const [newsList, setNewsList] = useState([]);
  const [categories, setCategories] = useState([]);
  const [existingTagNames, setExistingTagNames] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  const [editingNews, setEditingNews] = useState(null);
  const [formData, setFormData] = useState(EMPTY_FORM_DATA);
  const [formErrorMessage, setFormErrorMessage] = useState("");
  const [isSaving, setIsSaving] = useState(false);

  const [deleteTargetId, setDeleteTargetId] = useState(null);
  const [listErrorMessage, setListErrorMessage] = useState("");

  useEffect(() => {
    loadNews();
    categoryApi.getAllCategories().then(setCategories);
    newsApi.getAllTagNames().then(setExistingTagNames);
  }, []);

  function loadNews() {
    setIsLoading(true);
    newsApi
      .getAllNewsForStaff(keyword)
      .then(setNewsList)
      .finally(() => setIsLoading(false));
  }

  function handleSearchSubmit(event) {
    event.preventDefault();
    loadNews();
  }

  function openCreateModal() {
    setEditingNews({});
    setFormData(EMPTY_FORM_DATA);
    setFormErrorMessage("");
  }

  function openEditModal(news) {
    setEditingNews(news);
    setFormData({
      newsTitle: news.newsTitle,
      headline: news.headline || "",
      newsContent: news.newsContent || "",
      newsSource: news.newsSource || "",
      categoryId: news.categoryId,
      newsStatus: news.newsStatus,
      tagNamesText: news.tagNames.join(", "),
    });
    setFormErrorMessage("");
  }

  function closeModal() {
    setEditingNews(null);
  }

  function handleFormFieldChange(fieldName, value) {
    setFormData((previousData) => ({ ...previousData, [fieldName]: value }));
  }

  async function handleFormSubmit(event) {
    event.preventDefault();
    setFormErrorMessage("");
    setIsSaving(true);

    const requestData = {
      newsTitle: formData.newsTitle,
      headline: formData.headline,
      newsContent: formData.newsContent,
      newsSource: formData.newsSource,
      categoryId: Number(formData.categoryId),
      newsStatus: formData.newsStatus === true || formData.newsStatus === "true",
      tagNames: splitTagNamesText(formData.tagNamesText),
    };

    try {
      if (editingNews.newsArticleId) {
        await newsApi.updateNews(editingNews.newsArticleId, requestData);
      } else {
        await newsApi.createNews(requestData);
      }

      closeModal();
      loadNews();
      newsApi.getAllTagNames().then(setExistingTagNames);
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Lưu bài viết thất bại";
      setFormErrorMessage(message);
    } finally {
      setIsSaving(false);
    }
  }

  async function handleConfirmDelete() {
    setListErrorMessage("");

    try {
      await newsApi.deleteNews(deleteTargetId);
      setDeleteTargetId(null);
      loadNews();
    } catch (error) {
      const message =
        error.response && error.response.data && error.response.data.message
          ? error.response.data.message
          : "Xóa bài viết thất bại";
      setListErrorMessage(message);
      setDeleteTargetId(null);
    }
  }

  return (
    <div className="page-container">
      <h1>Quản lý tin tức</h1>

      {listErrorMessage && <div className="error-message">{listErrorMessage}</div>}

      <form onSubmit={handleSearchSubmit} className="toolbar-row">
        <div className="search-box">
          <input
            type="text"
            placeholder="Tìm theo tiêu đề..."
            value={keyword}
            onChange={(event) => setKeyword(event.target.value)}
          />
        </div>

        <div style={{ display: "flex", gap: 10 }}>
          <button type="submit" className="button button-secondary">
            Tìm kiếm
          </button>
          <button type="button" className="button button-primary" onClick={openCreateModal}>
            + Thêm bài viết
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
                <th>Tiêu đề</th>
                <th>Danh mục</th>
                <th>Người tạo</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {newsList.map((news) => (
                <tr key={news.newsArticleId}>
                  <td>{news.newsTitle}</td>
                  <td>{news.categoryName}</td>
                  <td>{news.createdByName}</td>
                  <td>
                    <span
                      className={`status-badge ${
                        news.newsStatus ? "status-badge-active" : "status-badge-inactive"
                      }`}
                    >
                      {news.newsStatus ? "Hoạt động" : "Ngừng hoạt động"}
                    </span>
                  </td>
                  <td className="action-buttons">
                    <button
                      type="button"
                      className="button button-secondary"
                      onClick={() => openEditModal(news)}
                    >
                      Sửa
                    </button>
                    <button
                      type="button"
                      className="button button-danger"
                      onClick={() => setDeleteTargetId(news.newsArticleId)}
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

      {editingNews !== null && (
        <Modal
          title={editingNews.newsArticleId ? "Sửa bài viết" : "Thêm bài viết mới"}
          onClose={closeModal}
        >
          {formErrorMessage && <div className="error-message">{formErrorMessage}</div>}

          <form onSubmit={handleFormSubmit}>
            <div className="form-field">
              <label htmlFor="newsTitle">Tiêu đề</label>
              <input
                id="newsTitle"
                type="text"
                value={formData.newsTitle}
                onChange={(event) => handleFormFieldChange("newsTitle", event.target.value)}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="headline">Tóm tắt (headline)</label>
              <input
                id="headline"
                type="text"
                value={formData.headline}
                onChange={(event) => handleFormFieldChange("headline", event.target.value)}
              />
            </div>

            <div className="form-field">
              <label htmlFor="newsContent">Nội dung</label>
              <textarea
                id="newsContent"
                value={formData.newsContent}
                onChange={(event) => handleFormFieldChange("newsContent", event.target.value)}
                required
              />
            </div>

            <div className="form-field">
              <label htmlFor="newsSource">Nguồn tin</label>
              <input
                id="newsSource"
                type="text"
                value={formData.newsSource}
                onChange={(event) => handleFormFieldChange("newsSource", event.target.value)}
              />
            </div>

            <div className="form-field">
              <label htmlFor="categoryId">Danh mục</label>
              <select
                id="categoryId"
                value={formData.categoryId}
                onChange={(event) => handleFormFieldChange("categoryId", event.target.value)}
                required
              >
                <option value="">-- Chọn danh mục --</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.categoryName}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-field">
              <label htmlFor="newsStatus">Trạng thái</label>
              <select
                id="newsStatus"
                value={formData.newsStatus}
                onChange={(event) => handleFormFieldChange("newsStatus", event.target.value)}
              >
                <option value="true">Hoạt động (hiển thị công khai)</option>
                <option value="false">Ngừng hoạt động</option>
              </select>
            </div>

            <div className="form-field">
              <label htmlFor="tagNamesText">Tag (cách nhau bởi dấu phẩy)</label>
              <input
                id="tagNamesText"
                type="text"
                list="existing-tag-names"
                placeholder="Ví dụ: Thể thao, Bóng đá"
                value={formData.tagNamesText}
                onChange={(event) => handleFormFieldChange("tagNamesText", event.target.value)}
              />
              <datalist id="existing-tag-names">
                {existingTagNames.map((tagName) => (
                  <option key={tagName} value={tagName} />
                ))}
              </datalist>
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
          message="Bạn có chắc muốn xóa bài viết này?"
          onConfirm={handleConfirmDelete}
          onCancel={() => setDeleteTargetId(null)}
        />
      )}
    </div>
  );
}

export default StaffNewsPage;
