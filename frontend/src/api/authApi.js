import axiosClient from "./axiosClient";

// call POST /api/auth/login, returns { token, accountId, accountName, accountEmail, accountRole }
export function login(email, password) {
  return axiosClient
    .post("/auth/login", { email, password })
    .then((response) => response.data);
}
