import React from "react";

import { BugFilled } from "@ant-design/icons";
import { Button } from "antd";
import { useHistory, useRouteMatch } from "react-router-dom";

import styles from "./DebugButton.module.css";

const DebugButton = () => {
  const history = useHistory();

  const match = useRouteMatch({
    path: "/debug",
    strict: false,
  });

  if (match) return null;

  return (
    <div className={styles.root}>
      <Button shape="circle" onClick={() => history.push("/debug")}>
        <BugFilled />
      </Button>
    </div>
  );
};

export default DebugButton;
