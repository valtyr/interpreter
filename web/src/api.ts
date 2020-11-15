import axios from "axios";
import { store } from "./store";
import { ActionTypes } from "./store/actions";

const CARD_GROUPS = "http://localhost:8080/api/cardgroups";
const ADD_CARD_GROUP = "http://localhost:8080/api/addcardgroups";

export const fetchCardGroups = async () => {
  store.dispatch({ type: ActionTypes.FetchCardGroups });
  const resp = await axios.get(CARD_GROUPS);
  store.dispatch({ type: ActionTypes.ReceiveCardGroups, payload: resp.data });
};

export const addCardGroup = async (name: string) => {
  const resp = await axios.post(ADD_CARD_GROUP, { name });
  return resp.data;
};
