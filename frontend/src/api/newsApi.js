import axiosClient from "./axiosClient";

// ----- public, no login needed, only active news -----

export function getActiveNewsForPublic(keyword, categoryId) {
  return axiosClient
    .get("/news/public", { params: { keyword, categoryId } })
    .then((response) => response.data);
}

export function getActiveNewsDetailForPublic(newsArticleId) {
  return axiosClient.get(`/news/public/${newsArticleId}`).then((response) => response.data);
}

// ----- Staff : manage news -----

export function getAllNewsForStaff(keyword) {
  return axiosClient.get("/news", { params: { keyword } }).then((response) => response.data);
}

export function getNewsById(newsArticleId) {
  return axiosClient.get(`/news/${newsArticleId}`).then((response) => response.data);
}

export function createNews(newsData) {
  return axiosClient.post("/news", newsData).then((response) => response.data);
}

export function updateNews(newsArticleId, newsData) {
  return axiosClient.put(`/news/${newsArticleId}`, newsData).then((response) => response.data);
}

export function deleteNews(newsArticleId) {
  return axiosClient.delete(`/news/${newsArticleId}`);
}

export function getMyNewsHistory() {
  return axiosClient.get("/news/my-history").then((response) => response.data);
}

export function getAllTagNames() {
  return axiosClient.get("/news/tags").then((response) => response.data);
}
