import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import * as newsApi from "../api/newsApi";

function PublicNewsDetailPage() {
  const { newsArticleId } = useParams();
  const [news, setNews] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    newsApi
      .getActiveNewsDetailForPublic(newsArticleId)
      .then(setNews)
      .catch(() => setErrorMessage("Không tìm thấy bài viết này"));
  }, [newsArticleId]);

  if (errorMessage) {
    return (
      <div className="page-container">
        <div className="error-message">{errorMessage}</div>
        <Link to="/">Quay lại trang chủ</Link>
      </div>
    );
  }

  if (news === null) {
    return <p className="loading-text">Đang tải...</p>;
  }

  return (
    <div className="page-container">
      <Link to="/">&larr; Quay lại danh sách</Link>

      <div className="white-box" style={{ marginTop: 14 }}>
        <h1>{news.newsTitle}</h1>
        <div className="news-meta">
          Danh mục: {news.categoryName} | Tác giả: {news.createdByName} | Ngày đăng:{" "}
          {new Date(news.createdDate).toLocaleDateString("vi-VN")}
        </div>

        {news.tagNames.length > 0 && (
          <div style={{ marginBottom: 14 }}>
            {news.tagNames.map((tagName) => (
              <span key={tagName} className="tag-chip">
                {tagName}
              </span>
            ))}
          </div>
        )}

        <p>
          <strong>{news.headline}</strong>
        </p>

        <p style={{ whiteSpace: "pre-line" }}>{news.newsContent}</p>

        {news.newsSource && <p>Nguồn: {news.newsSource}</p>}
      </div>
    </div>
  );
}

export default PublicNewsDetailPage;
