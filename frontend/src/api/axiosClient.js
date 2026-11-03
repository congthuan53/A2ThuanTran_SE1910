import axios from "axios";

// All requests to the backend go through this axios instance.
// The backend runs at http://localhost:8080 (see backend/src/main/resources/application.properties).
const axiosClient = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// Before every request is sent, attach the login token (if we have one)
// to the "Authorization" header, in the form "Bearer <token>".
axiosClient.interceptors.request.use((requestConfig) => {
  const token = localStorage.getItem("token");

  if (token) {
    requestConfig.headers.Authorization = `Bearer ${token}`;
  }

  return requestConfig;
});

// If the server answers with 401 (token missing/expired), send the user back to the login page.
axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("currentUser");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
