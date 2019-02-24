import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { BrowserRouter as Router, Route, NavLink, Redirect } from 'react-router-dom'
import Batch from './about/Batch'
import Other from './about/Other'
import Stats from './about/Stats'
import Timings from './about/Timings'

const styles = theme => ({
    root: {
        backgroundColor: theme.palette.background.paper,
        width: '100%',
    },
});

class About extends React.Component {
    state = {
        value: 0,
    };

    handleChange = (event, value) => {
        this.setState({ value });
    };

    render () {
        const { classes, match } = this.props
        const { value } = this.state
        return (
            <Router>
            <div className={classes.root}>
                <AppBar position="static">
                    <Tabs 
                    value={value} 
                    onChange={this.handleChange}
                    centered>
                        <Tab label="Batch" component={NavLink} to={`${match.url}/batch`} />
                        <Tab label="Other" component={NavLink} to={`${match.url}/other`} />
                        <Tab label="Timings" component={NavLink} to={`${match.url}/timings`} />
                        <Tab label="Stats" component={NavLink} to={`${match.url}/stats`} />
                    </Tabs>
                </AppBar>
                <Route exact path={`${match.path}`} render={() => <Redirect replace to={`${match.path}/batch`} />} />
                <Route path={`${match.path}/batch`} component={Batch}/>
                <Route path={`${match.path}/other`} component={Other}/>
                <Route path={`${match.path}/timings`} component={Timings}/>
                <Route path={`${match.path}/stats`} component={Stats}/>
            </div>
            </Router>
        );
    };
}

export default withStyles(styles)(About);