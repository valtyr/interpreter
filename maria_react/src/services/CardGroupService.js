import axios from 'axios'

const CARD_GROUP_REST_API_URL = 'http://localhost:8080/api/cardGroups';

class CardGroupService {
  getCardGroups() {
    return axios.get(CARD_GROUP_REST_API_URL);
  }
}

export default new CardGroupService();