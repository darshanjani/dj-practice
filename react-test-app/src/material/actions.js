export const FETCH_USERS = "FETCH_USERS"

export function fetchUsers(users) {
	return {
		type: FETCH_USERS,
		users
	}
}