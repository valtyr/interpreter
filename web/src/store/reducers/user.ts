import { Actions, ActionTypes } from "../actions";
import { Player } from "../types";

interface UserState {
  username: string | null;
  player: Player | null;
}

const initialState: UserState = {
  username: null,
  player: null,
};

export const user = (state = initialState, action: Actions): UserState => {
  switch (action.type) {
    case ActionTypes.UsernameSet:
      return {
        ...state,
        username: action.payload.username,
        player: action.payload.player,
      };
    default:
      return state;
  }
};
