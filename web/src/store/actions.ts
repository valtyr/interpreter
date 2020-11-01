import { createAction } from "@reduxjs/toolkit";
import { CardGroup } from "./types";

export enum ActionTypes {
  FetchCardGroups = "FETCH_CARD_GROUPS",
  ReceiveCardGroups = "RECEIVE_CARD_GROUPS",
}

interface FetchCardGroupsAction {
  type: ActionTypes.FetchCardGroups;
}

interface ReceiveCardGroupsAction {
  type: ActionTypes.ReceiveCardGroups;
  payload: CardGroup[];
}

export type CardGroupActions = FetchCardGroupsAction | ReceiveCardGroupsAction;

export type Actions = CardGroupActions;
