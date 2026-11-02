import Attendance from "./pages/Attendance";
import Leaves from "./pages/Leaves";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "./context/AuthContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Employees from "./pages/Employees";
import Departments from "./pages/Departments";
import Tasks from "./pages/Tasks";
import Navbar from "./components/Navbar";

function PrivateRoute({ children }) {
  const { user } = useAuth();
  return user ? children : <Navigate to="/login" />;
}

function AppRoutes() {
  const { user } = useAuth();
  return (
    <BrowserRouter>
      {user && <Navbar />}
      <div className="page-content">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/employees" element={<PrivateRoute><Employees /></PrivateRoute>} />
          <Route path="/departments" element={<PrivateRoute><Departments /></PrivateRoute>} />
          <Route path="/tasks" element={<PrivateRoute><Tasks /></PrivateRoute>} />
          <Route path="*" element={<Navigate to="/login" />} />
          <Route path="/attendance" element={<PrivateRoute><Attendance /></PrivateRoute>} />
          <Route path="/leaves" element={<PrivateRoute><Leaves /></PrivateRoute>} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <AppRoutes />
    </AuthProvider>
  );
}