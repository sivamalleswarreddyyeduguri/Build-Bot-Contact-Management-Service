import axios from 'axios';

class ContactService {

  static fetchContacts()
  {
    const BASE_URL = "http://localhost:8080/app/contacts-management/api/v1/fetch-all-contacts";
try{
  let response = axios.get(BASE_URL)
  return response

}
catch(error)
{
  throw error
}
    


  }

    static  addContact(contact) {
        const BASE_URL = "http://localhost:8080/app/contacts-management/api/v1/create-contact";
    
        try {
          const response =  axios.post(BASE_URL, contact);
          return response
        } catch (error) {
          throw error; 
        }
      }

      static deleteContact(contactId)
      {
        const BASE_URL = `http://localhost:9091/app/contacts-management/api/v1/delete-contact?contactId=${contactId}`;
        
      try {
          const response = axios.delete(BASE_URL);
          return response;
      } catch (error) {
    
          throw error; 
      }
      }

      static getContact(contactId)
      {
        
      }
      static updateContact(contact)
      {
        
      }
}

export default ContactService;
