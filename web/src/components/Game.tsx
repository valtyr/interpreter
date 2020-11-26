import Icon, {
  CloseOutlined,
  CrownTwoTone,
  FrownTwoTone,
  StarTwoTone,
} from "@ant-design/icons";
import {
  Card,
  Col,
  Row,
  List,
  Typography,
  Tooltip,
  Button,
  Form,
  Input,
  Empty,
  ConfigProvider,
  Space,
} from "antd";
import { FormInstance } from "antd/lib/form";
import React, { FunctionComponent, useRef } from "react";
import { useTypedDispatch } from "../store";
import { ActionTypes } from "../store/actions";
import { GameState, Player, Card as CardType } from "../store/types";
import Logo from "./Logo";
import Round from "./Round";
import WinState from "./WinState";

const MIN_PLAYERS = 3;
const MIN_WORDS = 5;

const canStartGame = (
  playerCount: number,
  wordCount: number
): { valid: boolean; reason: string } => {
  if (playerCount < MIN_PLAYERS)
    return {
      valid: false,
      reason: `At least ${MIN_PLAYERS} players must have joined before the game can start`,
    };
  if (wordCount < MIN_WORDS)
    return {
      valid: false,
      reason: `You need to add at least ${MIN_WORDS} terms before the game can start`,
    };
  return { valid: true, reason: "" };
};

const sortPlayers = (players: Player[]) => {
  const sortedPlayers = [...players];
  sortedPlayers.sort((a, b) => b.score - a.score);
  return sortedPlayers;
};

const PlayerList: FunctionComponent<{
  playerList: GameState["playerList"];
  playerId: string;
  creatorId: string;
}> = ({ playerId, playerList, creatorId }) => {
  return (
    <Card size="small" title="Players" bodyStyle={{ padding: 0 }}>
      <List
        size="small"
        dataSource={sortPlayers(playerList)}
        rowKey={(player) => String(player.id)}
        renderItem={(player) => (
          <List.Item
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "stretch",
            }}
          >
            <Row>
              <Col flex={1}>
                <Typography.Text strong={player.connectionId === playerId}>
                  {player.connectionId === playerId && (
                    <Tooltip title="You">
                      <StarTwoTone style={{ marginRight: 10 }} />
                    </Tooltip>
                  )}
                  {player.connectionId === creatorId && (
                    <Tooltip title="Host">
                      <CrownTwoTone
                        twoToneColor="gold"
                        style={{ marginRight: 10 }}
                      />
                    </Tooltip>
                  )}
                  {player.name}
                </Typography.Text>
              </Col>
              <Col>
                <Typography.Text>{player.score}</Typography.Text>
              </Col>
            </Row>
          </List.Item>
        )}
      />
    </Card>
  );
};

const sortCards = (cards: CardType[]) => {
  const sortedCards = [...cards];
  sortedCards.sort((a, b) => a.word.localeCompare(b.word));
  return sortedCards;
};

const customizeRenderEmpty = () => (
  <div style={{ textAlign: "center", padding: 20 }}>
    <FrownTwoTone style={{ fontSize: 30, marginBottom: 10 }} />
    <Typography.Text>
      <br />
      You haven't added any terms
    </Typography.Text>
  </div>
);

