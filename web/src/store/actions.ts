import { createAction } from "@reduxjs/toolkit";
import { CardGroup, GameState, Player } from "./types";

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
  JoinGame = "JOIN_GAME",
  GameJoined = "GAME_JOINED",
  CreateGame = "CREATE_GAME",
  StartGame = "START_GAME",
  StartRound = "START_ROUND",
  StartTurn = "START_TURN",
  EndTurn = "END_TURN",
  Correct = "CORRECT",
  Skip = "SKIP",
  PublishCardgroup = "PUBLISH_CARDGROUP",
  GameCreated = "GAME_CREATED",
  GameUpdated = "GAME_UPDATED",
  AddWord = "ADD_WORD",
  DeleteWord = "DELETE_WORD",
  ErrorRecieved = "ERROR_RECEIVED",
  ConfettiOn = "CONFETTI_ON",
  ConfettiOff = "CONFETTI_OFF",
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
  payload: {
    username: string;
    player: Player;
  };
}

export type UserActions = SetUsernameAction | UsernameSetAction;

// Game Actions

interface JoinGameAction {
  type: ActionTypes.JoinGame;
  payload: string;
}

interface GameJoinedAction {
  type: ActionTypes.GameJoined;
  payload: {
    id: string;
    gameState: GameState;
  };
}

interface CreateGameAction {
  type: ActionTypes.CreateGame;
}

interface StartGameAction {
  type: ActionTypes.StartGame;
}

interface StartRoundAction {
  type: ActionTypes.StartRound;
}

interface StartTurnAction {
  type: ActionTypes.StartTurn;
}

interface EndTurnAction {
  type: ActionTypes.EndTurn;
}

interface CorrectAction {
  type: ActionTypes.Correct;
}

interface SkipAction {
  type: ActionTypes.Skip;
}

interface GameCreatedAction {
  type: ActionTypes.GameCreated;
  payload: {
    id: string;
    gameState: GameState;
  };
}

interface GameUpdatedAction {
  type: ActionTypes.GameUpdated;
  payload: {
    gameState: GameState;
  };
}

interface AddWordAction {
  type: ActionTypes.AddWord;
  payload: string;
}

interface DeleteWordAction {
  type: ActionTypes.DeleteWord;
  payload: {
    id: number;
  };
}

interface PublishCardGroupAction {
  type: ActionTypes.PublishCardgroup;
  payload: {
    name: string;
    tags: string[];
  };
}

export type GameActions =
  | JoinGameAction
  | GameJoinedAction
  | CreateGameAction
  | StartGameAction
  | StartTurnAction
  | StartRoundAction
  | EndTurnAction
  | CorrectAction
  | SkipAction
  | GameCreatedAction
  | GameUpdatedAction
  | AddWordAction
  | DeleteWordAction
  | StartRoundAction
  | PublishCardGroupAction;

// Errors Actions

interface ErrorRecievedAction {
  type: ActionTypes.ErrorRecieved;
  payload: {
    title: string;
    message: string;
  };
}

export type ErrorActions = ErrorRecievedAction;

// Config actions

interface ConfettiOnAction {
  type: ActionTypes.ConfettiOn;
}

interface ConfettiOffAction {
  type: ActionTypes.ConfettiOff;
}

export type ConfigActions = ConfettiOnAction | ConfettiOffAction;

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

export type Actions =
  | CardGroupActions
  | DebugActions
  | UserActions
  | GameActions
  | ErrorActions
  | ConfigActions;
