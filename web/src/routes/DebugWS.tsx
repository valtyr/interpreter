import React, { useState } from "react";

import { Button, Col, Input, List, Modal, PageHeader, Row, Table } from "antd";
import ReactJson from "react-json-view";
import { useHistory } from "react-router-dom";

import { useTypedDispatch, useTypedSelector } from "../store";
import { Actions, ActionTypes } from "../store/actions";
import ActionEditor from "../components/ActionEditor";

interface Event {
  time: number;
  type: string;
  payload: any;
}

const Payload = ({ item, record }: { item: string; record: Event }) => {
  const [modalOpen, setModalOpen] = useState(false);
  const pureRecord = { ...record } as any;
  delete pureRecord["time"];

  return (
    <div>
      <Modal
        footer={null}
        visible={modalOpen}
        onCancel={() => setModalOpen(false)}
      >
        <ReactJson src={pureRecord} />
      </Modal>
      <Button onClick={() => setModalOpen(true)}>View payload</Button>
    </div>
  );
};

const DebugWS = () => {
  const dispatch = useTypedDispatch();
  const events = useTypedSelector((state) => state.debug.messages) as Event[];

  const history = useHistory();

  const [username, setUsername] = useState("");

  const columns = [
    {
      title: "Time",
      dataIndex: "time",
      key: "time",
      render: (item: number) => new Date(item).toLocaleString(),
    },
    {
      title: "Type",
      dataIndex: "type",
      key: "type",
      filters: Array.from(new Set(events.map((e) => e.type))).map((f) => ({
        text: f,
        value: f,
      })),
      onFilter: (
        value: any,
        record: {
          time: number;
          type: string;
          payload?: any;
        }
      ) => record.type === value,
    },
    {
      title: "Payload",
      dataIndex: "payload",
      key: "payload",
      render: (item: string, record: Event) => (
        <Payload item={item} record={record} />
      ),
    },
  ];

  return (
    <>
      <PageHeader
        onBack={() => history.goBack()}
        title={"Debug"}
        subTitle="Message list"
      />

      <Row gutter={10}>
        <Col flex={1}>
          <Input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Username"
          />
        </Col>
        <Col>
          <Button
            onClick={() =>
              dispatch({ type: ActionTypes.SetUsername, payload: username })
            }
          >
            Set username
          </Button>
        </Col>
      </Row>
      <Button onClick={() => dispatch({ type: ActionTypes.Ping })}>Ping</Button>

      <ActionEditor />

      <Table columns={columns} dataSource={events}></Table>
    </>
  );
};

export default DebugWS;