const AddWords: FunctionComponent<{
  addWord: (word: string) => void;
  removeWord: (id: number) => void;
  filteredWordList: GameState["cardGroup"]["cards"];
  wordCount: number;
  playerCount: number;
  startGame: () => void;
  isHost: boolean;
}> = ({
  addWord,
  removeWord,
  filteredWordList,
  wordCount,
  playerCount,
  startGame,
  isHost,
}) => {
  const form = useRef<FormInstance>(null);

  const { valid, reason } = canStartGame(playerCount, wordCount);

  return (
    <>
      <Row gutter={10}>
        <Col span={24}>
          <Typography.Title level={4}>Add terms</Typography.Title>
          <Typography.Paragraph>
            To begin with, you and your friends need to add at least 5 terms.
            Once you've added enough words the host can start the game.
          </Typography.Paragraph>
        </Col>
      </Row>
      <Form<{ word: string }>
        ref={form}
        onFinish={(values) => {
          addWord(values.word);
          form.current?.setFieldsValue({ word: "" });
        }}
        initialValues={{ username: "" }}
      >
        <Row gutter={20}>
          <Col flex={1}>
            <Form.Item
              name="word"
              rules={[{ required: true, message: "Please input a term" }]}
            >
              <Input placeholder="Word" />
            </Form.Item>
          </Col>
          <Col>
            <Form.Item>
              <Button type="primary" htmlType="submit">
                Add word
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
      <ConfigProvider renderEmpty={customizeRenderEmpty}>
        <List
          header={<Typography.Text strong>Your terms</Typography.Text>}
          footer={
            <Typography.Text>
              This game currently has{" "}
              <Typography.Text strong>{wordCount}</Typography.Text> terms of
              which{" "}
              <Typography.Text strong>
                {filteredWordList.length}
              </Typography.Text>{" "}
              are yours
            </Typography.Text>
          }
          size="small"
          dataSource={sortCards(filteredWordList)}
          rowKey={(card) => String(card.id)}
          bordered
          renderItem={(card) => (
            <List.Item>
              <Typography.Text>{card.word}</Typography.Text>
              <Tooltip title="Remove term">
                <CloseOutlined
                  color="grey"
                  onClick={() => removeWord(card.id)}
                />
              </Tooltip>
            </List.Item>
          )}
        />
      </ConfigProvider>
      {isHost && (
        <>
          <Button
            type="primary"
            block
            shape="round"
            disabled={!valid}
            onClick={startGame}
            style={{ marginTop: 30 }}
          >
            Start game
          </Button>
          {!valid && (
            <Typography.Text
              style={{
                fontSize: 12,
                color: "grey",
                textAlign: "center",
                display: "block",
                marginTop: 5,
              }}
            >
              {reason}
            </Typography.Text>
          )}
        </>
      )}
      {!isHost && (
        <>
          <Button
            type="primary"
            block
            shape="round"
            disabled={true}
            style={{ marginTop: 30 }}
          >
            Start game
          </Button>
          <Typography.Text
            style={{
              fontSize: 12,
              color: "grey",
              textAlign: "center",
              display: "block",
              marginTop: 5,
            }}
          >
            Only the host can start a game
          </Typography.Text>
        </>
      )}
    </>
  );
};

const Game: FunctionComponent<{ gameState: GameState; player: Player }> = ({
  gameState,
  player,
}) => {
  const dispatch = useTypedDispatch();

  if (gameState.gameOver) {
    return (
      <WinState
        gameState={gameState}
        isHost={gameState.creator.id === player.id}
      />
    );
  }

  return (
    <Space direction="vertical" style={{ width: "100%" }}>
      <Card>
        <Row justify={"center"}>
          <Logo width={400} />
        </Row>
      </Card>
      <Row gutter={10}>
        <Col span={18}>
          <Card
            title={
              !gameState.gameStartedTime ? (
                <Typography.Title level={5}>
                  Tell your friends to join using code{" "}
                  <Typography.Text code>{gameState.shareId}</Typography.Text>
                </Typography.Title>
              ) : (
                <Typography.Title level={5}>
                  Tell your friends they were too late to join
                </Typography.Title>
              )
            }
          >
            {!gameState.gameStartedTime && (
              <AddWords
                addWord={(word) =>
                  dispatch({ type: ActionTypes.AddWord, payload: word })
                }
                removeWord={(id) =>
                  dispatch({
                    type: ActionTypes.DeleteWord,
                    payload: { id },
                  })
                }
                wordCount={gameState.cardGroup.cards.length}
                playerCount={gameState.playerList.length}
                filteredWordList={gameState.cardGroup.cards.filter(
                  (card) => card.creatorId === player.connectionId
                )}
                startGame={() => {
                  dispatch({ type: ActionTypes.StartGame });
                }}
                isHost={gameState.creator.id === player.id}
              ></AddWords>
            )}
            {gameState.gameStartedTime && (
              <Round gameState={gameState} player={player} />
            )}
          </Card>
        </Col>
        <Col span={6}>
          <PlayerList
            playerList={gameState.playerList}
            playerId={player.connectionId}
            creatorId={gameState.creator.connectionId}
          />
        </Col>
      </Row>
    </Space>
  );
};

export default Game;
