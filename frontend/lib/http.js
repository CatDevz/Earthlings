import axios from "axios";

export const BASE_URL = "http://pingmc.app:8000";

const http = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
});

http.interceptors.request.use(
  (config) => config,
  (err) => {
    console.error(err);
    Promise.reject(err);
  },
);

http.interceptors.response.use(
  (res) => res,
  (err) => {
    console.error(err);
    Promise.reject(err);
  },
);

export default http;
