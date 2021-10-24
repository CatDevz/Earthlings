import React, { useEffect, useRef, useState } from "react";
import { View, Image, Dimensions, Text, Button } from "react-native";
import MapView, { Marker } from "react-native-maps";
import OverlayButton from "../components/OverlayButton";
import { StyleSheet } from "react-native";
import { useHistory } from "react-router-native";
import api from "../lib/api";
import { getLocationAsLatLongAsync } from "../lib/location";
import Drawer from "react-native-drawer";
import { BASE_URL } from "../lib/http";
import * as Location from "expo-location";
import { Magnetometer } from "expo-sensors";

const CompassView = ({ can, setShowingCompass }) => {
  const [subscription, setSubscription] = useState(null);
  const [magnetometer, setMagnetometer] = useState(0);

  const [angle, setAngle] = useState(0);

  useEffect(async () => {
    setSubscription(
      Magnetometer.addListener((data) => {
        let angle = 0;
        let { x, y } = data;
        if (Math.atan2(y, x) >= 0) {
          angle = Math.atan2(y, x) * (180 / Math.PI);
        } else {
          angle = (Math.atan2(y, x) + 2 * Math.PI) * (180 / Math.PI);
        }

        angle = Math.round(angle);
        angle = angle - 90 >= 0 ? angle - 90 : angle + 271;
        setMagnetometer(angle);
      }),
    );

    setInterval(async () => {
      const personLocation = await Location.getCurrentPositionAsync({
        accuracy: 5,
      });

      const { longitude: x1, latitude: y1 } = personLocation.coords;
      const { longitude: x2, latitude: y2 } = can;

      let y = y2 - y1;
      let x = x2 - x1;

      let res = (Math.atan2(y, x) * 180) / Math.PI;
      setAngle(res);
    }, 250);
  }, []);

  const computedAngle = angle + 90 + (360 - magnetometer);

  return (
    <View style={compassStyles.container}>
      <Text>Angle: {computedAngle}</Text>
      <Image
        source={require("../assets/arrow.png")}
        style={[compassStyles.arrow, { transform: [{ rotateZ: `${computedAngle}deg` }] }]}
      />
      <Button title='Show Image' onPress={() => setShowingCompass(false)} />
    </View>
  );
};

const compassStyles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "space-around",
  },
  arrow: {
    width: 180,
    height: 180,
  },
});

const MapPage = () => {
  const history = useHistory();

  const mapRef = useRef(null);
  const drawerRef = useRef(null);

  const [cans, setCans] = useState([]);
  const [selectedCan, setSelectedCan] = useState(null);
  const [showingCompass, setShowingCompass] = useState(false);

  // Centering the location to the user's location
  // when the MapPage is loaded
  useEffect(async () => {
    if (mapRef.current == null) return;

    mapRef.current.setCamera({
      center: await getLocationAsLatLongAsync(),
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
  const handleAddPress = async () => {
    history.push("/create");
  };

  // Called when the user presses the button to
  // focus the camera on their person
  const handleFocusPress = async () => {
    if (mapRef.current == null) return;

    mapRef.current.animateCamera({
      center: await getLocationAsLatLongAsync(),
      heading: 0.0,
      pitch: 0.0,
      zoom: 15,
    });
  };

  const handleRegionChange = async (region) => {
    // TODO: Load new trash cans
  };

  const handleMarkerPress = async (can) => {
    // TODO: open drawer with info when a trash can is clicked on
    setSelectedCan(can);
    drawerRef.current.open();
    mapRef.current.animateCamera({
      center: { latitude: can.latitude - 0.008, longitude: can.longitude },
      heading: 0.0,
      pitch: 0.0,
      zoom: 15,
    });
  };

  return (
    <Drawer
      ref={drawerRef}
      type='overlay'
      content={
        <View style={{ flex: 1, justifyContent: "flex-end" }}>
          <View style={styles.drawerContainer}>
            {selectedCan && showingCompass == false && (
              <>
                <Image
                  source={{
                    uri: `${BASE_URL}/cans/${selectedCan.uuid}/image`,
                  }}
                  style={{ width: 340, height: 400, marginBottom: 5 }}
                />
                <Button title='Show Compass Directions' onPress={() => setShowingCompass(true)} />
              </>
            )}
            {selectedCan && showingCompass == true && (
              <CompassView can={selectedCan} setShowingCompass={setShowingCompass} />
            )}
          </View>
        </View>
      }
      openDrawerOffset={0.0}
      panCloseMask={0.2}
      styles={drawerStyles}
      onClose={() => {
        if (selectedCan == null) return;
        const { latitude, longitude } = selectedCan;
        mapRef.current.animateCamera({
          center: { latitude, longitude },
          heading: 0.0,
          pitch: 0.0,
          zoom: 15,
        });

        setSelectedCan(null);
        setShowingCompass(false);
      }}
    >
      <View>
        <MapView
          ref={mapRef}
          style={styles.map}
          showsUserLocation={true}
          userLocationUpdateInterval={1000}
          showsMyLocationButton={false}
          rotateEnabled={false}
          userLocationPriority='high'
          onRegionChangeComplete={handleRegionChange}
        >
          {cans.map((can) => (
            <Marker
              key={can.uuid}
              image={require("../assets/trashcan.png")}
              coordinate={{ longitude: can.longitude, latitude: can.latitude }}
              onPress={() => handleMarkerPress(can)}
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
    </Drawer>
  );
};

const drawerStyles = {
  drawer: { shadowColor: "#000000", shadowOpacity: 0.8, shadowRadius: 3 },
  main: { paddingLeft: 0 },
};

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    alignItems: "flex-end",
    justifyContent: "space-between",

    position: "absolute",
    bottom: 30,
    right: 20,
  },
  map: {
    width: Dimensions.get("window").width,
    height: Dimensions.get("window").height,
  },
  drawerContainer: {
    backgroundColor: "#fff",
    height: 500,
    borderTopLeftRadius: 15,
    borderTopRightRadius: 15,
    padding: 10,
  },
});

export default MapPage;
