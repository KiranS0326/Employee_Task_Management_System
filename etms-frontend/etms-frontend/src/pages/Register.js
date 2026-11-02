// TODO: register form, calls POST /auth/register
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "", email: "", roleName: "ROLE_EMPLOYEE" });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setMessage("");
    try {
      await api.post("/auth/register", form);
      setMessage("Registration successful! Redirecting to login...");
      setTimeout(() => navigate("/login"), 1500);
    } catch (err) {
      setError(err.response?.data?.message || "Registration failed. Username may already exist.");
    }
  };

  return (
    <div className="auth-container">
      <h2>Create Account</h2>
      {message && <p style={{ color: "green" }}>{message}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" value={form.username} onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" value={form.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" value={form.password} onChange={handleChange} required />
        <select name="roleName" value={form.roleName} onChange={handleChange}>
          <option value="ROLE_EMPLOYEE">Employee</option>
          <option value="ROLE_MANAGER">Manager</option>
          <option value="ROLE_ADMIN">Admin</option>
        </select>
        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <Link to="/login">Login</Link></p>
    </div>
  );
}