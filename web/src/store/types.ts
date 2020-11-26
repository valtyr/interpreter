export interface User {
  id: number;
  name: string;
  email: string;
}

export interface Card {
  id: number;
  word: string;
  creatorId?: string | null;
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
  published?: boolean;
}

export interface Player {
  id: number;

  name: string;
  connectionId: string;

  score: number;
}

export interface GameState {
  id: number;

  gameOver: boolean;
  gameStartedTime: string;
  turnStartedTime: string;
  turnInProgress: boolean;

  currentRound: number | null;

  creator: Player;
  playerList: Player[];

  winner: Player | null;
  currentPlayer: Player | null;
  currentGuesser: Player | null;

  currentCard: Card;
  cardGroup: CardGroup;
  cardSequence: number[];

  shareId: string;
}
