import React from "react";
import { View, Image, Dimensions } from "react-native";
import MapView from "react-native-maps";
import OverlayButton from "../components/OverlayButton";
import { StyleSheet } from "react-native";
import { usePage } from "../lib/contexts/pageContext";

const MapPage = () => {
  const { page, setPage } = usePage();

  return (
    <>
      <MapView
        style={styles.map}
        showsUserLocation={true}
        userLocationUpdateInterval={1000}
        showsMyLocationButton={false}
        userLocationPriority='balanced'
      />

      <View style={styles.overlay}>
        <OverlayButton>
          <Image source={require("../assets/add.png")} style={{ width: 30, height: 30 }} />
        </OverlayButton>
        <OverlayButton onPress={() => {}}>
          <Image source={require("../assets/crosshair.png")} style={{ width: 30, height: 30 }} />
        </OverlayButton>
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    alignItems: "flex-end",
    justifyContent: "space-between",
    padding: 20,

    position: "absolute",
    bottom: 0,
    right: 0,
  },
  map: {
    width: Dimensions.get("window").width,
    height: Dimensions.get("window").height,
  },
});

export default MapPage;
