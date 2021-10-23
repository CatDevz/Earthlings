import { createSlice } from "@reduxjs/toolkit";

const cansSlice = createSlice({
  name: "cans",
  initialState: [],
  reducers: {
    addCan: (state, action) => {
      const can = action.payload;
      state.push(can);
    },
    removeCan: (state, action) => {
      const canUuid = action.payload;
      state = state.filter((it) => it.uuid !== canUuid);
    },
  },
});

export const { addCan, removeCan } = cansSlice.actions;

export default cansSlice.reducer;
