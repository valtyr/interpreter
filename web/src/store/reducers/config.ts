import { Actions, ActionTypes } from "../actions";

interface ConfigState {
  confettiOn: boolean;
}

const initialState: ConfigState = {
  confettiOn: false,
};

export const config = (state = initialState, action: Actions): ConfigState => {
  switch (action.type) {
    case ActionTypes.ConfettiOn:
      return { ...state, confettiOn: true };
    case ActionTypes.ConfettiOff:
      return { ...state, confettiOn: false };
    default:
      return state;
  }
};
