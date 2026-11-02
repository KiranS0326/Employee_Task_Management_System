import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";

export default function Leaves() {
  const { user } = useAuth();
  const [leaves, setLeaves] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [form, setForm] = useState({ employeeId: "", leaveType: "CASUAL", startDate: "", endDate: "", reason: "" });

  const loadData = () => {
    api.get("/leaves").then((res) => setLeaves(res.data));
    api.get("/employees").then((res) => setEmployees(res.data));
  };
  useEffect(() => { loadData(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleApply = async (e) => {
    e.preventDefault();
    const payload = {
      employee: { id: parseInt(form.employeeId) },
      leaveType: form.leaveType,
      startDate: form.startDate,
      endDate: form.endDate,
      reason: form.reason,
    };
    await api.post("/leaves", payload);
    setForm({ employeeId: "", leaveType: "CASUAL", startDate: "", endDate: "", reason: "" });
    loadData();
  };

  const handleDecision = async (id, status) => {
    await api.put(`/leaves/${id}/status?status=${status}`);
    loadData();
  };

  const isAdmin = user?.role === "ROLE_ADMIN" || user?.role === "ROLE_MANAGER";

  return (
    <div>
      <h2>Leave Requests</h2>
      <form onSubmit={handleApply} className="form-row">
        <select name="employeeId" value={form.employeeId} onChange={handleChange} required>
          <option value="">-- Employee --</option>
          {employees.map((e) => <option key={e.id} value={e.id}>{e.fullName}</option>)}
        </select>
        <select name="leaveType" value={form.leaveType} onChange={handleChange}>
          <option value="SICK">Sick</option>
          <option value="CASUAL">Casual</option>
          <option value="VACATION">Vacation</option>
          <option value="EMERGENCY">Emergency</option>
        </select>
        <input name="startDate" type="date" value={form.startDate} onChange={handleChange} required />
        <input name="endDate" type="date" value={form.endDate} onChange={handleChange} required />
        <input name="reason" placeholder="Reason" value={form.reason} onChange={handleChange} />
        <button type="submit">Apply Leave</button>
      </form>

      <table>
        <thead><tr><th>Employee</th><th>Type</th><th>From</th><th>To</th><th>Reason</th><th>Status</th>{isAdmin && <th>Action</th>}</tr></thead>
        <tbody>
          {leaves.map((l) => (
            <tr key={l.id}>
              <td>{l.employee?.fullName}</td>
              <td>{l.leaveType}</td>
              <td>{l.startDate}</td>
              <td>{l.endDate}</td>
              <td>{l.reason}</td>
              <td><span className={`status-badge status-${l.status}`}>{l.status}</span></td>
              {isAdmin && l.status === "PENDING" && (
                <td>
                  <button onClick={() => handleDecision(l.id, "APPROVED")} style={{ background: "#10b981", marginRight: 6 }}>Approve</button>
                  <button onClick={() => handleDecision(l.id, "REJECTED")}>Reject</button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}