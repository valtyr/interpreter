import { act } from "react-dom/test-utils";
import { Actions, ActionTypes } from "../actions";
import { GameState } from "../types";

interface Game {
  id: string | null;
  gameState: GameState | null;
}

const initialState: Game = {
  id: null,
  gameState: null,
};

export const game = (state = initialState, action: Actions): Game => {
  switch (action.type) {
    case ActionTypes.GameJoined:
      return {
        ...state,
        id: action.payload.gameState.id.toString(),
        gameState: action.payload.gameState,
      };
    case ActionTypes.GameCreated:
      return {
        ...state,
        id: action.payload.id,
        gameState: action.payload.gameState,
      };
    case ActionTypes.GameUpdated:
      return {
        ...state,
        gameState: action.payload.gameState,
      };
    default:
      return state;
  }
};
