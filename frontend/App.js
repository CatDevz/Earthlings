import React, { useEffect, useState } from "react";
import { StyleSheet, View, Text, Button } from "react-native";
import * as Location from "expo-location";
import MapPage from "./pages/MapPage";
import { StatusBar } from "expo-status-bar";
import { BackButton, NativeRouter, Route, useLocation } from "react-router-native";
import CreatePage from "./pages/CreatePage";

export default function App() {
  const [hasLocationPerms, setHasLocationPerms] = useState(null);

  const requestLocationPerms = () => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      setHasLocationPerms(status === "granted");
    })();
  };

  useEffect(() => {
    requestLocationPerms();
  }, []);

  // Requesting location permissions if they haven't already been granted
  if (!hasLocationPerms) {
    return (
      <View style={localStyles.container}>
        <Text>This app requires location permissions to function, please grant them!</Text>
        <Button title='Grant' onPress={requestLocationPerms} />
      </View>
    );
  }

  return (
    <NativeRouter>
      <BackButton>
        <View style={localStyles.container}>
          <StatusBar style='auto' />

          <Route exact path='/' component={MapPage} />
          <Route exact path='/create' component={CreatePage} />
        </View>
      </BackButton>
    </NativeRouter>
  );
}

const localStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
});
