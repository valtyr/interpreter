import React, { useRef, useState } from "react";

import ActionSchema from "../schemas/actions.schema.json";
import MonacoEditor from "react-monaco-editor";
import Ajv from "ajv";
import { Button } from "antd";
import { useTypedDispatch } from "../store";

var ajv = new Ajv();
var validate = ajv.compile(ActionSchema);

const ActionEditor = () => {
  const monaco = useRef<MonacoEditor | null>(null);
  const [action, setAction] = useState(JSON.stringify({ type: "" }, null, 2));
  const [valid, setValid] = useState(false);

  const jsonAction = JSON.parse(action);

  const dispatch = useTypedDispatch();

  return (
    <>
      <MonacoEditor
        key="editor"
        height="600"
        language="json"
        theme="vs-dark"
        value={action}
        ref={monaco}
        onChange={(value) => {
          setAction(value);
        }}
        editorWillMount={(monaco) => {
          monaco.languages.json.jsonDefaults.setDiagnosticsOptions({
            validate: true,
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
        onClick={() => dispatch(jsonAction)}
        //disabled={!validate(jsonAction)}
      >
        Run action
      </Button>
    </>
  );
};

export default ActionEditor;
