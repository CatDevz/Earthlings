import http from "./http";

const api = {
  cans: {
    getCans: () => http.get("/cans"),
    getCan: (canUuid) => http.get(`/cans/${canUuid}`),
    getCanImage: (canUuid) => http.get(`/cans/${canUuid}/image`),

    createCan: (body) => http.post("/cans", body),
    deleteCan: (canUuid) => http.delete(`/cans/${canUuid}`),
  },
};

export default api;
