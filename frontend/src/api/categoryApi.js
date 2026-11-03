import axiosClient from "./axiosClient";

// public, no login needed : used on the public news page to build the filter dropdown
export function getActiveCategories() {
  return axiosClient.get("/categories/public").then((response) => response.data);
}

// ----- Staff : manage categories -----

export function getAllCategories(keyword) {
  return axiosClient
    .get("/categories", { params: { keyword } })
    .then((response) => response.data);
}

export function createCategory(categoryData) {
  return axiosClient.post("/categories", categoryData).then((response) => response.data);
}

export function updateCategory(categoryId, categoryData) {
  return axiosClient
    .put(`/categories/${categoryId}`, categoryData)
    .then((response) => response.data);
}

export function deleteCategory(categoryId) {
  return axiosClient.delete(`/categories/${categoryId}`);
}
