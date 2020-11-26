import React, { useEffect } from "react";
import CardGroupList from "./routes/CardGroupList";
import { useTypedDispatch, useTypedSelector } from "./store";

import { Router, Switch, Route, Redirect } from "react-router-dom";
import { createBrowserHistory } from "history";

import "antd/dist/antd.css";
import CardGroup from "./routes/CardGroup";
import { Col, Layout, Row } from "antd";

import DebugWS from "./routes/DebugWS";
import { ActionTypes } from "./store/actions";
import DebugButton from "./components/DebugButton";
import Play from "./routes/Play";
import Confetti from "react-confetti";
import useWindowSize from "react-use/lib/useWindowSize";

const { Header, Content, Footer } = Layout;

const history = createBrowserHistory();

function App() {
  const dispatch = useTypedDispatch();
  useEffect(() => {
    dispatch({
      type: ActionTypes.Connect,
    });
  }, []);

  const confettiOn = useTypedSelector((state) => state.config.confettiOn);
  const { width, height } = useWindowSize();

  return (
    <>
      {confettiOn && (
        <Confetti
          width={width}
          height={height}
          recycle={false}
          onConfettiComplete={() => dispatch({ type: ActionTypes.ConfettiOff })}
        />
      )}
      <Router history={history}>
        <Layout className="layout">
          <Content style={{ padding: "0 50px", minHeight: "100vh" }}>
            <Switch>
              <Route path="/debug">
                <DebugWS />
              </Route>

              <Route path="/play">
                <Play />
              </Route>

              <Route path="/cardgroup/:id">
                <CardGroup />
              </Route>

              <Route path="/cardgroups">
                <CardGroupList />
              </Route>

              <Route strict path="/">
                <Redirect to="/play" />
              </Route>
            </Switch>
          </Content>
        </Layout>
        <DebugButton />
      </Router>
    </>
  );
}

export default App;
