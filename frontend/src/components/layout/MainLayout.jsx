import { Outlet } from "react-router-dom";
import Footer from "./Footer/Footer";
import Header from "./Header/Header";

function MainLayout() {
  return (
    <div className="relative min-h-screen overflow-x-hidden">
      <div className="pointer-events-none fixed inset-x-0 top-[-160px] z-0 mx-auto h-[420px] w-[420px] rounded-full bg-[rgba(133,178,58,0.16)] blur-3xl" />
      <div className="pointer-events-none fixed bottom-[-180px] right-[-60px] z-0 h-[360px] w-[360px] rounded-full bg-[rgba(44,121,96,0.18)] blur-3xl" />
      <Header />
      <main className="relative z-10 min-h-screen pb-16">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}

export default MainLayout;
