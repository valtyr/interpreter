import {
  Alert,
  Button,
  Col,
  Divider,
  Row,
  Statistic,
  Tooltip,
  Typography,
} from "antd";
import Countdown from "antd/lib/statistic/Countdown";
import React, { FunctionComponent, useCallback, useState } from "react";
import { useTypedDispatch } from "../store";
import { ActionTypes } from "../store/actions";
import { Card, GameState, Player } from "../store/types";

import RoundCountdown from "./Countdown";

const TURN_LENGTH = 60 * 1000;

const titleForRound = (round: number) => {
  switch (round) {
    case 1:
      return "Describe the term";
    case 2:
      return "Act it out!";
    case 3:
      return "One word";
    case 4:
      return "Sculptures";
  }
};

const Turn: FunctionComponent<{
  currentWord: Card;
  correct: () => void;
  skip: () => void;
  turnStartedTime: string;
  currentlyInterpreter: boolean;
  onFinish: () => void;
  round: number;
  interpreter: Player;
  guesser: Player;
}> = ({
  currentWord,
  correct,
  skip,
  turnStartedTime,
  currentlyInterpreter,
  onFinish,
  round,
  interpreter,
  guesser,
}) => {
  const [showCountdown, setShowCountdown] = useState(true);
  if (showCountdown) {
    return (
      <Row justify="center">
        <RoundCountdown
          onCountdownEnded={() => {
            setShowCountdown(false);
          }}
        />
      </Row>
    );
  }

  if (!currentlyInterpreter) {
    return (
      <>
        <Row>
          <Col span={6}>
            <Statistic title="Round" value={round}></Statistic>
          </Col>
          <Col span={10}>
            <Statistic
              title="Directions"
              value={titleForRound(round)}
            ></Statistic>
          </Col>
          <Col span={8}>
            <Countdown
              title="Time left"
              value={Date.parse(turnStartedTime) + TURN_LENGTH + 4000}
              format="mm:ss:SS"
            />
          </Col>
        </Row>
        <Divider type="horizontal" />
        <Row>
          <Col span={12} style={{ textAlign: "center" }}>
            <Typography.Text>Interpreter</Typography.Text>
            <Typography.Title level={3}>{interpreter.name}</Typography.Title>
          </Col>
          <Col span={12} style={{ textAlign: "center" }}>
            <Typography.Text>Guesser</Typography.Text>
            <Typography.Title level={3}>{guesser.name}</Typography.Title>
          </Col>
        </Row>
      </>
    );
  }

  return (
    <>
      <Row>
        <Col span={6}>
          <Statistic title="Round" value={round}></Statistic>
        </Col>
        <Col span={10}>
          <Statistic
            title="Directions"
            value={titleForRound(round)}
          ></Statistic>
        </Col>
        <Col span={8}>
          <Countdown
            title="Time left"
            value={Date.parse(turnStartedTime) + TURN_LENGTH + 4000}
            onFinish={onFinish}
            format="mm:ss:SS"
          />
        </Col>
      </Row>

      <Row>
        <Col style={{ textAlign: "center" }} span={24}>
          <Divider />
          <Typography.Text
            style={{ marginTop: 70, marginBottom: -30, display: "block" }}
          >
            Your term:
          </Typography.Text>
          <Typography.Title
            style={{ marginBottom: 70, display: "block" }}
            level={1}
          >
            {currentWord.word}
          </Typography.Title>
          <Divider />
          <Row gutter={10}>
            <Col span={12}>
              <Button block type="primary" onClick={correct}>
                Correct guess
              </Button>
            </Col>
            <Col span={12}>
              <Button block danger onClick={skip}>
                Pass
              </Button>
            </Col>
          </Row>
        </Col>
      </Row>
    </>
  );
};

