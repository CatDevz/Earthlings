import React, { createContext, useContext, useState } from "react";
import { StyleSheet } from "react-native";
import { StatusBar, View } from "react-native";

const PageContext = createContext({
  page: <></>,
  setPage: (component) => {},
});

export const usePage = () => useContext(PageContext);

export const PageContainer = ({ defaultPage }) => {
  const [page, setPage] = useState(defaultPage);

  return (
    <PageContext.Provider
      value={{
        page: page,
        setPage: setPage,
      }}
    >
      <View style={localStyles.container}>
        <StatusBar style='auto' />
        {page}
      </View>
    </PageContext.Provider>
  );
};

const localStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
