import React, { Component } from 'react';
import { Link } from 'react-router-dom'

export default class NotFound extends Component {
    render() {
        return (
            <div>
                <h1>Page Not Found</h1>
                <p>Sorry, the page you requested cannot be found</p>
                <p><Link to="/" className="btn btn-primary btn-lg">Back to Home</Link></p>
            </div>
        )
    }
}