import { FaArrowRight, FaClock, FaMapMarkerAlt, FaPhoneAlt } from "react-icons/fa";
import logo from "../../../assets/logo.png";
import { contactDetails, footerColumns } from "../../../data/storefront";

export default function Footer() {
  const icons = [FaMapMarkerAlt, FaPhoneAlt, FaClock];

  return (
    <footer className="relative z-10 pb-8 pt-6 text-white">
      <div id="contact" className="page-shell">
        <div className="surface-panel overflow-hidden rounded-[36px]">
          <div className="grid gap-8 border-b border-white/[0.08] px-6 py-8 lg:grid-cols-[1.1fr_0.9fr] lg:px-8">
            <div className="space-y-5">
              <div className="flex items-center gap-4">
                <div className="rounded-full border border-white/10 bg-white/[0.06] p-3">
                  <img src={logo} alt="Electronics Management" className="h-12 w-12 object-contain" />
                </div>
                <div>
                  <p className="font-display text-sm uppercase tracking-[0.32em] text-[var(--accent)]">
                    Electronics Management
                  </p>
                  <h2 className="mt-2 text-3xl font-semibold text-white sm:text-4xl">
                    Mot footer giong showroom, khong phai danh sach lien ket vo hon.
                  </h2>
                </div>
              </div>
              <p className="max-w-2xl text-sm leading-7 text-[var(--muted)] sm:text-base">
                Toan bo shell da duoc doi sang mot huong visual dam, xanh, phan cap ro va day
                section nhu cach phongcachxanh.vn dan nguoi dung xuong tung block.
              </p>
              <a className="inline-flex items-center gap-2 text-sm font-semibold text-[var(--accent)]" href="/checkout">
                Tao don mau
                <FaArrowRight className="text-xs" />
              </a>
            </div>

            <div className="grid gap-4">
              {contactDetails.map((item, index) => {
                const Icon = icons[index];
                return (
                  <div key={item.label} className="surface-soft flex items-start gap-4 rounded-[24px] p-4">
                    <span className="mt-1 inline-flex h-10 w-10 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                      <Icon />
                    </span>
                    <div>
                      <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">{item.label}</p>
                      <p className="mt-2 text-sm leading-6 text-white/[0.85]">{item.value}</p>
                    </div>
                  </div>
                );
              })}
            </div>
          </div>

          <div className="grid gap-8 px-6 py-8 md:grid-cols-2 xl:grid-cols-4 lg:px-8">
            <div className="space-y-4">
              <h3 className="font-display text-lg font-semibold text-white">Tong quan</h3>
              <p className="text-sm leading-7 text-[var(--muted)]">
                Layout moi uu tien khoi hero lon, the san pham sang, section trust ro va CTA
                day don hang theo cung mot visual language.
              </p>
            </div>

            {footerColumns.map((column) => (
              <div key={column.title}>
                <h3 className="font-display text-lg font-semibold text-white">{column.title}</h3>
                <ul className="mt-4 space-y-3 text-sm text-[var(--muted)]">
                  {column.links.map((link) => (
                    <li key={link}>
                      <a className="transition hover:text-[var(--accent)]" href="/">
                        {link}
                      </a>
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>

          <div className="flex flex-col gap-3 border-t border-white/[0.08] px-6 py-5 text-sm text-[var(--muted)] lg:flex-row lg:items-center lg:justify-between lg:px-8">
            <p>&copy; 2026 Electronics Management. Frontend reset theo huong storefront dark-green.</p>
            <p>Design focus: hero ro, section co nhip, CTA nhin thay ngay.</p>
          </div>
        </div>
      </div>
    </footer>
  );
}
