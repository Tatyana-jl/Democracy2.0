// Author: Santiago G. Princ
// React Frontend for the Junction 2019 Stara/Microsoft/Aivon challenges.

import React, { Component } from 'react'

export default class UpForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      location: "",
      showing: false,
      previewURL: "",
      picture: "",
      address: "",
      type: "",
      name: ""
    };

    this.handleImageChange = this.handleImageChange.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
    this.handleDropdownChange = this.handleDropdownChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  static getDerivedStateFromProps(nextProps, prevState){
    if(nextProps.location !== prevState.location){
      return {
        location: nextProps.location
      }
    }
    return null;
  }

  handleTextChange(e){
    //e.preventDefault();
    this.setState({[e.target.name]: e.target.value});
  }

  handleDropdownChange(e){
    this.setState({type: e.target.value})
  }

  handleImageChange(e){
    e.preventDefault();
    let file = e.target.files[0];
    this.setState({picture: file});

  }

  handleSubmit(e){
    e.preventDefault();
    let data = {
      "location": this.state.location,
      "name": this.state.name,
      "type":this.state.type,
      "picture": this.state.picture,
    }
    this.props.submitRequest(data);
  }

  render() {
    const containerStyle = {"display": this.props.showForm === true ? "block" : "none"};

    return (
      <div id = "uploadForm" style = {containerStyle}>
        <form onSubmit={this.handleSubmit}>
          <span>
            <label>Location: </label>
            <input type="text" name="location" value={this.state.location.lat + ", "+ this.state.location.lon} onChange={this.handleTextChange}/>
          </span>
          <span>
            <label>Name: </label>
            <input type="text" name="name" defaultValue="" onChange={this.handleTextChange}/>
          </span>
          <span>
            <label>Type of Issue: </label>
            <select name="cars" onChange={this.handleDropdownChange}>
              <option value="wall">Walls</option>
              <option value="pipe">Pipes</option>
              <option value="paint">Paint</option>
              <option value="street">Street</option>
            </select>
          </span>
          <span>
            <label>Picture: </label>
            <input type="file" onChange={this.handleImageChange}/>
          </span>
          <input type='submit'/>
          <button onClick={this.props.closeForm}>Close</button>
        </form>
      </div>
    )
  }
}
