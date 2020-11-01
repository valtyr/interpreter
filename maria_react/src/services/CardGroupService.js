import axios from 'axios'

const CARD_GROUP_REST_API_URL = 'http://localhost:8080/api/cardGroups';

class CardGroupService {
  async getCardGroups() {
    const resp = await axios.get(CARD_GROUP_REST_API_URL);
    return resp.data;
  }
}

export default new CardGroupService();