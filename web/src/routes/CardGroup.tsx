import {
  Button,
  Card,
  Col,
  PageHeader,
  Rate,
  Result,
  Row,
  Space,
  Spin,
  Statistic,
  Table,
} from "antd";
import { CloseOutlined, EditOutlined } from "@ant-design/icons";
import React, { FunctionComponent, useEffect, useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import { useTypedSelector } from "../store";
import { fetchCardGroups } from "../api";

const columns = [{ title: "Word", dataIndex: "word", key: "word" }];

const CardGroup: FunctionComponent = () => {
  const { id } = useParams<{ id: string }>();
  const history = useHistory();

  useEffect(() => {
    fetchCardGroups();
  }, []);

  const data = useTypedSelector((state) => state.cardGroups);
  const cardGroup = data.cardGroups.find((cg) => cg.id === Number(id));

  const [editMode, setEditMode] = useState(false);

  if (data.loading)
    return (
      <>
        <PageHeader
          onBack={() => history.push("/")}
          title={"Loading"}
          subTitle="Card group"
        />

        <Spin />
      </>
    );
  if (!cardGroup)
    return (
      <Result
        status="404"
        title="404"
        subTitle="Sorry, the page you visited does not exist."
        extra={
          <Link to="/">
            <Button type="primary">Back Home</Button>
          </Link>
        }
      />
    );

  return (
    <>
      <PageHeader
        onBack={() => history.push("/")}
        title={cardGroup.name}
        subTitle="Card group"
        extra={
          <Button
            icon={editMode ? <CloseOutlined /> : <EditOutlined />}
            shape="circle"
            onClick={() => setEditMode(!editMode)}
          />
        }
      />
      <Card title="Stats">
        <Row>
          <Col lg={6} md={12} sm={24} xs={24}>
            <Statistic
              title="Rating"
              value={cardGroup.rating}
              suffix="stars"
              prefix={
                <Rate allowHalf disabled defaultValue={cardGroup.rating} />
              }
            />
          </Col>
          <Col lg={6} md={12} sm={24} xs={24}>
            <Statistic
              title="Date created"
              value={new Date(cardGroup.creationDate).toLocaleString(
                undefined,
                {
                  day: "numeric",
                  month: "numeric",
                  year: "numeric",
                  hour12: false,
                  hour: "numeric",
                  minute: "2-digit",
                }
              )}
            />
          </Col>
          <Col lg={8} md={12} sm={24} xs={24}>
            <Statistic title="Author" value={cardGroup.creator.name} />
          </Col>
          <Col lg={4} md={12} sm={24} xs={24}>
            <Statistic
              title="Played"
              value={cardGroup.timesUsed}
              suffix="times"
            />
          </Col>
        </Row>
      </Card>
      <Space direction="vertical" style={{ width: "100%" }}>
        <Table
          // title="Words"
          columns={columns}
          size="large"
          dataSource={cardGroup.cards}
        />
      </Space>
    </>
  );
};

export default CardGroup;
