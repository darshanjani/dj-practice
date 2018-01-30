import AuthorData from './authorData';
import _ from 'lodash';

let authors = AuthorData.authors;

function _generateId(author) {
	return author.firstName.toLowerCase() + '-' + author.lastName.toLowerCase();
};

function _clone(item) {
	return JSON.parse(JSON.stringify(item)); //return cloned copy so that the item is passed by value instead of by reference
};

class AuthorApi {
	static getAllAuthors() {
		return _clone(authors); 
	}

	static getAuthorById(id) {
		var author = _.find(authors, {id: id});
		return _clone(author);
	}
	
	static saveAuthor(author) {
		//pretend an ajax call to web api is made here
		console.log('Pretend this just saved the author to the DB via AJAX call...');
		
		if (author.id) {
			var existingAuthorIndex = _.indexOf(authors, _.find(authors, {id: author.id})); 
			authors.splice(existingAuthorIndex, 1, author);
		} else {
			//Just simulating creation here.
			//The server would generate ids for new authors in a real app.
			//Cloning so copy returned is passed by value rather than by reference.
			author.id = _generateId(author);
			authors.push(author);
		}

		return _clone(author);
	}

	static deleteAuthor(id) {
		console.log('Pretend this just deleted the author from the DB via an AJAX call...');
		_.remove(authors, { id: id});
	}
}

export default AuthorApi;