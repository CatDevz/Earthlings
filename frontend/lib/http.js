import axios from "axios";

const BASE_URL = "https://d889-68-235-44-77.ngrok.io";

const http = axios.create({
  baseURL: BASE_URL,
  timeout: 5000,
});

export default http;
