import { Routes, Route } from "react-router-dom";
import MainLayout from "./components/layout/MainLayout";
import Home from "./pages/Home";
import Checkout from "./pages/Checkout";
import ProductDetail from "./pages/ProductDetail";
import ManageStaff from "./pages/Admin/ManageStaff";
import ManageUser from "./pages/Admin/ManageUser";
import Dashboard from "./pages/Admin/Dashboard";

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Home />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/products/:productId" element={<ProductDetail />} />
      </Route>
      <Route path="/admin" element={<Dashboard />}>
        <Route path="manage" element={<ManageStaff />} />
        <Route path="manage-staff" element={<ManageStaff />} />
        <Route path="manage-user" element={<ManageUser />} />
      </Route>
    </Routes>
  );
}

export default App;
