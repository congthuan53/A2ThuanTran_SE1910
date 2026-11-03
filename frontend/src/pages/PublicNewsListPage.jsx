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
      <h1>Tin tuc</h1>

      <form onSubmit={handleSearchSubmit} className="toolbar-row">
        <div className="search-box">
          <input
            type="text"
            placeholder="Tim theo tieu de..."
            value={keyword}
            onChange={(event) => setKeyword(event.target.value)}
          />
        </div>

        <select
          value={selectedCategoryId}
          onChange={(event) => setSelectedCategoryId(event.target.value)}
        >
          <option value="">Tat ca danh muc</option>
          {categories.map((category) => (
            <option key={category.categoryId} value={category.categoryId}>
              {category.categoryName}
            </option>
          ))}
        </select>

        <button type="submit" className="button button-primary">
          Tim kiem
        </button>
      </form>

      {isLoading && <p className="loading-text">Dang tai...</p>}

      {!isLoading && newsList.length === 0 && <p>Khong co bai viet nao.</p>}

      {newsList.map((news) => (
        <div key={news.newsArticleId} className="news-card">
          <h3>
            <Link to={`/news/${news.newsArticleId}`}>{news.newsTitle}</Link>
          </h3>
          <div className="news-meta">
            Danh muc: {news.categoryName} | Ngay dang:{" "}
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
