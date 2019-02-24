import React from 'react';
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import MenuIcon from '@material-ui/icons/Menu';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import { withStyles } from '@material-ui/core/styles'
import {
	BrowserRouter as Router,
	Route,
	NavLink
} from 'react-router-dom'
import Home from './Home'
import About from './About'
import Topic from './Topic'
import { Provider } from 'react-redux'

const styles = theme => ({
	root: {
		width: '100%',
	},
	grow: {
		flexGrow: 1,
	},
	menuButton: {
		marginLeft: -12,
		marginRight: 20,
	},
	formControl: {
		margin: theme.spacing.unit,
		minWidth: 120,
	},
	title: {
		display: 'none',
		[theme.breakpoints.up('sm')]: {
			display: 'block',
		},
	},
	selectEmpty: {
		marginTop: theme.spacing.unit * 2,
	},
	button: {
		margin: theme.spacing.unit,
	},
});

class DashboardTopMenu extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			env: 'PROD'
		};
	};

	handleChange = event => {
		this.setState({ [event.target.name]: event.target.value });
	};

	render() {
		const { classes, store } = this.props
		return (
			<Provider store={store}>
				<Router>
					<div className={classes.root}>
						<AppBar position="static">
							<Toolbar>
								<IconButton className={classes.menuButton} color="inherit" aria-label="Open drawer">
									<MenuIcon />
								</IconButton>
								<Typography className={classes.title} variant="h6" color="inherit" noWrap>
									Welcome to Dashboard
													</Typography>
								<Button variant="contained" className={classes.button}>
									<NavLink to="/">Home</NavLink>
								</Button>
								<Button variant="contained" className={classes.button}>
									<NavLink to="/about">About</NavLink>
								</Button>
								<Button variant="contained" className={classes.button}>
									<NavLink to="/topic">Topic</NavLink>
								</Button>
								<div className={classes.grow} />
								<FormControl className={classes.formControl}>
									<InputLabel shrink htmlFor="age-label-placeholder">
										Env
															</InputLabel>
									<Select
										value={this.state.env}
										onChange={this.handleChange}
										input={<Input name="env" id="age-label-placeholder" />}
										displayEmpty
										name="env"
										className={classes.selectEmpty}
									>
										<MenuItem value={"PROD"}>PROD</MenuItem>
										<MenuItem value={"UAT"}>UAT</MenuItem>
										<MenuItem value={"QA"}>QA</MenuItem>
										<MenuItem value={"DEV"}>DEV</MenuItem>
									</Select>
								</FormControl>
								<Button variant="contained" className={classes.button}>
									Change Env
													</Button>
								<Button variant="contained" className={classes.button}>
									Logout
													</Button>
							</Toolbar>
						</AppBar>
						<Route exact path="/" component={Home} />
						<Route exact path="/about" component={About} />
						<Route exact path="/topic" component={Topic} />
					</div>
				</Router>
			</Provider>
		)
	}
}

export default withStyles(styles)(DashboardTopMenu)