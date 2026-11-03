import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import * as newsApi from "../api/newsApi";
import * as categoryApi from "../api/categoryApi";

// This page does not need login. It shows every news article whose status is active.
function PublicNewsListPage() {
  const [newsList, setNewsList] = useState([]);
  const [categories, setCategories] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [selectedCategoryId, setSelectedCategoryId] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    categoryApi.getActiveCategories().then(setCategories);
  }, []);

  useEffect(() => {
    loadNews();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedCategoryId]);

  function loadNews() {
    setIsLoading(true);
    const categoryId = selectedCategoryId === "" ? undefined : Number(selectedCategoryId);

    newsApi
      .getActiveNewsForPublic(keyword, categoryId)
      .then(setNewsList)
      .finally(() => setIsLoading(false));
  }

  function handleSearchSubmit(event) {
    event.preventDefault();
    loadNews();
  }

  return (
    <div className="page-container">
      <h1>Tin tức</h1>

      <form onSubmit={handleSearchSubmit} className="toolbar-row">
        <div className="search-box">
          <input
            type="text"
            placeholder="Tìm theo tiêu đề..."
            value={keyword}
            onChange={(event) => setKeyword(event.target.value)}
          />
        </div>

        <select
          value={selectedCategoryId}
          onChange={(event) => setSelectedCategoryId(event.target.value)}
        >
          <option value="">Tất cả danh mục</option>
          {categories.map((category) => (
            <option key={category.categoryId} value={category.categoryId}>
              {category.categoryName}
            </option>
          ))}
        </select>

        <button type="submit" className="button button-primary">
          Tìm kiếm
        </button>
      </form>

      {isLoading && <p className="loading-text">Đang tải...</p>}

      {!isLoading && newsList.length === 0 && <p>Không có bài viết nào.</p>}

      {newsList.map((news) => (
        <div key={news.newsArticleId} className="news-card">
          <h3>
            <Link to={`/news/${news.newsArticleId}`}>{news.newsTitle}</Link>
          </h3>
          <div className="news-meta">
            Danh mục: {news.categoryName} | Ngày đăng:{" "}
            {new Date(news.createdDate).toLocaleDateString("vi-VN")}
          </div>
          <p>{news.headline}</p>
          {news.tagNames.map((tagName) => (
            <span key={tagName} className="tag-chip">
              {tagName}
            </span>
          ))}
        </div>
      ))}
    </div>
  );
}

export default PublicNewsListPage;
