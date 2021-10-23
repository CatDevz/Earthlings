import React from "react";
import { TouchableOpacity, View } from "react-native";

const OverlayButton = ({ children, size = 60, ...props }) => {
  const localStyles = {
    overlay_button: {
      backgroundColor: "#fff",
      marginTop: 10,

      borderRadius: 50,
      borderColor: "#000000",
      borderWidth: 1,
    },
    overlay_button_touchable: {
      flex: 1,
      alignItems: "center",
      justifyContent: "center",
    },
  };

  return (
    <View style={{ ...localStyles.overlay_button, width: size, height: size }}>
      <TouchableOpacity style={localStyles.overlay_button_touchable} {...props}>
        {children}
      </TouchableOpacity>
    </View>
  );
};

export default OverlayButton;
