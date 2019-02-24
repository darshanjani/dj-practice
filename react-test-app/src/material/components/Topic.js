import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import { AgGridReact } from 'ag-grid-react';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-balham.css';
import { connect } from 'react-redux'

const styles = theme => ({
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
  button: {
    margin: theme.spacing.unit,
  },
});

class Topic extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      env: 'PROD',
      columnDefs: [{
        headerName: "ID", field: "id", sortable: true
      }, {
        headerName: "Name", field: "name", sortable: true, filter: true
      }, {
        headerName: "Username", field: "username", sortable: true, filter: true
      }, {
        headerName: "Email", field: "email", sortable: false, filter: true
      }],
      rowData: []
    }
    this.fetchData = this.fetchData.bind(this)
  }

  fetchData() {
    console.log('Fetching 10 users from', 'https://jsonplaceholder.typicode.com/users')
    fetch('https://jsonplaceholder.typicode.com/users')
      .then(response => response.json())
      .then(json => this.setState({rowData: json}))
  }

  render() {
    const { classes } = this.props
    return (
      <div>
        <div>
          <form className={classes.container} noValidate>
            <TextField
              id="date"
              label="Select Date"
              type="date"
              defaultValue="2017-05-24"
              className={classes.textField}
              InputLabelProps={{
                shrink: true,
              }}
            />
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
            <Button variant="contained" color="primary" className={classes.button} onClick={this.fetchData}>
              Refresh
            </Button>
          </form>
        </div>
        <hr />
        <div
          className="ag-theme-balham"
          style={{
            height: '400px',
            width: '100%'
          }}>
          <AgGridReact
            columnDefs={this.state.columnDefs}
            rowData={this.state.rowData}>
          </AgGridReact>
        </div>
        <hr />
        <p>Grid copied twice to check scrolling behavior</p>
        <div
          className="ag-theme-balham"
          style={{
            height: '400px',
            width: '100%'
          }}>
          <AgGridReact
            columnDefs={this.state.columnDefs}
            rowData={this.state.rowData}>
          </AgGridReact>
        </div>
      </div>
    )
  }
}

Topic = connect()(Topic)

export default withStyles(styles)(Topic);