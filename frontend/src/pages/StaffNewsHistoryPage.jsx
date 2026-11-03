import { useEffect, useState } from "react";
import * as newsApi from "../api/newsApi";

// Shows every news article that the logged-in staff has created.
function StaffNewsHistoryPage() {
  const [newsList, setNewsList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    newsApi
      .getMyNewsHistory()
      .then(setNewsList)
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <div className="page-container">
      <h1>Lịch sử đăng tin</h1>

      <div className="white-box">
        {isLoading ? (
          <p className="loading-text">Đang tải...</p>
        ) : newsList.length === 0 ? (
          <p>Bạn chưa đăng bài viết nào.</p>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>Tiêu đề</th>
                <th>Danh mục</th>
                <th>Ngày đăng</th>
                <th>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              {newsList.map((news) => (
                <tr key={news.newsArticleId}>
                  <td>{news.newsTitle}</td>
                  <td>{news.categoryName}</td>
                  <td>{new Date(news.createdDate).toLocaleDateString("vi-VN")}</td>
                  <td>
                    <span
                      className={`status-badge ${
                        news.newsStatus ? "status-badge-active" : "status-badge-inactive"
                      }`}
                    >
                      {news.newsStatus ? "Hoạt động" : "Ngừng hoạt động"}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default StaffNewsHistoryPage;
