import { ActionTypes, CardGroupActions } from "../actions";
import { CardGroup } from "../types";

interface CardGroupState {
  loading: boolean;
  cardGroupsById: Record<string, CardGroup>;
}

const initialState: CardGroupState = {
  loading: false,
  cardGroupsById: {},
};

const mockState: CardGroupState = {
  loading: false,
  cardGroupsById: {
    "1": {
      id: 1,
      creator: {
        name: "Valtýr Örn Kjartansson",
        id: 1,
        email: "valtyr@gmail.com",
      },
      name: "Halloween pack",
      creationDate: new Date(),
      tags: ["spooky", "fun"],
      timesUsed: 30,
      cards: [
        { id: 1, word: "Pumpkin" },
        { id: 2, word: "Headless horseman" },
        { id: 3, word: "CheEzy bitEz" },
        { id: 4, word: "Máni" },
      ],
      rating: 4.5,
    },
    "2": {
      id: 2,
      creator: {
        name: "Valtýr Örn Kjartansson",
        id: 1,
        email: "valtyr@gmail.com",
      },
      name: "Christmas pack",
      creationDate: new Date(),
      tags: ["festive", "fun"],
      timesUsed: 32,
      cards: [
        { id: 5, word: "Santa claus" },
        { id: 6, word: "Present" },
        { id: 7, word: "Christmas tree" },
        { id: 8, word: "Yule log" },
      ],
      rating: 2.5,
    },
  },
};

export const cardGroups = (state = initialState, action: CardGroupActions) => {
  switch (action.type) {
    case ActionTypes.FetchCardGroups:
      return { ...state, loading: true };
    case ActionTypes.ReceiveCardGroups:
      return { ...state, loading: false, cardGroups: action.payload };
    default:
      return state;
  }
};
