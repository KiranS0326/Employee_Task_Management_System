import { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";

export default function Tasks() {
  const { user } = useAuth();

  const [tasks, setTasks] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [form, setForm] = useState({ title: "", description: "", dueDate: "", employeeId: "" });

  const [activeTaskId, setActiveTaskId] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");

  const loadData = () => {
    api.get("/tasks").then((res) => setTasks(res.data));
    api.get("/employees").then((res) => setEmployees(res.data));
  };

  useEffect(() => { loadData(); }, []);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleAdd = async (e) => {
    e.preventDefault();
    const payload = {
      title: form.title,
      description: form.description,
      dueDate: form.dueDate,
      employee: form.employeeId ? { id: parseInt(form.employeeId) } : null,
    };
    await api.post("/tasks", payload);
    setForm({ title: "", description: "", dueDate: "", employeeId: "" });
    loadData();
  };

  const handleStatusChange = async (id, status) => {
    await api.put(`/tasks/${id}/status?status=${status}`);
    loadData();
  };

  const handleDelete = async (id) => {
    if (window.confirm("Delete this task?")) {
      await api.delete(`/tasks/${id}`);
      loadData();
    }
  };

  const openComments = async (taskId) => {
    setActiveTaskId(taskId);
    const res = await api.get(`/comments/task/${taskId}`);
    setComments(res.data);
  };

  const submitComment = async () => {
    if (!newComment.trim()) return;
    await api.post("/comments", {
      task: { id: activeTaskId },
      author: { id: user.id },
      content: newComment,
    });
    setNewComment("");
    const res = await api.get(`/comments/task/${activeTaskId}`);
    setComments(res.data);
  };

  return (
    <div>
      <h2>Tasks</h2>
      <form onSubmit={handleAdd} className="form-row">
        <input name="title" placeholder="Task Title" value={form.title} onChange={handleChange} required />
        <input name="description" placeholder="Description" value={form.description} onChange={handleChange} />
        <input name="dueDate" type="date" value={form.dueDate} onChange={handleChange} />
        <select name="employeeId" value={form.employeeId} onChange={handleChange}>
          <option value="">-- Assign to --</option>
          {employees.map((emp) => <option key={emp.id} value={emp.id}>{emp.fullName}</option>)}
        </select>
        <button type="submit">Add Task</button>
      </form>

      <table>
        <thead>
          <tr><th>Title</th><th>Description</th><th>Due Date</th><th>Employee</th><th>Status</th><th>Action</th></tr>
        </thead>
        <tbody>
          {tasks.map((t) => (
            <tr key={t.id}>
              <td>{t.title}</td>
              <td>{t.description}</td>
              <td>{t.dueDate}</td>
              <td>{t.employee?.fullName || "-"}</td>
              <td>
                <select value={t.status} onChange={(e) => handleStatusChange(t.id, e.target.value)}>
                  <option value="PENDING">Pending</option>
                  <option value="IN_PROGRESS">In Progress</option>
                  <option value="DONE">Done</option>
                </select>
              </td>
              <td>
                <button onClick={() => openComments(t.id)} style={{ background: "#4f46e5", marginRight: 6 }}>Comments</button>
                <button onClick={() => handleDelete(t.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {activeTaskId && (
        <div className="chart-card" style={{ marginTop: 20 }}>
          <h4>Activity Timeline — Task #{activeTaskId}</h4>
          <div style={{ maxHeight: 200, overflowY: "auto", marginBottom: 12 }}>
            {comments.map((c) => (
              <div key={c.id} style={{ padding: "8px 0", borderBottom: "1px solid #e2e8f0" }}>
                <b>{c.author?.username || "User"}</b>{" "}
                <span style={{ color: "#64748b", fontSize: "0.8em" }}>{new Date(c.createdAt).toLocaleString()}</span>
                <p style={{ margin: "4px 0 0" }}>{c.content}</p>
              </div>
            ))}
            {comments.length === 0 && <p style={{ color: "#64748b" }}>No comments yet.</p>}
          </div>
          <div style={{ display: "flex", gap: 8 }}>
            <input placeholder="Add a comment..." value={newComment} onChange={(e) => setNewComment(e.target.value)} style={{ flex: 1, margin: 0 }} />
            <button onClick={submitComment}>Post</button>
          </div>
        </div>
      )}
    </div>
  );
}