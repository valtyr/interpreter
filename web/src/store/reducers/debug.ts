import { Actions, ActionTypes } from "../actions";

interface DebugState {
  messages: {
    time: number;
    type: string;
    payload?: any;
  }[];
}

const initialState: DebugState = {
  messages: [],
};

export interface ActionWithPayload {
  type: ActionTypes;
  payload?: any;
}

export const debug = (state = initialState, action: Actions) => {
  switch (action.type) {
    default:
      return {
        ...state,
        messages: [
          {
            type: action.type,
            time: Date.now(),
            payload: (action as ActionWithPayload).payload,
          },
          ...state.messages,
        ],
      };
  }
};
