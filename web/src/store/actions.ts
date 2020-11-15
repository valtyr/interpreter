import { createAction } from "@reduxjs/toolkit";
import { CardGroup } from "./types";

export enum ActionTypes {
  FetchCardGroups = "FETCH_CARD_GROUPS",
  ReceiveCardGroups = "RECEIVE_CARD_GROUPS",
  CreateCardGroup = "CREATE_CARD_GROUP",
  CardGroupCreated = "CARD_GROUP_CREATED",
}

interface FetchCardGroupsAction {
  type: ActionTypes.FetchCardGroups;
}

interface ReceiveCardGroupsAction {
  type: ActionTypes.ReceiveCardGroups;
  payload: CardGroup[];
}

interface CreateCardGroupAction {
  type: ActionTypes.CreateCardGroup;
}

interface CardGroupCreatedAction {
  type: ActionTypes.CardGroupCreated;
  payload: CardGroup;
}

export type CardGroupActions =
  | FetchCardGroupsAction
  | ReceiveCardGroupsAction
  | CreateCardGroupAction
  | CardGroupCreatedAction;

export type Actions = CardGroupActions;
