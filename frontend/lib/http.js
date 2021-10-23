import axios from "axios";

const BASE_URL = "https://db41-198-54-134-125.ngrok.io";

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
