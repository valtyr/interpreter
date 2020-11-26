import React, { FunctionComponent, useEffect, useRef } from "react";
import {
  Space,
  Card,
  Row,
  Col,
  Typography,
  Form,
  Input,
  Select,
  Button,
} from "antd";
import { GameState, Player } from "../store/types";
import Logo from "./Logo";

import { motion, useAnimation } from "framer-motion";

import styles from "./WinState.module.css";
import { useTypedDispatch } from "../store";
import { ActionTypes } from "../store/actions";
import { CheckCircleTwoTone, CrownTwoTone } from "@ant-design/icons";

const sortLeaderboard = (players: Player[]) => {
  const sortedPlayers = [...players];
  sortedPlayers.sort((a, b) => b.score - a.score);
  return sortedPlayers;
};

const PlaceAnimation: FunctionComponent<{
  name: string;
  place: string;
  delay?: number;
  onComplete?: () => void;
  crown?: boolean;
}> = ({ name, place, delay = 0, onComplete, crown = false }) => {
  const descriptionControls = useAnimation();
  const nameControls = useAnimation();
  const crownControls = useAnimation();

  useEffect(() => {
    (async () => {
      descriptionControls.set({
        opacity: 0,
        y: 10,
      });
      nameControls.set({
        opacity: 0,
        y: 10,
      });

      crownControls.set({
        opacity: 0,
        rotate: 30,
      });

      await descriptionControls.start({
        opacity: 1,
        y: 0,
        transition: { duration: 0.4, delay },
      });
      await nameControls.start({
        opacity: 1,
        y: 0,
        transition: { duration: 0.4, delay: 0.8 },
      });

      if (crown) {
        crownControls.start({
          opacity: 1,
          transition: {
            duration: 0.3,
          },
        });
        crownControls.start({
          rotate: 0,
          transition: {
            type: "spring",
            stiffness: 2000,
          },
        });
      }

      if (onComplete) onComplete();
    })();
  }, []);

  return (
    <div className={styles.placing}>
      {crown && (
        <motion.div animate={crownControls} className={styles.crown}>
          <CrownTwoTone twoToneColor="gold" />
        </motion.div>
      )}
      <motion.div animate={descriptionControls} className={styles.description}>
        In {place} place
      </motion.div>
      <motion.div animate={nameControls} className={styles.name}>
        {name}
      </motion.div>
    </div>
  );
};

const WinState: FunctionComponent<{
  gameState: GameState;
  isHost: boolean;
}> = ({ gameState, isHost }) => {
  const dispatch = useTypedDispatch();

  const leaderboard = sortLeaderboard(gameState.playerList);
  const rootRef = useRef<HTMLDivElement>(null);

  const winnerControls = useAnimation();

  return (
    <Space direction="vertical" style={{ width: "100%" }}>
      <Card>
        <Row justify={"center"}>
          <Logo width={400} />
        </Row>
      </Card>
      <Card>
        <div className={styles.root} ref={rootRef}>
          <PlaceAnimation
            place="second"
            name={leaderboard[1]?.name}
            delay={4}
          />
          <motion.div animate={winnerControls}>
            <PlaceAnimation
              place="first"
              name={leaderboard[0].name}
              delay={8}
              crown
              onComplete={() => {
                dispatch({ type: ActionTypes.ConfettiOn });
                winnerControls.start({
                  scale: 1.5,
                  transition: {
                    type: "spring",
                    damping: 5,
                    stiffness: 500,
                  },
                });
              }}
            />
          </motion.div>
          <PlaceAnimation place="third" name={leaderboard[2]?.name} />
        </div>
      </Card>

      {isHost && (
        <Row gutter={10}>
          <Col span={12}>
            <Card title="Did you enjoy this game?">
              <Typography.Paragraph>
                If so, consider adding your cards to the library! If you do,
                other users of ntrprtr can play a match with the words you just
                used!
              </Typography.Paragraph>
            </Card>
          </Col>
          <Col span={12}>
            <Card title="Publish to library">
              {!gameState.cardGroup.published && (
                <Form<{ name: string; tags: string[] }>
                  onFinish={(values) => {
                    dispatch({
                      type: ActionTypes.PublishCardgroup,
                      payload: {
                        name: values.name,
                        tags: values.tags,
                      },
                    });
                  }}
                  initialValues={{ gameId: "" }}
                >
                  <Form.Item
                    name="name"
                    rules={[
                      {
                        required: true,
                        message: "Please enter a name for your cards",
                      },
                    ]}
                  >
                    <Input placeholder="Name" style={{ width: "100%" }} />
                  </Form.Item>
                  <Form.Item
                    name="tags"
                    rules={[
                      {
                        required: true,
                        message: "Please tag your cards",
                      },
                    ]}
                  >
                    <Select
                      mode="tags"
                      style={{ width: "100%" }}
                      placeholder="Tags"
                      open={false}
                      onKeyDown={(e) =>
                        e.keyCode == 13 ? e.preventDefault() : ""
                      }
                    />
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary" block htmlType="submit">
                      Publish
                    </Button>
                  </Form.Item>
                </Form>
              )}
              {gameState.cardGroup.published && (
                <Row>
                  <Space
                    style={{ width: "100%", alignItems: "center" }}
                    direction="vertical"
                  >
                    <CheckCircleTwoTone style={{ fontSize: 30 }} />
                    <Typography.Title level={3}>
                      Cards published!
                    </Typography.Title>
                  </Space>
                </Row>
              )}
            </Card>
          </Col>
        </Row>
      )}
    </Space>
  );
};

export default WinState;
