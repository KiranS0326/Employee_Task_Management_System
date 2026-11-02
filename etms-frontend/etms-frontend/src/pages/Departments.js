// TODO: list/add/edit/delete departments via /api/departments
import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

export default function Departments() {
  const [departments, setDepartments] = useState([]);
  const [name, setName] = useState("");

  const loadData = () => {
    api.get("/departments").then((res) => setDepartments(res.data));
  };

  useEffect(() => { loadData(); }, []);

  const handleAdd = async (e) => {
    e.preventDefault();
    await api.post("/departments", { name });
    setName("");
    loadData();
  };

  const handleDelete = async (id) => {
    if (window.confirm("Delete this department?")) {
      await api.delete(`/departments/${id}`);
      loadData();
    }
  };

  return (
    <div>
      <h2>Departments</h2>
      <form onSubmit={handleAdd} className="form-row">
        <input placeholder="Department Name" value={name} onChange={(e) => setName(e.target.value)} required />
        <button type="submit">Add Department</button>
      </form>

      <table>
        <thead><tr><th>ID</th><th>Name</th><th>Action</th></tr></thead>
        <tbody>
          {departments.map((d) => (
            <tr key={d.id}>
              <td>{d.id}</td>
              <td>{d.name}</td>
              <td><button onClick={() => handleDelete(d.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}