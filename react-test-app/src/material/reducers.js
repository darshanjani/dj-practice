import { FETCH_USERS } from './actions'

function users(state = [], action) {
	switch (action.type) {
		case FETCH_USERS:
			return [
				action.users
			]
		default:
			return state
	}
}

export default function userReducers(state = {}, action) {
	return {
		fetchUsers: users(state.users, action)
	}
}
