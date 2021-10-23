import React, { useEffect, useState } from "react";
import { StyleSheet, Text, View } from "react-native";
import * as ImagePicker from "expo-image-picker";
import { getLocationAsLatLongAsync } from "../lib/location";
import api from "../lib/api";
import { useHistory } from "react-router-native";

const CreatePage = () => {
  const history = useHistory();

  const [loading, setLoading] = useState(false);

  useEffect(async () => {
    const { status } = await ImagePicker.requestCameraPermissionsAsync();
    if (status !== "granted") {
      alert("Sorry, we need camera roll permissions to make this work!");
      history.push("/");
      return;
    }

    const result = await ImagePicker.launchCameraAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      base64: true,
    });

    // If cancelled goto main page
    if (result.cancelled) {
      history.push("/");
      return;
    }

    // Uploading the image & trash can
    setLoading(true);

    const location = await getLocationAsLatLongAsync({ accuracy: 6 });
    api.cans.createCan({ ...location, photoBase64: result.base64 }).then(() => {
      setLoading(false);
      history.push("/");
    });
  }, []);

  return (
    <View style={localStyles.formContainer}>
      <Text style={localStyles.formHeader}>Add Garbagecan</Text>
      <Text>{loading ? "Loading..." : "Done!"}</Text>
    </View>
  );
};

const localStyles = StyleSheet.create({
  formContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  formHeader: {
    textAlign: "center",
    fontSize: 24,
  },
});

export default CreatePage;
