import axiosClient from "./axiosClient";

// ----- Admin : manage accounts -----

export function getAllAccounts(keyword) {
  return axiosClient
    .get("/accounts", { params: { keyword } })
    .then((response) => response.data);
}

export function createAccount(accountData) {
  return axiosClient.post("/accounts", accountData).then((response) => response.data);
}

export function updateAccount(accountId, accountData) {
  return axiosClient
    .put(`/accounts/${accountId}`, accountData)
    .then((response) => response.data);
}

export function deleteAccount(accountId) {
  return axiosClient.delete(`/accounts/${accountId}`);
}

// ----- Staff : own profile -----

export function getMyProfile() {
  return axiosClient.get("/profile").then((response) => response.data);
}

export function updateMyProfile(profileData) {
  return axiosClient.put("/profile", profileData).then((response) => response.data);
}
