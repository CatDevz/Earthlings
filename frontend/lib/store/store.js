import { configureStore } from "@reduxjs/toolkit";
import cansReducer from "./cansReducer";

const store = configureStore({
  reducer: {
    cans: cansReducer,
  },
});

export default store;
