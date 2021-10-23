import React, { useEffect, useState } from "react";
import { View, Text } from "react-native";
import * as Location from "expo-location";
import MapPage from "./pages/MapPage";
import { PageContainer } from "./lib/contexts/pageContext";

export default function App() {
  const [errorMsg, setErrorMsg] = useState(null);

  useEffect(
    () =>
      (async () => {
        let { status } = await Location.requestForegroundPermissionsAsync();
        if (status !== "granted") {
          setErrorMsg("Permission to access location was denied");
          return;
        }
      })(),
    [],
  );

  if (errorMsg) {
    return (
      <View>
        <Text>{errorMsg}</Text>
      </View>
    );
  }

  return <PageContainer defaultPage={<MapPage />} />;
}
