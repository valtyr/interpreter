import axios from "axios";
import { store } from "./store";
import { ActionTypes } from "./store/actions";

const CARD_GROUP_REST_API_URL = "http://localhost:8080/api/cardGroups";

export const fetchCardGroups = async () => {
  store.dispatch({ type: ActionTypes.FetchCardGroups });
  const resp = await axios.get(CARD_GROUP_REST_API_URL);
  store.dispatch({ type: ActionTypes.ReceiveCardGroups, payload: resp.data });
};
