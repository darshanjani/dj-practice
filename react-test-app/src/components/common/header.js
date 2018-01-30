import React, { Component } from 'react';
import { Navbar, Nav, NavItem, NavDropdown, MenuItem }  from 'react-bootstrap';
import { IndexLinkContainer, LinkContainer } from 'react-router-bootstrap';
import logo from '../../images/pluralsight-logo.jpg';

class Header extends Component {
    render() {
        return (
            // <Navbar inverse>
            //     <Navbar.Header>
            //         <Navbar.Brand>
            //             <a href="/#"><img width="180" height="30" src={logo} alt="Pluralsight"/></a>
            //         </Navbar.Brand>
            //         <Navbar.Toggle />
            //     </Navbar.Header>
            //     {/* <Nav>
            //         <NavItem href="#">
            //             <span></span>
            //         </NavItem>
            //         <NavItem href="#">
            //             <span><Link to="/authors">Authors</Link></span>
            //         </NavItem>
            //         <NavItem href="#">
            //             <span><Link to="/about">About</Link></span>
            //         </NavItem>
            //     </Nav> */}
            //     <div className="navbar-nav">
            //         <Link to="/" className="nav-item nav-link active">Home</Link>
            //         <Link to="/authors" className="nav-item nav-link">Authors</Link>

            //         {/* <ul className="navbar-nav mr-auto">
            //             <li className="nav-item active">
            //                 <Link to="/" className="nav-link">Home</Link>
            //             </li>
            //             <li className="nav-item">
            //                 <Link to="/authors" className="nav-link">Authors</Link>
            //             </li>
            //             <li className="nav-item">
            //                 <Link to="/about" className="nav-link">About</Link>
            //             </li>
            //         </ul> */}
            //     </div>
            // </Navbar>
            // <nav class="navbar navbar-expand-lg navbar-light bg-light">
            //     <a className="navbar-brand">Navbar</a>
            //     <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            //     <span class="navbar-toggler-icon"></span>
            //     </button>
            //     <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            //     <div class="navbar-nav">
            //         <a class="nav-item nav-link active">Home <span class="sr-only">(current)</span></a>
            //         <a class="nav-item nav-link">Features</a>
            //         <a class="nav-item nav-link">Pricing</a>
            //         <a class="nav-item nav-link disabled">Disabled</a>
            //     </div>
            //     </div>
            // </nav>
            <Navbar inverse collapseOnSelect>
                <Navbar.Header>
                    <IndexLinkContainer to="/">
                        <Navbar.Brand>React-Bootstrap</Navbar.Brand>
                    </IndexLinkContainer>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <IndexLinkContainer to="/">
                            <NavItem eventKey={1}>Home</NavItem>
                        </IndexLinkContainer>
                        <LinkContainer to="/authors">
                            <NavItem eventKey={2}>Authors</NavItem>
                        </LinkContainer>
                        <LinkContainer to="/about">
                            <NavItem eventKey={3}>About</NavItem>
                        </LinkContainer>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default Header;