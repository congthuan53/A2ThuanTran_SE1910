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
      .catch(() => setErrorMessage("Khong tim thay bai viet nay"));
  }, [newsArticleId]);

  if (errorMessage) {
    return (
      <div className="page-container">
        <div className="error-message">{errorMessage}</div>
        <Link to="/">Quay lai trang chu</Link>
      </div>
    );
  }

  if (news === null) {
    return <p className="loading-text">Dang tai...</p>;
  }

  return (
    <div className="page-container">
      <Link to="/">&larr; Quay lai danh sach</Link>

      <div className="white-box" style={{ marginTop: 14 }}>
        <h1>{news.newsTitle}</h1>
        <div className="news-meta">
          Danh muc: {news.categoryName} | Tac gia: {news.createdByName} | Ngay dang:{" "}
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

        {news.newsSource && <p>Nguon: {news.newsSource}</p>}
      </div>
    </div>
  );
}

export default PublicNewsDetailPage;
