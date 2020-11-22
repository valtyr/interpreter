import { Actions, ActionTypes } from "../actions";

interface ErrorsState {
  errors: {
    timestamp: number;
    title: string;
    message: string;
  }[];
}

const initialState: ErrorsState = {
  errors: [],
};

export const errors = (state = initialState, action: Actions): ErrorsState => {
  switch (action.type) {
    case ActionTypes.ErrorRecieved:
      return {
        ...state,
        errors: [{ ...action.payload, timestamp: Date.now() }, ...state.errors],
      };
    default:
      return state;
  }
};
