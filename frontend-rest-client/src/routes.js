// src/routes.js
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Books from './pages/book';
import NewBook from './pages/NewBook';

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<Login />} />
    <Route path="/books" element={<Books />} />
    <Route path="/book/new/:bookId" element={<NewBook />} />
  </Routes>
);

export default AppRoutes;