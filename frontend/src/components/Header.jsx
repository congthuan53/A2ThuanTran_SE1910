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
        <Link to="/">Trang chu</Link>

        {isAdmin && <Link to="/admin/accounts">Quan ly tai khoan</Link>}

        {isStaff && (
          <>
            <Link to="/staff/categories">Danh muc</Link>
            <Link to="/staff/news">Quan ly tin tuc</Link>
            <Link to="/staff/news-history">Lich su dang tin</Link>
            <Link to="/staff/profile">Ho so cua toi</Link>
          </>
        )}

        {currentUser === null ? (
          <Link to="/login">Dang nhap</Link>
        ) : (
          <>
            <span>Xin chao, {currentUser.accountName}</span>
            <button type="button" onClick={handleLogoutClick}>
              Dang xuat
            </button>
          </>
        )}
      </nav>
    </header>
  );
}

export default Header;
