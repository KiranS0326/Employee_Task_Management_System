// TODO: nav links to Dashboard / Employees / Tasks + Logout button
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function Navbar() {
  const { user, logout } = useAuth();
  const [unread, setUnread] = useState(0);
useEffect(() => {
  if (user?.id) {
    api.get(`/notifications/user/${user.id}/unread-count`).then((res) => setUnread(res.data));
  }
}, [user]);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <span className="navbar-brand">ETMS</span>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/employees">Employees</Link>
        <Link to="/departments">Departments</Link>
        <Link to="/tasks">Tasks</Link>
        <Link to="/attendance">Attendance</Link>
        <Link to="/leaves">Leaves</Link>
      </div>
      <div className="navbar-right">
        <span>{user?.username} ({user?.role?.replace("ROLE_", "")})</span>
        <button onClick={handleLogout}>Logout</button>
        <span style={{ position: "relative", marginRight: 8 }}>
  🔔 {unread > 0 && <span style={{ background: "#ef4444", color: "#fff", borderRadius: "50%", padding: "1px 6px", fontSize: "0.7em", position: "absolute", top: -8, right: -12 }}>{unread}</span>}
</span>
      </div>
    </nav>
  );
}