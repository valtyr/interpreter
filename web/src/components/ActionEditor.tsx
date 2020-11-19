import React, { useRef, useState } from "react";

import ActionSchema from "../schemas/actions.schema.json";
import MonacoEditor from "react-monaco-editor";
import Ajv from "ajv";
import { Button, Typography } from "antd";
import { useTypedDispatch } from "../store";

import styles from "./ActionEditor.module.css";

var ajv = new Ajv();
var validate = ajv.compile(ActionSchema);

const parseJSON = (data: string) => {
  try {
    return JSON.parse(data);
  } catch (e) {
    return {};
  }
};

const ActionEditor = () => {
  const monaco = useRef<MonacoEditor | null>(null);
  const [action, setAction] = useState(JSON.stringify({ type: "" }, null, 2));
  const [valid, setValid] = useState(false);

  const data = parseJSON(action);

  const dispatch = useTypedDispatch();

  return (
    <div className={styles.root}>
      <div className={styles.title}>
        <Typography.Title level={5}>Actions</Typography.Title>
      </div>
      <MonacoEditor
        key="editor"
        height="150"
        language="json"
        value={action}
        ref={monaco}
        onChange={(value) => {
          setAction(value);
        }}
        editorWillMount={(monaco) => {
          monaco.languages.json.jsonDefaults.setDiagnosticsOptions({
            validate: true,
            enableSchemaRequest: false,
            schemas: [
              {
                uri: "http://myserver/foo-schema.json",
                fileMatch: [""],
                schema: ActionSchema,
              },
            ],
          });
        }}
      />
      <Button
        disabled={!validate(data)}
        onClick={() => dispatch(data)}
        type="primary"
        className={styles.button}
      >
        Run action
      </Button>
    </div>
  );
};

export default ActionEditor;
