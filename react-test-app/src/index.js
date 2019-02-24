import React from 'react';
import ReactDOM from 'react-dom';
import { createStore } from 'redux'
import userReducers from './material/reducers'
import App from './material/App'
import { BrowserRouter as Router } from 'react-router-dom'
//import { addTodo, toggleTodo, setVisibilityFilter, VisibilityFilters } from './todo/redux/actions'

const store = createStore(userReducers)

// console.log('store initial state: ', store.getState())
// const unsubscribe = store.subscribe(() => console.log(store.getState()))
// store.dispatch(addTodo('Learn about actions'))
// store.dispatch(addTodo('Learn about reducers'))
// store.dispatch(addTodo('Learn about store'))
// store.dispatch(toggleTodo(0))
// store.dispatch(toggleTodo(1))
// store.dispatch(setVisibilityFilter(VisibilityFilters.SHOW_COMPLETED))
// unsubscribe()

ReactDOM.render(
  <Router>
    <App store={store} />
  </Router>,
  document.getElementById('root')
);
