// Author: Santiago G. Princ
// React Frontend for the Junction 2019 Stara/Microsoft/Aivon challenges.

import React, { Component } from "react";
import LMap from "./components/map";
import UpForm from "./components/uploadform"

const endpoint = "http://10.100.0.37:8085";

export default class MapView extends Component {
  constructor(props) {
    super(props);

    // Set some state
    this.state = {
      showform: false,
      location: "",
      requests: []
    }

    this.showForm = this.showForm.bind(this);
    this.closeForm = this.closeForm.bind(this);
    this.setLocation = this.setLocation.bind(this);
    this.fetchPastRequests = this.fetchPastRequests.bind(this);
  }

  componentDidMount(){
    this.fetchPastRequests();
  }


  // Request to create a new request on the backend
  submitRequest(request){
    console.log(request);

    fetch(endpoint + "/recognize", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      },
      body: JSON.stringify(
        {
        address: request.location,
        name: request.name,
        type: request.type,
        picture: request.piture
      })

    }).then(response => {
      if (response.status === 200) {
        alert("Request created succesfully!");
      } else {
        alert("Something went wrong! Call Santiago!");
      }
    });
  }

  // Request to fetch all active open requests in the backend
  fetchPastRequests(){
    fetch(endpoint + "/problems", {
      method: "GET",
    })
    .then(response => response.json())
    .then(data => this.setState({ requests: data.requests }));
  }

  setLocation(location){
    this.setState({location: location});
    this.showForm();
  }

  showForm() {
    this.setState({ showForm: true });
  }

  closeForm() {
    this.setState({ showForm: false });
  }

  render () {
    return (
      <div>
        <LMap
          setLocation={this.setLocation}
        />
        <UpForm
          location = {this.state.location}
          showForm = {this.state.showForm}
          closeForm = {this.closeForm}
          submitRequest = {this.submitRequest}
        />
      </div>
    );
  }
}
