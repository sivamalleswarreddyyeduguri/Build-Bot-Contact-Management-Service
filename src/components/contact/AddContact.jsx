import React, { useEffect, useRef, useState } from 'react';
import ContactService from '../services/ContactService';
import { toast } from 'react-toastify';
import { useNavigate, useParams } from 'react-router-dom';

export default function AddContact() {
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNo: ''
  });

  const { cid } = useParams();
  const navigate = useNavigate();


  const firstNameRef = useRef();
  const lastNameRef = useRef();
  const emailRef = useRef();
  const phoneNoRef = useRef();


  useEffect(() => {
    if (cid) {
      const fetchContactById = async () => {
        try {
          let response = await ContactService.getContact(cid);
          let data = response.data;
          setForm({
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            phoneNo: data.phoneNo
          });
        } catch (error) {
          console.error('Error fetching contact:', error);
        }
      };
      fetchContactById();
    }
  }, [cid]);

  const onSubmit = async (e) => {
    e.preventDefault();

    const newContact = {
      firstName: firstNameRef.current.value,
      lastName: lastNameRef.current.value,
      email: emailRef.current.value,
      phoneNo: phoneNoRef.current.value
    };

    try {
      if (cid) {
       
        await ContactService.updateContact(cid, newContact);
        toast.success('Contact updated successfully');
      } else {
       
        await ContactService.addContact(newContact);
        toast.success('Contact created successfully');
      }
      navigate('/all-contacts');
    } catch (error) {
      console.error('Error saving contact:', error);
      toast.error(error.response?.data?.errorMessage || 'Error saving contact');
    }
  };

  return (
    <div className="container mt-5 mx-5">
      <div className="d-flex justify-content-start mb-3">
        <button className="btn btn-secondary" onClick={() =>cid?navigate('/all-contacts'):navigate("/")}>
          Back
        </button>
      </div>
      <h1 className="text-center mb-4">{cid ? 'Edit Contact' : 'Add New Contact'}</h1>
      <form onSubmit={onSubmit} className="card p-5 shadow-lg mx-5 mt-5">
        <div className="row mb-3 mx-5">
          <label htmlFor="firstName" className="col-sm-3 col-form-label">First Name</label>
          <div className="col-sm-9">
            <input
              type="text"
              id="firstName"
              ref={firstNameRef}
              className="form-control"
              pattern="[a-zA-Z]+"
              required
              placeholder="Enter first name"
              defaultValue={form.firstName}
            />
          </div>
        </div>
        <div className="row mb-3 mx-5">
          <label htmlFor="lastName" className="col-sm-3 col-form-label">Last Name</label>
          <div className="col-sm-9">
            <input
              type="text"
              id="lastName"
              ref={lastNameRef}
              className="form-control"
              pattern="[a-zA-Z]+"
              required
              placeholder="Enter last name"
              defaultValue={form.lastName}
            />
          </div>
        </div>
        <div className="row mb-3 mx-5">
          <label htmlFor="email" className="col-sm-3 col-form-label">Email</label>
          <div className="col-sm-9">
            <input
              type="email"
              id="email"
              ref={emailRef}
              className="form-control"
              pattern="^[A-Za-z0-9+_.-]+@(.+)$"
              required
              placeholder="Enter email"
              defaultValue={form.email}
            />
          </div>
        </div>
        <div className="row mb-3 mx-5">
          <label htmlFor="phoneNo" className="col-sm-3 col-form-label">Phone Number</label>
          <div className="col-sm-9">
            <input
              type="text"
              id="phoneNo"
              ref={phoneNoRef}
              className="form-control"
              pattern="(^\+91[6-9]\d{9}$)|(^[6-9]\d{9}$)|(^\+[0-9]{1,2}[0-9]{10,}$)"
              required
              placeholder="Enter phone number"
              defaultValue={form.phoneNo}
            />
          </div>
        </div>
        <div className="d-flex justify-content-center">
          <button type="submit" className="btn btn-primary">{cid ? 'Update Contact' : 'Add Contact'}</button>
        </div>
      </form>
    </div>
  );
}
