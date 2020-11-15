import React from "react";
import { Button, PageHeader, Rate, Table, Tag } from "antd";
import { FunctionComponent } from "react";
import { useTypedSelector } from "../store";
import { Card } from "../store/types";
import { useHistory } from "react-router-dom";
import { items } from "../helpers";
import { PlusOutlined } from "@ant-design/icons";
import { addCardGroup } from "../api";

const columns = [
  { title: "Name", dataIndex: "name", key: "name" },
  {
    title: "# Cards",
    dataIndex: "cards",
    key: "cards",
    render: (cards: Card[]) => cards.length,
  },
  {
    title: "Tags",
    dataIndex: "tags",
    key: "tags",
    render: (tags: string[]) => (
      <>
        {tags.map((tag) => (
          <Tag key={tag}>{tag}</Tag>
        ))}
      </>
    ),
  },
  {
    title: "Rating",
    dataIndex: "rating",
    key: "rating",
    render: (rating: number) => (
      <Rate allowHalf disabled defaultValue={rating} />
    ),
  },
  { title: "Times played", dataIndex: "timesUsed", key: "timesUsed" },
];

const CardGroupList: FunctionComponent = () => {
  const data = useTypedSelector((state) => state.cardGroups);
  const history = useHistory();

  return (
    <>
      <PageHeader
        title="Card groups"
        extra={
          <Button
            icon={<PlusOutlined />}
            shape="circle"
            onClick={async () => {
              const res = await addCardGroup("BOB");
              console.log(res);
            }}
          />
        }
      />
      <Table
        columns={columns}
        size="large"
        dataSource={items(data.cardGroupsById)}
        loading={data.loading}
        onRow={(item) => ({
          onClick: () => history.push(`/cardgroup/${item.id}`),
        })}
      />
    </>
  );
};

export default CardGroupList;
