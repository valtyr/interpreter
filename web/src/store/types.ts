export interface User {
  id: number;
  name: string;
  email: string;
}

export interface Card {
  id: number;
  word: string;
}

export interface CardGroup {
  id: number;
  name: string;
  cards: Card[];
  creator: User;
  creationDate: Date;
  timesUsed: number;
  tags: string[];
  rating: number;
}
