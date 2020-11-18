import { configureStore } from "@reduxjs/toolkit";
import { combineReducers } from "@reduxjs/toolkit";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import wsMiddleware from "../ws/middleware";
import { Actions } from "./actions";
import { cardGroups } from "./reducers/cardGroups";
import { debug } from "./reducers/debug";
import { user } from "./reducers/user";

const rootReducer = combineReducers({ cardGroups, debug, user });

export const store = configureStore({
  reducer: rootReducer,
  middleware: [wsMiddleware],
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof store.dispatch;
export type Store = typeof store;

export type Dispatch = <TReturnType>(action: Actions) => TReturnType;
export const useTypedSelector: TypedUseSelectorHook<RootState> = useSelector;
export const useTypedDispatch = () => useDispatch<Dispatch>();
