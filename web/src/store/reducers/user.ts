import { Actions, ActionTypes } from "../actions";

interface UserState {
  username: string | null;
}

const initialState: UserState = {
  username: null,
};

export const user = (state = initialState, action: Actions): UserState => {
  switch (action.type) {
    case ActionTypes.SetUsername:
    case ActionTypes.UsernameSet:
      return {
        ...state,
        username: action.payload,
      };
    default:
      return state;
  }
};
