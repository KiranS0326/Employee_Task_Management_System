// TODO: login form, calls POST /auth/login, stores JWT via AuthContext
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/login", form);
      login(res.data);
      navigate("/dashboard");
    } catch (err) {
      setError("Invalid username or password");
    }
  };

  return (
    <div className="auth-container">
      <h2>ETMS Login</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" value={form.username} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" value={form.password} onChange={handleChange} required />
        <button type="submit">Login</button>
      </form>
      <p>Don't have an account? <Link to="/register">Register</Link></p>
    </div>
  );
}