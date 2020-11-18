import React, { useRef, useState } from "react";

import ActionSchema from "../schemas/actions.schema.json";
import MonacoEditor from "react-monaco-editor";

const ActionEditor = () => {
  const monaco = useRef<MonacoEditor | null>(null);
  const [action, setAction] = useState("");
  const [valid, setValid] = useState(false);

  return (
    <MonacoEditor
      height="600"
      language="json"
      theme="vs-dark"
      value={action}
      ref={monaco}
      onChange={(value) => {
        setAction(value);
      }}
      editorWillMount={(monaco) => {
        var modelUri = monaco.Uri.parse("a://b/foo.json"); // a made up unique URI for our model
        var model = monaco.editor.createModel("{}", "json", modelUri);

        monaco.languages.json.jsonDefaults.setDiagnosticsOptions({
          validate: true,
          schemas: [{ uri: "bla", schema: ActionSchema }],
        });
      }}
    />
  );
};

export default ActionEditor;
