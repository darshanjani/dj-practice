import React, { Component } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom'
import Home from './components/homePage';
import AuthorPage from './components/authors/authorPage';
import ManageAuthor from './components/authors/manageAuthorPage';
import About from './components/about/aboutPage';
import NotFound from './components/404';

export default class MyRoutes extends Component {
    render() {
        return (
            <Switch>
                <Route exact path="/" component={Home} />
                <Redirect from="/about-us" to="/about"/>
                <Route path="/authors/:id" component={ManageAuthor} />
                <Route path="/authors" component={AuthorPage} />
                <Route path="/addAuthor" component={ManageAuthor} />
                <Route path="/about" component={About} />
                <Route component={NotFound} />
            </Switch>
        );
    }
};