import * as Location from "expo-location";

export const getLocationAsLatLongAsync = async (options = {}) => {
  const location = await Location.getCurrentPositionAsync(options);
  const latitude = location.coords.latitude;
  const longitude = location.coords.longitude;
  return { latitude, longitude };
};
