import React, { useEffect } from "react";
import { Button, PageHeader, Rate, Table, Tag } from "antd";
import { FunctionComponent } from "react";
import { useTypedSelector } from "../store";
import { Card } from "../store/types";
import { useHistory } from "react-router-dom";
import { items } from "../helpers";
import { fetchCardGroups } from "../api";

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

  useEffect(() => {
    fetchCardGroups();
  }, []);

  return (
    <>
      <PageHeader title="Card groups" />
      <Table
        columns={columns}
        size="large"
        dataSource={data.cardGroups.filter((c) => c.published)}
        loading={data.loading}
        onRow={(item) => ({
          onClick: () => history.push(`/cardgroup/${item.id}`),
        })}
      />
    </>
  );
};

export default CardGroupList;
