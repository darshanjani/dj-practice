import React, { Component } from 'react';

class About extends Component {
    render() {
        return (
            <div>
                <h1>About</h1>
                <p>
                    This app uses the following tech
                </p>
                <ul>
                    <li>React</li>
                    <li>React-router</li>
                    <li>React-dom</li>
                    <li>Bootstrap</li>
                    <li>Flux</li>
                </ul>
            </div>
        )
    }
}

export default About;