import React, { Component } from 'react';
import MyRoutes from '../routes';

import Header from './common/header';

export default class App extends Component {
  render() {
    return (
      <div>
        <Header />
        <div className="container-fluid">
          <MyRoutes />
        </div>
      </div>
    );
  }
}