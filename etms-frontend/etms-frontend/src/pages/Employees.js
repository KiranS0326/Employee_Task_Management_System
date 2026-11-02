import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

const PAGE_SIZE = 5;

export default function Employees() {
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [form, setForm] = useState({ fullName: "", email: "", phone: "", designation: "", departmentId: "" });
  const [search, setSearch] = useState("");
  const [filterDept, setFilterDept] = useState("");
  const [page, setPage] = useState(1);
  const [photoFile, setPhotoFile] = useState(null);

  const loadData = () => {
    api.get("/employees").then((res) => setEmployees(res.data));
    api.get("/departments").then((res) => setDepartments(res.data));
  };
  useEffect(() => { loadData(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleAdd = async (e) => {
    e.preventDefault();
    let photoUrl = null;
    if (photoFile) {
      const formData = new FormData();
      formData.append("file", photoFile);
      const uploadRes = await api.post("/files/upload", formData, { headers: { "Content-Type": "multipart/form-data" } });
      photoUrl = uploadRes.data;
    }
    const payload = {
      fullName: form.fullName,
      email: form.email,
      phone: form.phone,
      designation: form.designation,
      department: form.departmentId ? { id: parseInt(form.departmentId) } : null,
      profilePhotoUrl: photoUrl,
    };
    await api.post("/employees", payload);
    setForm({ fullName: "", email: "", phone: "", designation: "", departmentId: "" });
    setPhotoFile(null);
    loadData();
  };

  const handleDelete = async (id) => {
    if (window.confirm("Delete this employee?")) {
      await api.delete(`/employees/${id}`);
      loadData();
    }
  };

  const filtered = employees.filter((e) => {
    const matchesSearch = e.fullName.toLowerCase().includes(search.toLowerCase()) ||
                           e.email.toLowerCase().includes(search.toLowerCase());
    const matchesDept = filterDept ? e.department?.id === parseInt(filterDept) : true;
    return matchesSearch && matchesDept;
  });

  const totalPages = Math.max(1, Math.ceil(filtered.length / PAGE_SIZE));
  const paginated = filtered.slice((page - 1) * PAGE_SIZE, page * PAGE_SIZE);

  return (
    <div>
      <h2>Employees</h2>

      <form onSubmit={handleAdd}>
        <div className="form-row">
          <input name="fullName" placeholder="Full Name" value={form.fullName} onChange={handleChange} required />
          <input name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
          <input name="phone" placeholder="Phone" value={form.phone} onChange={handleChange} />
          <input name="designation" placeholder="Designation" value={form.designation} onChange={handleChange} />
          <select name="departmentId" value={form.departmentId} onChange={handleChange}>
            <option value="">-- Department --</option>
            {departments.map((d) => <option key={d.id} value={d.id}>{d.name}</option>)}
          </select>
        </div>

        <div className="form-row" style={{ marginTop: -10 }}>
          <div style={{ display: "flex", flexDirection: "column", gap: 4 }}>
            <label style={{ fontSize: "0.78em", color: "#64748b", fontWeight: 600 }}>Profile Photo (optional)</label>
            <input
              type="file"
              accept="image/*"
              onChange={(e) => setPhotoFile(e.target.files[0])}
              style={{ width: 220, padding: "8px", fontSize: "0.85em" }}
            />
            {photoFile && <span style={{ fontSize: "0.75em", color: "#10b981" }}>✓ {photoFile.name}</span>}
          </div>
          <button type="submit">Add Employee</button>
        </div>
      </form>

      <div className="toolbar">
        <input placeholder="Search by name or email..." value={search}
               onChange={(e) => { setSearch(e.target.value); setPage(1); }} />
        <select value={filterDept} onChange={(e) => { setFilterDept(e.target.value); setPage(1); }}>
          <option value="">All Departments</option>
          {departments.map((d) => <option key={d.id} value={d.id}>{d.name}</option>)}
        </select>
      </div>

      <table>
        <thead><tr><th>Name</th><th>Email</th><th>Phone</th><th>Designation</th><th>Department</th><th>Action</th></tr></thead>
        <tbody>
          {paginated.map((emp) => (
            <tr key={emp.id}>
              <td>{emp.fullName}</td><td>{emp.email}</td><td>{emp.phone}</td>
              <td>{emp.designation}</td><td>{emp.department?.name || "-"}</td>
              <td><button onClick={() => handleDelete(emp.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="pagination">
        {Array.from({ length: totalPages }, (_, i) => i + 1).map((p) => (
          <button key={p} className={p === page ? "active" : ""} onClick={() => setPage(p)}>{p}</button>
        ))}
      </div>
    </div>
  );
}