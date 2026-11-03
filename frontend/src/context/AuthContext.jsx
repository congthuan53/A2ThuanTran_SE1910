import { createContext, useContext, useState } from "react";
import * as authApi from "../api/authApi";

// This context stores who is currently logged in (or null if nobody is logged in),
// and exposes login/logout functions. Any component can read it with useAuth().
const AuthContext = createContext(null);

const ROLE_ADMIN = 1;
const ROLE_STAFF = 2;

function readCurrentUserFromStorage() {
  const savedUserJson = localStorage.getItem("currentUser");

  if (!savedUserJson) {
    return null;
  }

  try {
    return JSON.parse(savedUserJson);
  } catch (error) {
    return null;
  }
}

export function AuthProvider({ children }) {
  const [currentUser, setCurrentUser] = useState(readCurrentUserFromStorage());

  async function login(email, password) {
    const response = await authApi.login(email, password);

    const user = {
      accountId: response.accountId,
      accountName: response.accountName,
      accountEmail: response.accountEmail,
      accountRole: response.accountRole,
    };

    localStorage.setItem("token", response.token);
    localStorage.setItem("currentUser", JSON.stringify(user));
    setCurrentUser(user);

    return user;
  }

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("currentUser");
    setCurrentUser(null);
  }

  const value = {
    currentUser,
    login,
    logout,
    isAdmin: currentUser !== null && currentUser.accountRole === ROLE_ADMIN,
    isStaff: currentUser !== null && currentUser.accountRole === ROLE_STAFF,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}
