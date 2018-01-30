import React, { Component } from 'react';
import AuthorForm from './authorForm';
import AuthorApi from '../../api/authorApi';
import {
	withRouter
} from 'react-router-dom';

class ManageAuthor extends Component {
    constructor(props) {
        super(props);
        let initialAuthor = { id: '', firstName: 'blank', lastName: 'blank' };
        let authorId = this.props.match.params.id;
        if (authorId) {
            try {
                initialAuthor = AuthorApi.getAuthorById(authorId);
            } catch(E) {
                alert("Author not found: " + authorId);
                initialAuthor = { id: '', firstName: 'blank', lastName: 'blank' };
            }
        }
        this.state = {
            author: initialAuthor
        };
        this.setAuthorState = this.setAuthorState.bind(this);
        this.saveAuthor = this.saveAuthor.bind(this);
        this.onCancel = this.onCancel.bind(this);
    }

    setAuthorState(event) {
        event.preventDefault();
        var field = event.target.id;
        var value = event.target.value;
        var prevAuthor = this.state.author;
        prevAuthor[field] = value;
        console.log(prevAuthor);
        this.setState({ author: prevAuthor });
    }

    saveAuthor(event) {
        event.preventDefault()
        console.log('form save called: ' + this.state.author.firstName);
        AuthorApi.saveAuthor(this.state.author);
        this.props.history.push('/authors');
        alert('Author saved successfully');
    }

    onCancel(event) {
        event.preventDefault();
        alert('Cancelled changed');
        this.props.history.push('/authors');
    }

    render() {
        return (
            <div>
                <h1>
                    Manage Author Page {this.props.match.params.id}
                </h1>
                <AuthorForm 
                    author={this.state.author}
                    onChange={this.setAuthorState}
                    onSave={this.saveAuthor}
                    onCancel={this.onCancel}
                />
            </div>
        );
    }
}

export default withRouter(ManageAuthor);