
import './App.css';
import AddContact from './components/contact/AddContact';
import DisplayContacts from './components/contact/DisplayContacts';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/contact/Home';


function App() {
  return (
    <div className="App">

<Router>
  <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/contact/add" element={<AddContact />} />
    <Route path="/all-contacts" element={<DisplayContacts />}>
      <Route path="edit/:cid" element={<AddContact />} />
    </Route>
  </Routes>
</Router>
    </div>
  );
}

export default App;
