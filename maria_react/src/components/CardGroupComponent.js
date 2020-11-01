import React from 'react';
import CardGroupService from '../services/CardGroupService';

class CardGroupComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cardGroups: []
    }
  }

  compenntDidMount() { 
    CardGroupService.getCardGroups().then((response) => {
      this.setState({cardGroups: response.data})
    });
  }

  render () {
    return (
      <div>
        <h1>Card Groups</h1>
        <table>
          <thead>
          </thead>
          <tbody>
            {
              this.state.cardGroups.map(
                cardGroup =>
                <td> {cardGroup.id}</td>
              )
            }
          </tbody>
        </table>
      </div>
    )
  }
}

export default CardGroupComponent