import React, { FunctionComponent } from "react";

import image from "../svgs/Logo.svg";

const Logo: FunctionComponent<{ width: number }> = ({ width }) => {
  return <img src={image} width={width}></img>;
};

export default Logo;
