import React, { useEffect, useRef, useState } from "react";
import { View, Image, Dimensions } from "react-native";
import * as Location from "expo-location";
import MapView, { Marker } from "react-native-maps";
import OverlayButton from "../components/OverlayButton";
import { StyleSheet } from "react-native";
import { useHistory } from "react-router-native";
import api from "../lib/api";

const getLocationAsLatLong = async () => {
  const location = await Location.getCurrentPositionAsync({});
  const latitude = location.coords.latitude;
  const longitude = location.coords.longitude;
  return { latitude, longitude };
};

const MapPage = () => {
  const history = useHistory();
  const mapRef = useRef(null);

  const [cans, setCans] = useState([]);

  // Centering the location to the user's location
  // when the MapPage is loaded
  useEffect(async () => {
    if (mapRef.current == null) return;

    mapRef.current.setCamera({
      center: await getLocationAsLatLong(),
      heading: 0.0,
      pitch: 0.0,
      zoom: 15,
    });
  }, [mapRef]);

  useEffect(async () => {
    const response = await api.cans.getCans();
    const cans = response.data;
    setCans(cans);
  }, []);

  // Called when the user presses the button to
  // add a new trash can onto the map
  const handleAddPress = () => {
    history.push("/create");
  };

  // Called when the user presses the button to
  // focus the camera on their person
  const handleFocusPress = async () => {
    if (mapRef.current == null) return;

    mapRef.current.animateCamera({
      center: await getLocationAsLatLong(),
      heading: 0.0,
      pitch: 0.0,
      zoom: 15,
    });
  };

  const handleRegionChange = async (region) => {
    // TODO: Load new trash cans
  };

  return (
    <View>
      <MapView
        ref={mapRef}
        style={styles.map}
        showsUserLocation={true}
        userLocationUpdateInterval={1000}
        showsMyLocationButton={false}
        rotateEnabled={false}
        userLocationPriority='balanced'
        onRegionChangeComplete={handleRegionChange}
      >
        {cans.map((can) => (
          <Marker
            key={can.uuid}
            title={can.uuid}
            image={require("../assets/trashcan.png")}
            coordinate={{ longitude: can.longitude, latitude: can.latitude }}
          />
        ))}
      </MapView>

      <View style={styles.overlay}>
        <OverlayButton onPress={handleAddPress}>
          <Image source={require("../assets/add.png")} style={{ width: 30, height: 30 }} />
        </OverlayButton>
        <OverlayButton onPress={handleFocusPress}>
          <Image source={require("../assets/crosshair.png")} style={{ width: 30, height: 30 }} />
        </OverlayButton>
      </View>
    </View>
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
