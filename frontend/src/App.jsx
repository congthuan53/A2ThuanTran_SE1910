import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Header from "./components/Header";
import ProtectedRoute from "./components/ProtectedRoute";

import LoginPage from "./pages/LoginPage";
import PublicNewsListPage from "./pages/PublicNewsListPage";
import PublicNewsDetailPage from "./pages/PublicNewsDetailPage";
import AdminAccountPage from "./pages/AdminAccountPage";
import StaffCategoryPage from "./pages/StaffCategoryPage";
import StaffNewsPage from "./pages/StaffNewsPage";
import StaffNewsHistoryPage from "./pages/StaffNewsHistoryPage";
import StaffProfilePage from "./pages/StaffProfilePage";

const ROLE_ADMIN = 1;
const ROLE_STAFF = 2;

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Header />

        <Routes>
          {/* public pages, no login needed */}
          <Route path="/" element={<PublicNewsListPage />} />
          <Route path="/news/:newsArticleId" element={<PublicNewsDetailPage />} />
          <Route path="/login" element={<LoginPage />} />

          {/* Admin only */}
          <Route
            path="/admin/accounts"
            element={
              <ProtectedRoute requiredRole={ROLE_ADMIN}>
                <AdminAccountPage />
              </ProtectedRoute>
            }
          />

          {/* Staff only */}
          <Route
            path="/staff/categories"
            element={
              <ProtectedRoute requiredRole={ROLE_STAFF}>
                <StaffCategoryPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/staff/news"
            element={
              <ProtectedRoute requiredRole={ROLE_STAFF}>
                <StaffNewsPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/staff/news-history"
            element={
              <ProtectedRoute requiredRole={ROLE_STAFF}>
                <StaffNewsHistoryPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/staff/profile"
            element={
              <ProtectedRoute requiredRole={ROLE_STAFF}>
                <StaffProfilePage />
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
