import React, { useEffect } from "react";
import { fetchCardGroups } from "./api";
import CardGroupList from "./routes/CardGroupList";
import { useTypedSelector } from "./store";

import { Router, Switch, Route } from "react-router-dom";
import { createBrowserHistory } from "history";

import "antd/dist/antd.css";
import CardGroup from "./routes/CardGroup";
import { Layout } from "antd";
const { Header, Content, Footer } = Layout;

const history = createBrowserHistory();

function App() {
  const cardGroups = useTypedSelector((state) => state.cardGroups);
  // useEffect(() => {
  //   fetchCardGroups();
  // }, []);

  return (
    <Router history={history}>
      <Layout className="layout">
        <Content style={{ padding: "0 50px", minHeight: "100vh" }}>
          <Switch>
            <Route path="/cardgroup/:id">
              <CardGroup />
            </Route>
            <Route strict path="/">
              <CardGroupList />
            </Route>
          </Switch>
        </Content>
      </Layout>
    </Router>
  );
}

export default App;
