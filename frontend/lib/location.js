import * as Location from "expo-location";

export const getLocationAsLatLongAsync = async (options = {}) => {
  console.log("Hello Location!");
  const location = await Location.getCurrentPositionAsync(options);
  console.log("pog");
  const latitude = location.coords.latitude;
  const longitude = location.coords.longitude;
  console.log(`It is: ${latitude}, ${longitude}`);
  return { latitude, longitude };
};
