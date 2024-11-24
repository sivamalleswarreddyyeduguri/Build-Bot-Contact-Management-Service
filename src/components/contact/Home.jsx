import React from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div className="container mt-5 text-center">
      <div className="jumbotron bg-light p-5 rounded shadow-sm">
        <h1 className="display-4 fw-bold text-danger">Build Bot Contact Management Service</h1>
        <p className="lead fs-4 text-muted">
          Manage your contacts with ease. Add new contacts.
        </p>
        <hr className="my-4" />
        <div className="mt-4 d-flex justify-content-center gap-5">
          <Link to="/contact/add" className="fs-4 text-decoration-none text-success hover-shadow">
            <div className="card p-3 rounded-3 shadow-sm" style={{ width: '220px' }}>
              <i className="bi bi-person-plus fs-1 mb-2"></i>
              <h5>Add Contact</h5>
            </div>
          </Link>
        </div>
      </div>
    </div>
  );
}
