import React, { Component } from 'react';
import { FormGroup, FormControl, ControlLabel, HelpBlock, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom'

function FieldGroup({ id, label, help, ...props }) {
  return (
    <FormGroup controlId={id}>
      <ControlLabel>{label}</ControlLabel>
      <FormControl {...props} />
      {help && <HelpBlock>{help}</HelpBlock>}
    </FormGroup>
  );
}

class AuthorForm extends Component {
    render() {
        return (
            <form>
                <FieldGroup
                id="firstName"
                type="text"
                label="First Name"
                defaultValue={this.props.author.firstName}
                onChange={this.props.onChange}
                placeholder="Enter First Name"
                />
                <FieldGroup
                id="lastName"
                type="text"
                label="Last Name"
                defaultValue={this.props.author.lastName}
                onChange={this.props.onChange}
                placeholder="Enter Surname"
                />
                
                <Button className="btn-primary" onClick={this.props.onSave}>Save</Button>
                <Button className="btn-danger" onClick={this.props.onCancel}>Cancel</Button>
            </form>
        );
    }
}

export default AuthorForm;