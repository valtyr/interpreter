import React, { FunctionComponent } from "react";
import { Button, Form, Input } from "antd";
import { useTypedDispatch, useTypedSelector } from "../store";
import { ActionTypes } from "../store/actions";
import { game } from "../store/reducers/game";
import ReactJson from "react-json-view";

const UsernamePrompt: FunctionComponent<{
  setUsername: (username: string) => void;
}> = ({ setUsername }) => {
  return (
    <Form<{ username: string }>
      onFinish={(values) => {
        setUsername(values.username);
      }}
      initialValues={{ username: "" }}
    >
      <Form.Item
        label="Username"
        name="username"
        rules={[{ required: true, message: "Please input a username" }]}
      >
        <Input />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Continue
        </Button>
      </Form.Item>
    </Form>
  );
};

const GamePrompt: FunctionComponent<{
  setGameId: (id: string) => void;
  createGame: () => void;
}> = ({ setGameId, createGame }) => {
  return (
    <>
      <Button onClick={createGame}>Create game</Button>
      <Form<{ gameId: string }>
        onFinish={(values) => {
          setGameId(values.gameId);
        }}
        initialValues={{ gameId: "" }}
      >
        <Form.Item
          label="Game ID"
          name="gameId"
          rules={[{ required: true, message: "Please input a game ID" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Continue
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

const Play = () => {
  const dispatch = useTypedDispatch();

  const username = useTypedSelector((state) => state.user.username);
  const gameID = useTypedSelector((state) => state.game.id);
  const gameState = useTypedSelector((state) => state.game.gameState);

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
          dispatch({ type: ActionTypes.JoinGame, payload: gameID })
        }
      />
    );
  }

  return (
    gameState && (
      <code>
        <ReactJson src={gameState} />
      </code>
    )
  );
};

export default Play;
