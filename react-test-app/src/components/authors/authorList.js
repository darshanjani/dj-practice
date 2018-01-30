import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom'

export default class AuthorList extends Component {
    render() {
        function createAuthorRow(author) {
            return (
                <tr key={author.id}>
                    <td><Link to={"/authors/" + author.id}>{author.id}</Link></td>
                    <td>{author.firstName} {author.lastName}</td>
                </tr>
            )
        };

        return (
            <div>
                <Link to="/addAuthor" className="btn btn-primary">Add Author</Link>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.authors.map(createAuthorRow, this)}
                    </tbody>
                </table>
            </div>

        );
    }
};

AuthorList.propTypes = {
    authors: PropTypes.array.isRequired
}