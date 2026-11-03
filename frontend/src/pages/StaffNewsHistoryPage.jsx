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
      <h1>Lich su dang tin</h1>

      <div className="white-box">
        {isLoading ? (
          <p className="loading-text">Dang tai...</p>
        ) : newsList.length === 0 ? (
          <p>Ban chua dang bai viet nao.</p>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>Tieu de</th>
                <th>Danh muc</th>
                <th>Ngay dang</th>
                <th>Trang thai</th>
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
                      {news.newsStatus ? "Hoat dong" : "Ngung hoat dong"}
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
