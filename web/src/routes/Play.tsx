import React, { FunctionComponent } from "react";
import {
  Button,
  Card,
  Col,
  Divider,
  Form,
  Input,
  Row,
  Space,
  Typography,
} from "antd";
import { useTypedDispatch, useTypedSelector } from "../store";
import { ActionTypes } from "../store/actions";
import { game } from "../store/reducers/game";
import ReactJson from "react-json-view";
import Title from "antd/lib/typography/Title";
import Paragraph from "antd/lib/skeleton/Paragraph";
import Game from "../components/Game";
import Countdown from "../components/Countdown";
import Logo from "../components/Logo";
import { verify } from "crypto";
import WinState from "../components/WinState";

const UsernamePrompt: FunctionComponent<{
  setUsername: (username: string) => void;
}> = ({ setUsername }) => {
  return (
    <Row>
      <Space direction="vertical" style={{ width: "100%" }}>
        <Card>
          <Row justify={"center"}>
            <Logo width={400} />
          </Row>
        </Card>
        <Row gutter={10}>
          <Col span={12}>
            <Card title="Choose a username">
              <Typography.Paragraph>
                This is the name your fellow players see. Feel free to use
                emojis!
              </Typography.Paragraph>
              <Form<{ username: string }>
                onFinish={(values) => {
                  setUsername(values.username);
                }}
                initialValues={{ username: "" }}
              >
                <Form.Item
                  name="username"
                  rules={[
                    { required: true, message: "Please input a username" },
                  ]}
                >
                  <Input placeholder="Username" />
                </Form.Item>
                <Form.Item>
                  <Row justify="center">
                    <Button block type="primary" htmlType="submit">
                      Continue
                    </Button>
                  </Row>
                </Form.Item>
              </Form>
            </Card>
          </Col>
          <Col span={12}>
            <Card title="How does the game work?">
              <Typography.Paragraph>
                <Typography.Text strong>NTRPRTR</Typography.Text> is like a
                mashup of Alias, Codenames and Actionary.
              </Typography.Paragraph>
              <Typography.Paragraph>
                Before the first round, each user has to input a few terms.
                These terms are then used in the game's four rounds.
              </Typography.Paragraph>
              <Typography.Paragraph>
                The rounds are then as follows:
              </Typography.Paragraph>
              <ol>
                <li>
                  <strong>A round of Alias:</strong> The interpreter must
                  describe the term to the guesser without using any part of the
                  term itself. Please note that saying the word in another
                  language is prohibited.
                </li>
                <li>
                  <strong>A round of actionary:</strong> The interpreter must
                  act out the term. No talking or sounds allowed.
                </li>
                <li>
                  <strong>One word:</strong> The intepreter has to describe the
                  term using only one word.
                </li>
                <li>
                  <strong>Sculptures:</strong> The intepreter must act out the
                  term by assuming a single pose.
                </li>
              </ol>
              <Typography.Paragraph>
                The terms are recycled for each round so your memory is tested
                along with your ability to interpret. Because of this the game
                slowly turns into a self referential clusterf***.
              </Typography.Paragraph>
              <Typography.Paragraph>Good luck!</Typography.Paragraph>
              <Typography.Paragraph>- The NTRPRTR team 😁</Typography.Paragraph>
            </Card>
          </Col>
        </Row>
      </Space>
    </Row>
  );
};

const GamePrompt: FunctionComponent<{
  setGameId: (id: string) => void;
  createGame: () => void;
}> = ({ setGameId, createGame }) => {
  return (
    <Row>
      <Space direction="vertical" style={{ width: "100%" }}>
        <Card>
          <Row justify={"center"}>
            <Logo width={400} />
          </Row>
        </Card>

        <Row gutter={10}>
          <Col span={12}>
            <Card title="Join an existing game">
              <Typography.Paragraph>
                If your friend has already created a game you can enter its code
                here.
              </Typography.Paragraph>
              <Form<{ gameId: string }>
                onFinish={(values) => {
                  setGameId(values.gameId);
                }}
                initialValues={{ gameId: "" }}
              >
                <Form.Item
                  name="gameId"
                  rules={[
                    { required: true, message: "Please input a game code" },
                  ]}
                >
                  <Input placeholder="Game code" />
                </Form.Item>
                <Form.Item>
                  <Button block type="primary" htmlType="submit">
                    Continue
                  </Button>
                </Form.Item>
              </Form>
            </Card>
          </Col>
          <Col span={12}>
            <Card title="Host a game">
              <Typography.Paragraph>
                Click here to host a game. You will receive a code with which
                your friends can join.
              </Typography.Paragraph>
              <Button onClick={createGame} block>
                Create game
              </Button>
            </Card>
          </Col>
        </Row>
      </Space>
    </Row>
  );
};

const Play = () => {
  const dispatch = useTypedDispatch();

  const username = useTypedSelector((state) => state.user.username);
  const gameID = useTypedSelector((state) => state.game.id);
  const gameState = useTypedSelector((state) => state.game.gameState);
  const player = useTypedSelector((state) => state.user.player);

  if (!username) {
    return (
      <UsernamePrompt
        setUsername={(username) =>
          dispatch({ type: ActionTypes.SetUsername, payload: username })
        }
      />
    );
  }

  if (!gameID) {
    return (
      <GamePrompt
        createGame={() => dispatch({ type: ActionTypes.CreateGame })}
        setGameId={(gameID) =>
          dispatch({
            type: ActionTypes.JoinGame,
            payload: gameID.toUpperCase(),
          })
        }
      />
    );
  }

  return (
    gameState && (
      <>
        {gameState && player && (
          <Game gameState={gameState} player={player}></Game>
        )}
        {/* <code>
          <ReactJson src={gameState} />
        </code> */}
      </>
    )
  );
};

export default Play;
