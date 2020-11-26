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

const WINNING_STATE: Game = {
  id: "LJTCFE",
  gameState: {
    id: 13,
    gameStartedTime: "2020-11-25T21:19:14Z",
    turnStartedTime: "2020-11-25T21:22:56Z",
    creator: {
      id: 15,
      connectionId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
      name: "valtyr",
      score: 22,
    },
    playerList: [
      {
        id: 17,
        connectionId: "ded66891-47d2-4dd2-b7cc-fb4f38637cac",
        name: "sælir menn",
        score: 20,
      },
      {
        id: 15,
        connectionId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
        name: "valtyr",
        score: 22,
      },
      {
        id: 16,
        connectionId: "a92e6125-31b4-4b80-a7fb-924edabf22ff",
        name: "Jeff Pesos",
        score: 22,
      },
    ],
    currentPlayer: {
      id: 15,
      connectionId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
      name: "valtyr",
      score: 22,
    },
    currentGuesser: {
      id: 17,
      connectionId: "ded66891-47d2-4dd2-b7cc-fb4f38637cac",
      name: "sælir menn",
      score: 20,
    },
    currentRound: 5,
    currentCard: {
      id: 20,
      word: "Gaggalagú",
      creatorId: "a92e6125-31b4-4b80-a7fb-924edabf22ff",
    },
    cardGroup: {
      id: 16,
      cards: [
        {
          id: 16,
          word: "þrútið typpi",
          creatorId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
        },
        {
          id: 15,
          word: "ýstra",
          creatorId: "ded66891-47d2-4dd2-b7cc-fb4f38637cac",
        },
        {
          id: 17,
          word: "stoner boner",
          creatorId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
        },
        {
          id: 14,
          word: "honk honk",
          creatorId: "ded66891-47d2-4dd2-b7cc-fb4f38637cac",
        },
        {
          id: 21,
          word: "rafáll",
          creatorId: "ded66891-47d2-4dd2-b7cc-fb4f38637cac",
        },
        {
          id: 20,
          word: "Gaggalagú",
          creatorId: "a92e6125-31b4-4b80-a7fb-924edabf22ff",
        },
        {
          id: 18,
          word: "Michael Fassbender",
          creatorId: "a92e6125-31b4-4b80-a7fb-924edabf22ff",
        },
        {
          id: 19,
          word: "andri vampíra",
          creatorId: "f20fd769-71f2-4d33-9532-ec7c840c52c1",
        },
      ],
      creator: {
        id: 1,
        email: "",
        name: "",
      },
      name: "null",
      creationDate: new Date(),
      rating: 0,
      timesUsed: 4,
      tags: [],
    },
    shareId: "LJTCFE",
    cardSequence: [],
    turnInProgress: false,
    gameOver: true,
    winner: null,
  },
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
