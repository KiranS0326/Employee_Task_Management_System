import { useEffect, useState } from "react";
import { PieChart, Pie, Cell, BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from "recharts";
import api from "../api/axiosConfig";
import { useAuth } from "../context/AuthContext";

const COLORS = { PENDING: "#f59e0b", IN_PROGRESS: "#4f46e5", DONE: "#10b981" };

export default function Dashboard() {
  const { user } = useAuth();
  const [employees, setEmployees] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [leaves, setLeaves] = useState([]);

  useEffect(() => {
    api.get("/employees").then((res) => setEmployees(res.data));
    api.get("/tasks").then((res) => setTasks(res.data));
    api.get("/leaves").then((res) => setLeaves(res.data)).catch(() => setLeaves([]));
  }, []);

  const taskStatusData = ["PENDING", "IN_PROGRESS", "DONE"].map((status) => ({
    name: status.replace("_", " "),
    value: tasks.filter((t) => t.status === status).length,
    fill: COLORS[status],
  }));

  const deptWorkload = {};
  employees.forEach((e) => {
    const dept = e.department?.name || "Unassigned";
    deptWorkload[dept] = (deptWorkload[dept] || 0) + tasks.filter((t) => t.employee?.id === e.id).length;
  });
  const deptData = Object.entries(deptWorkload).map(([name, tasks]) => ({ name, tasks }));

  const pendingLeaves = leaves.filter((l) => l.status === "PENDING").length;

  return (
    <div>
      <h2>Welcome back, {user?.username}</h2>
      <div className="dashboard-cards">
        <div className="card"><h3>Total Employees</h3><p>{employees.length}</p></div>
        <div className="card"><h3>Pending Tasks</h3><p>{tasks.filter(t => t.status === "PENDING").length}</p></div>
        <div className="card"><h3>Completed Tasks</h3><p>{tasks.filter(t => t.status === "DONE").length}</p></div>
        <div className="card"><h3>Pending Leaves</h3><p>{pendingLeaves}</p></div>
      </div>

      <div className="chart-grid">
        <div className="chart-card">
          <h4>Task Status Distribution</h4>
          <ResponsiveContainer width="100%" height={260}>
            <PieChart>
              <Pie data={taskStatusData} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={90} label>
                {taskStatusData.map((entry, i) => <Cell key={i} fill={entry.fill} />)}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>

        <div className="chart-card">
          <h4>Workload by Department</h4>
          <ResponsiveContainer width="100%" height={260}>
            <BarChart data={deptData}>
              <XAxis dataKey="name" fontSize={12} />
              <YAxis allowDecimals={false} fontSize={12} />
              <Tooltip />
              <Bar dataKey="tasks" fill="#4f46e5" radius={[6, 6, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}