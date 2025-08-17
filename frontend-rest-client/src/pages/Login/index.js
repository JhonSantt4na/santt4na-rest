import React, { useState } from 'react';
import './style.css';
import logo1 from '../../assets/padlock.png'; 
import logo from '../../assets/logo.svg';

const Login = () => {
  const [mode, setMode] = useState('login');
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form submitted:', formData);
  };

  return (
    <div className="container">
      <img src={logo1} alt="Cadastre-se" className="logo" />
      <img src={logo} alt="Cadastre-se" className="logo1" />
      <h1>{mode === 'login' ? 'Login' : 'Cadastro'}</h1>
      <form onSubmit={handleSubmit}>
        {mode === 'signup' && (
          <input
            type="text"
            name="name"
            placeholder="Nome"
            value={formData.name}
            onChange={handleChange}
            required
          />
        )}
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Senha"
          value={formData.password}
          onChange={handleChange}
          required
        />
        {mode === 'signup' && (
          <input
            type="password"
            name="confirmPassword"
            placeholder="Confirme a Senha"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
        )}
        <button type="submit">{mode === 'login' ? 'Entrar' : 'Cadastrar'}</button>
      </form>
      <p className="switch" onClick={() => setMode(mode === 'login' ? 'signup' : 'login')}>
        {mode === 'login' ? 'Não tem conta? Cadastre-se' : 'Já tem conta? Faça login'}
      </p>
    </div>
  );
};

export default Login;