import { createAction } from "@reduxjs/toolkit";
import { CardGroup } from "./types";

export enum ActionTypes {
  FetchCardGroups = "FETCH_CARD_GROUPS",
  ReceiveCardGroups = "RECEIVE_CARD_GROUPS",
  CreateCardGroup = "CREATE_CARD_GROUP",
  CardGroupCreated = "CARD_GROUP_CREATED",
  Ping = "PING",
  Pong = "PONG",
  Connect = "CONNECT",
  Connected = "CONNECTED",
  Disconnected = "DISCONNECTED",
  SetUsername = "SET_USERNAME",
  UsernameSet = "USERNAME_SET",
}

// Card group actions

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

// User action

interface SetUsernameAction {
  type: ActionTypes.SetUsername;
  payload: string;
}

interface UsernameSetAction {
  type: ActionTypes.UsernameSet;
  payload: string;
}

export type UserActions = SetUsernameAction | UsernameSetAction;

// Debug Actions

interface PingAction {
  type: ActionTypes.Ping;
}

interface PongAction {
  type: ActionTypes.Pong;
}

interface ConnectAction {
  type: ActionTypes.Connect;
}

interface ConnectedAction {
  type: ActionTypes.Connected;
}

interface DisconnectedAction {
  type: ActionTypes.Disconnected;
}

export type DebugActions =
  | PingAction
  | PongAction
  | ConnectAction
  | ConnectedAction
  | DisconnectedAction;

export type Actions = CardGroupActions | DebugActions | UserActions;
