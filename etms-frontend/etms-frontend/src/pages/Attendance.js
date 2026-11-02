import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function Attendance() {
  const [records, setRecords] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [selectedEmp, setSelectedEmp] = useState("");

  const loadData = () => {
    api.get("/attendance").then((res) => setRecords(res.data));
    api.get("/employees").then((res) => setEmployees(res.data));
  };
  useEffect(() => { loadData(); }, []);

  const handleCheckIn = async () => {
    if (!selectedEmp) return alert("Select an employee first");
    await api.post(`/attendance/checkin/${selectedEmp}`);
    loadData();
  };

  const handleCheckOut = async (attendanceId) => {
    await api.put(`/attendance/checkout/${attendanceId}`);
    loadData();
  };

  return (
    <div>
      <div className="section-header"><h2>Attendance</h2></div>
      <div className="form-row">
        <select value={selectedEmp} onChange={(e) => setSelectedEmp(e.target.value)}>
          <option value="">-- Select Employee --</option>
          {employees.map((e) => <option key={e.id} value={e.id}>{e.fullName}</option>)}
        </select>
        <button className="checkin-btn" onClick={handleCheckIn}>Check In</button>
      </div>

      <table>
        <thead><tr><th>Employee</th><th>Date</th><th>Check In</th><th>Check Out</th><th>Status</th><th>Action</th></tr></thead>
        <tbody>
          {records.map((r) => (
            <tr key={r.id}>
              <td>{r.employee?.fullName}</td>
              <td>{r.date}</td>
              <td>{r.checkIn || "-"}</td>
              <td>{r.checkOut || "-"}</td>
              <td><span className={`status-badge status-${r.status}`}>{r.status}</span></td>
              <td>{!r.checkOut && <button onClick={() => handleCheckOut(r.id)}>Check Out</button>}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}