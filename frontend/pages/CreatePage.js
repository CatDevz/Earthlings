import React, { useState } from "react";
import { StyleSheet, Text, TextInput, View } from "react-native";

const CreatePage = () => {
  const [imageBase64, setImageBase64] = useState("");

  const [latitude, setLatitude] = useState(null);
  const [longitude, setLongitude] = useState(null);

  return (
    <View style={localStyles.formContainer}>
      <Text style={localStyles.formHeader}>Add Garbagecan</Text>

      <View style={localStyles.form}>
        <TextInput value='Hello World' />
      </View>
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