const CurrentPlayers: FunctionComponent<{
  guesser: Player;
  interpreter: Player;
  currentlyPlaying: boolean;
  currentlyInterpreter: boolean;
  startTurn: () => void;
}> = ({
  guesser,
  interpreter,
  currentlyPlaying,
  currentlyInterpreter,
  startTurn,
}) => {
  return (
    <>
      {currentlyPlaying && (
        <Alert
          style={{ textAlign: "center", marginBottom: 20 }}
          message="Heads up! You're next."
          type="info"
        />
      )}
      <Row>
        <Col span={11} style={{ textAlign: "center" }}>
          <Typography.Text>Interpreter</Typography.Text>
          <Typography.Title level={3}>{interpreter.name}</Typography.Title>
        </Col>
        <Col span={2}>
          <Divider type="vertical" />
        </Col>
        <Col span={11} style={{ textAlign: "center" }}>
          <Typography.Text>Guesser</Typography.Text>
          <Typography.Title level={3}>{guesser.name}</Typography.Title>
        </Col>
      </Row>

      {currentlyInterpreter ? (
        <Button
          block
          size="large"
          type="primary"
          shape="round"
          style={{ marginTop: 20 }}
          onClick={startTurn}
        >
          Start turn
        </Button>
      ) : (
        <>
          <Tooltip title="Waiting for interpreter to start turn">
            <Button
              disabled
              block
              size="large"
              type="primary"
              shape="round"
              style={{ marginTop: 20 }}
            >
              Start turn
            </Button>
          </Tooltip>
        </>
      )}
    </>
  );
};

const descriptionForRound = (round: number) => {
  switch (round) {
    case 1:
      return (
        <Typography.Paragraph>
          <strong>A round of Alias:</strong> The interpreter must describe the
          term to the guesser without using any part of the term itself. Please
          note that saying the word in another language is prohibited.
        </Typography.Paragraph>
      );
    case 2:
      return (
        <Typography.Paragraph>
          <strong>A round of actionary:</strong> The interpreter must act out
          the term. No talking or sounds allowed.
        </Typography.Paragraph>
      );
    case 3:
      return (
        <Typography.Paragraph>
          <strong>One word:</strong> The intepreter has to describe the term
          using only one word.
        </Typography.Paragraph>
      );
    case 4:
      return (
        <Typography.Paragraph>
          <strong>Sculptures:</strong> The intepreter must act out the term by
          assuming a single pose.
        </Typography.Paragraph>
      );
    default:
      return null;
  }
};

const Round: FunctionComponent<{ gameState: GameState; player: Player }> = ({
  gameState,
  player,
}) => {
  const dispatch = useTypedDispatch();

  const cardsDone =
    gameState.cardSequence !== null && gameState.cardSequence.length === 0;

  if (
    gameState.gameStartedTime &&
    (!gameState.currentPlayer || !gameState.currentGuesser || cardsDone)
  ) {
    return (
      <>
        <Typography.Title level={3}>
          Round {gameState.currentRound}
        </Typography.Title>
        {descriptionForRound(gameState.currentRound!)}
        <Button
          block
          onClick={() => dispatch({ type: ActionTypes.StartRound })}
        >
          Start round
        </Button>
      </>
    );
  }

  const currentlyPlaying =
    player.id === gameState.currentGuesser?.id ||
    player.id === gameState.currentPlayer?.id;

  if (!gameState.turnInProgress) {
    return (
      <CurrentPlayers
        guesser={gameState.currentGuesser!}
        interpreter={gameState.currentPlayer!}
        currentlyPlaying={currentlyPlaying}
        currentlyInterpreter={player.id === gameState.currentPlayer?.id}
        startTurn={() => dispatch({ type: ActionTypes.StartTurn })}
      />
    );
  }

  return (
    <Turn
      correct={() => {
        dispatch({ type: ActionTypes.Correct });
        dispatch({ type: ActionTypes.ConfettiOn });
      }}
      guesser={gameState.currentGuesser!}
      interpreter={gameState.currentPlayer!}
      skip={() => dispatch({ type: ActionTypes.Skip })}
      currentWord={gameState.currentCard}
      currentlyInterpreter={player.id === gameState.currentPlayer?.id}
      turnStartedTime={gameState.turnStartedTime}
      onFinish={() => dispatch({ type: ActionTypes.EndTurn })}
      round={gameState.currentRound!}
    />
  );
};

export default Round;
