import { NavLink, Outlet, useLocation } from "react-router-dom";

const sidebarItems = [
  { label: "Tổng quan", href: "/admin", end: true },
  { label: "Quản lý nhân viên", href: "/admin/manage" },
  { label: "Quản lý người dùng", href: "/admin/manage-user" },
];

const stats = [
  { label: "Đơn hàng hôm nay", value: "126" },
  { label: "Doanh thu", value: "248.000.000đ" },
  { label: "Khách hàng mới", value: "39" },
];

function Dashboard() {
  const location = useLocation();
  const isOverviewRoute = location.pathname === "/admin";

  return (
    <div className="min-h-screen bg-white text-slate-900">
      <div className="mx-auto flex min-h-screen w-full max-w-[1480px]">
        <aside className="w-full max-w-[280px] border-r border-[rgb(41,48,50)] bg-[rgb(24,28,29)] px-6 py-8 text-white">
          <p className="font-display text-xl font-bold tracking-tight text-white">
            Admin Panel
          </p>
          <p className="mt-1 text-sm text-white/65">
            Hệ thống quản trị cửa hàng
          </p>

          <nav className="mt-8 space-y-2">
            {sidebarItems.map((item) => (
              <NavLink
                key={item.href}
                to={item.href}
                end={item.end}
                className={({ isActive }) =>
                  `block rounded-xl px-4 py-3 text-sm font-semibold transition ${
                    isActive
                      ? "bg-[rgb(41,48,50)] text-white"
                      : "text-white/80 hover:bg-white/10 hover:text-white"
                  }`
                }
              >
                {item.label}
              </NavLink>
            ))}
          </nav>
        </aside>

        <section className="flex-1 bg-white px-6 py-8 md:px-10">
          {isOverviewRoute ? (
            <>
              <header className="flex flex-wrap items-center justify-between gap-3 border-b border-slate-200 pb-6">
                <div>
                  <h1 className="text-3xl font-bold tracking-tight text-slate-900">
                    Dashboard quản trị
                  </h1>
                  <p className="mt-2 text-sm text-slate-500">
                    Theo dõi chỉ số kinh doanh và quản lý vận hành tại một nơi.
                  </p>
                </div>
                <button
                  type="button"
                  className="rounded-xl bg-slate-900 px-4 py-2.5 text-sm font-semibold text-white transition hover:bg-slate-800"
                >
                  Tạo báo cáo mới
                </button>
              </header>

              <div className="mt-6 grid gap-4 sm:grid-cols-2 xl:grid-cols-3">
                {stats.map((item) => (
                  <article
                    key={item.label}
                    className="rounded-2xl border border-slate-200 bg-white p-5"
                  >
                    <p className="text-sm font-medium text-slate-500">
                      {item.label}
                    </p>
                    <p className="mt-2 text-2xl font-bold tracking-tight text-slate-900">
                      {item.value}
                    </p>
                  </article>
                ))}
              </div>

              <article className="mt-6 rounded-2xl border border-slate-200 bg-white p-6">
                <h2 className="text-lg font-semibold text-slate-900">
                  Hoạt động gần đây
                </h2>
                <p className="mt-3 text-sm leading-6 text-slate-600">
                  Hệ thống đã ghi nhận 12 đơn hàng mới trong 1 giờ gần nhất, tỷ
                  lệ xử lý đúng hạn đạt 98%, và không có cảnh báo tồn kho quan
                  trọng.
                </p>
              </article>
            </>
          ) : (
            <Outlet />
          )}
        </section>
      </div>
    </div>
  );
}

export default Dashboard;
