import React, { useEffect, useState } from 'react';
import ContactService from '../services/ContactService';
import './DisplayContacts.css'; 
import { toast } from 'react-toastify';
import { NavLink, useNavigate } from 'react-router-dom';

export default function DisplayContacts() {
  const [contacts, setContacts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const navigate =useNavigate()

  useEffect(() => {
    const fetchContacts = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const response = await ContactService.fetchContacts();
        setContacts(response.data);
      } catch (error) {
        console.error('Error fetching contacts:', error);
        setError(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchContacts();
  }, []);


  function handleEditClick(contactId) {
    navigate(`/all-contacts/edit/${contactId}`);
  }
  

  async function handleDeleteClick(contactId)
 {
   try{
    let response = await ContactService.deleteContact(contactId)
    setContacts((prev)=>prev.filter((contact)=>contact.contactId!=contactId))
    toast.success("Deleted Successfully")
   }catch(error)
   {

   }
  }
  function goBack()
  {
    navigate("/")
  }

  return (
    <div className="container shadow-lg mt-5">
         <div className="d-flex justify-content-start mb-3">
    <button className="btn btn-secondary" onClick={() => goBack()}>
      Back
    </button>
  </div>
      <h1>Contacts</h1>
      {isLoading ? (
        <p className="text-center">Loading contacts...</p>
      ) : (
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">First Name</th>
              <th scope="col">Last Name</th>
              <th scope="col">Email</th>
              <th scope="col">Phone Number</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            {contacts.map((contact) => (
              <tr key={contact.contactId}>
                <td>{contact.firstName}</td>
                <td>{contact.lastName}</td>
                <td>{contact.email}</td>
                <td>{contact.phoneNo}</td>
                <td>
                  <button type="button" className="btn btn-sm btn-primary me-2" onClick={() => handleEditClick(contact.contactId)}>
                    <i className="bi bi-pencil-square"></i>
                    
                  </button>
                  <button type="button" className="btn btn-sm btn-danger" onClick={() => handleDeleteClick(contact.contactId)}>
                    <i className="bi bi-trash"></i>
                   
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}