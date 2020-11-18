import React, { useEffect } from "react";
import { fetchCardGroups } from "./api";
import CardGroupList from "./routes/CardGroupList";
import { useTypedDispatch, useTypedSelector } from "./store";

import { Router, Switch, Route } from "react-router-dom";
import { createBrowserHistory } from "history";

import "antd/dist/antd.css";
import CardGroup from "./routes/CardGroup";
import { Layout } from "antd";

import DebugWS from "./routes/DebugWS";
import { ActionTypes } from "./store/actions";
import DebugButton from "./components/DebugButton";

const { Header, Content, Footer } = Layout;

const history = createBrowserHistory();

function App() {
  const cardGroups = useTypedSelector((state) => state.cardGroups);
  // useEffect(() => {
  //   fetchCardGroups();
  // }, []);

  // useEffect(() => {
  //   (async () => {
  //     const client = await connect();
  //     client.send("/app/ping");
  //   })();
  // }, []);

  const dispatch = useTypedDispatch();
  useEffect(() => {
    dispatch({
      type: ActionTypes.Connect,
    });
  }, []);

  return (
    <Router history={history}>
      <Layout className="layout">
        <Content style={{ padding: "0 50px", minHeight: "100vh" }}>
          <Switch>
            <Route path="/debug">
              <DebugWS />
            </Route>

            <Route path="/cardgroup/:id">
              <CardGroup />
            </Route>
            <Route strict path="/">
              <CardGroupList />
            </Route>
          </Switch>
        </Content>
      </Layout>
      <DebugButton />
    </Router>
  );
}

export default App;
