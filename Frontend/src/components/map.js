// Author: Santiago G. Princ
// React Frontend for the Junction 2019 Stara/Microsoft/Aivon challenges.

import React, { Component } from 'react'
import { Map, TileLayer, marker } from "react-leaflet";

import 'leaflet/dist/leaflet.css';

export default class LMap extends Component {
  constructor(props) {
    super(props);
    this.state = {
      lat: 60.167,
      lng: 24.944,
      zoom: 15,
    };

    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e){
    var location = {lat: e.latlng.lat, lon: e.latlng.lng}
    this.props.setLocation(location);
    console.log("[Debugging] Clicked: " + location.lat + " " + location.lon);
  }

  render() {
    const position = [this.state.lat, this.state.lng]
    const mapStyle = {"width": "100%", "height":"100%"}
    const containerStyle = {"width": "100%", "height": "100vh"}
    

    
//     L.marker([51.5, -0.09]).addTo(LMap)
// 		.bindPopup("<b>Hello world!</b><br />I am a popup.").openPopup();
        
        
    return (
      <div style={containerStyle}>
        <Map center={position} zoom={this.state.zoom}  style = {mapStyle} onClick={this.handleClick}>
          <TileLayer
            attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          
          // If you want to add the markers for the open requests, a an array of Marker components should happen here  

        </Map>
          
      </div>
      

    )
  }
}
