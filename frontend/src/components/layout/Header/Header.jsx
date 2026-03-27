import { useState } from "react";
import {
  FaArrowRight,
  FaBars,
  FaSearch,
  FaShoppingCart,
  FaTimes,
  FaUser,
} from "react-icons/fa";
import { Link } from "react-router-dom";
import logo from "../../../assets/logo.png";
import { primaryNav, utilityHighlights } from "../../../data/storefront";

function NavItem({ item, onClick }) {
  const className =
    "text-sm font-medium text-white/[0.78] transition hover:text-[var(--accent)]";

  if (item.href.includes("#")) {
    return (
      <a className={className} href={item.href} onClick={onClick}>
        {item.label}
      </a>
    );
  }

  return (
    <Link className={className} to={item.href} onClick={onClick}>
      {item.label}
    </Link>
  );
}

export default function Header() {
  const [mobileOpen, setMobileOpen] = useState(false);

  return (
    <header className="sticky top-0 z-50">
      <div className="border-b border-white/[0.08] bg-[rgba(7,17,13,0.88)] backdrop-blur-xl">
        <div className="page-shell flex gap-6 overflow-x-auto py-3 text-[11px] uppercase tracking-[0.28em] text-[var(--accent)]">
          {utilityHighlights.map((item) => (
            <span key={item} className="whitespace-nowrap">
              {item}
            </span>
          ))}
        </div>
      </div>

      <div className="page-shell py-4">
        <div className="surface-panel flex items-center gap-3 rounded-full px-4 py-3 sm:px-5">
          <Link className="flex items-center gap-3" to="/">
            <div className="rounded-full border border-white/10 bg-white/[0.06] p-2">
              <img src={logo} alt="Electronics Management" className="h-9 w-9 object-contain" />
            </div>
            <div className="hidden sm:block">
              <p className="font-display text-sm font-semibold uppercase tracking-[0.32em] text-[var(--accent)]">
                Electronics
              </p>
              <p className="text-sm text-white/75">Management storefront</p>
            </div>
          </Link>

          <nav className="hidden flex-1 items-center justify-center gap-6 lg:flex">
            {primaryNav.map((item) => (
              <NavItem key={item.label} item={item} />
            ))}
          </nav>

          <div className="hidden min-w-[240px] items-center gap-3 rounded-full border border-white/10 bg-white/[0.04] px-4 py-2 text-sm text-white/75 xl:flex">
            <FaSearch className="text-white/[0.45]" />
            <input
              type="text"
              placeholder="Tim laptop, linh kien, man hinh..."
              className="w-full bg-transparent text-sm placeholder:text-white/40 focus:outline-none"
            />
          </div>

          <div className="ml-auto flex items-center gap-2 sm:gap-3">
            <button
              type="button"
              className="inline-flex h-11 w-11 items-center justify-center rounded-full border border-white/10 bg-white/[0.04] text-white transition hover:border-[rgba(215,245,111,0.4)] hover:text-[var(--accent)] xl:hidden"
            >
              <FaSearch />
            </button>
            <button
              type="button"
              className="hidden h-11 w-11 items-center justify-center rounded-full border border-white/10 bg-white/[0.04] text-white transition hover:border-[rgba(215,245,111,0.4)] hover:text-[var(--accent)] sm:inline-flex"
            >
              <FaUser />
            </button>
            <Link
              to="/checkout"
              className="inline-flex items-center gap-2 rounded-full border border-[rgba(215,245,111,0.18)] bg-[rgba(215,245,111,0.08)] px-4 py-3 text-sm font-semibold text-white transition hover:border-[rgba(215,245,111,0.38)] hover:text-[var(--accent)]"
            >
              <span className="relative inline-flex">
                <FaShoppingCart />
                <span className="absolute -right-2 -top-2 inline-flex h-4 w-4 items-center justify-center rounded-full bg-[var(--accent)] text-[10px] font-bold text-[#07110d]">
                  3
                </span>
              </span>
              <span className="hidden sm:inline">Don hang</span>
            </Link>
            <button
              type="button"
              className="inline-flex h-11 w-11 items-center justify-center rounded-full border border-white/10 bg-white/[0.04] text-white transition hover:border-[rgba(215,245,111,0.4)] hover:text-[var(--accent)] lg:hidden"
              onClick={() => setMobileOpen((current) => !current)}
            >
              {mobileOpen ? <FaTimes /> : <FaBars />}
            </button>
          </div>
        </div>

        {mobileOpen && (
          <div className="surface-panel mt-3 rounded-[28px] p-5 lg:hidden">
            <div className="space-y-4">
              {primaryNav.map((item) => (
                <div key={item.label} className="flex items-center justify-between">
                  <NavItem item={item} onClick={() => setMobileOpen(false)} />
                  <FaArrowRight className="text-xs text-white/[0.45]" />
                </div>
              ))}
            </div>
            <Link
              to="/checkout"
              className="mt-5 inline-flex items-center gap-2 text-sm font-semibold text-[var(--accent)]"
              onClick={() => setMobileOpen(false)}
            >
              Di den thanh toan
              <FaArrowRight className="text-xs" />
            </Link>
          </div>
        )}
      </div>
    </header>
  );
}
