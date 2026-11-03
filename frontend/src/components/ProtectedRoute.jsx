import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

// Wrap a page with this component to require login.
// Pass requiredRole (1 = Admin, 2 = Staff) to also require a specific role.
// Example: <ProtectedRoute requiredRole={1}><AdminAccountPage /></ProtectedRoute>
function ProtectedRoute({ requiredRole, children }) {
  const { currentUser } = useAuth();

  if (currentUser === null) {
    return <Navigate to="/login" replace />;
  }

  if (requiredRole !== undefined && currentUser.accountRole !== requiredRole) {
    return <Navigate to="/" replace />;
  }

  return children;
}

export default ProtectedRoute;
