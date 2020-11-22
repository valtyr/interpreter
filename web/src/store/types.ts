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

export interface Player {
  id: number;

  name: string;
  connectionId: string;

  score: number;
}

export interface GameState {
  id: number;

  creator: Player;
  players: Player[];

  winner: Player | null;
  currentPlayer: Player | null;

  currentCard: Card;
  cardGroup: CardGroup;

  shareId: string;
}
