import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Header() {
  const { currentUser, isAdmin, isStaff, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogoutClick() {
    logout();
    navigate("/login");
  }

  return (
    <header className="top-navigation-bar">
      <Link to="/" className="site-title">
        FU News Management
      </Link>

      <nav className="navigation-links">
        <Link to="/">Trang chủ</Link>

        {isAdmin && <Link to="/admin/accounts">Quản lý tài khoản</Link>}

        {isStaff && (
          <>
            <Link to="/staff/categories">Danh mục</Link>
            <Link to="/staff/news">Quản lý tin tức</Link>
            <Link to="/staff/news-history">Lịch sử đăng tin</Link>
            <Link to="/staff/profile">Hồ sơ của tôi</Link>
          </>
        )}

        {currentUser === null ? (
          <Link to="/login">Đăng nhập</Link>
        ) : (
          <>
            <span>Xin chào, {currentUser.accountName}</span>
            <button type="button" onClick={handleLogoutClick}>
              Đăng xuất
            </button>
          </>
        )}
      </nav>
    </header>
  );
}

export default Header;
