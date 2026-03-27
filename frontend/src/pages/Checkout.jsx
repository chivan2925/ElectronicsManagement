import { FaArrowLeft, FaArrowRight, FaShieldAlt, FaTruck } from "react-icons/fa";
import { Link } from "react-router-dom";
import { formatCurrency, productCatalog } from "../data/storefront";

export default function Checkout() {
  const summaryItems = productCatalog.slice(0, 3);
  const subtotal = summaryItems.reduce((total, item) => total + item.price, 0);
  const serviceFee = 290000;
  const shippingFee = 0;
  const total = subtotal + serviceFee + shippingFee;

  return (
    <section className="page-shell py-6 sm:py-10">
      <div className="surface-panel rounded-[36px] p-6 lg:p-8">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div>
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">Checkout reset</p>
            <h1 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              Trang thanh toan da doi cung visual voi trang chu.
            </h1>
            <p className="mt-4 max-w-2xl text-sm leading-7 text-[var(--muted)] sm:text-base">
              Giu lai huong dark-green, tach ro form va order summary de project khong bi doi layout
              nua duong.
            </p>
          </div>

          <Link className="secondary-button" to="/">
            <FaArrowLeft className="text-xs" />
            Quay lai trang chu
          </Link>
        </div>

        <div className="mt-8 grid gap-6 lg:grid-cols-[1.08fr_0.92fr]">
          <div className="space-y-6">
            <section className="surface-soft rounded-[30px] p-5 sm:p-6">
              <h2 className="text-xl font-semibold text-white">Thong tin nhan hang</h2>
              <div className="mt-5 grid gap-4 sm:grid-cols-2">
                <input className="field-shell" placeholder="Ho va ten" type="text" />
                <input className="field-shell" placeholder="So dien thoai" type="tel" />
                <input className="field-shell sm:col-span-2" placeholder="Email" type="email" />
                <input className="field-shell sm:col-span-2" placeholder="Dia chi giao hang" type="text" />
                <input className="field-shell" placeholder="Tinh / Thanh pho" type="text" />
                <input className="field-shell" placeholder="Quan / Huyen" type="text" />
                <textarea
                  className="field-shell min-h-[140px] resize-none sm:col-span-2"
                  placeholder="Ghi chu giao hang, nhu cau nang cap, phan mem can cai..."
                />
              </div>
            </section>

            <section className="surface-soft rounded-[30px] p-5 sm:p-6">
              <h2 className="text-xl font-semibold text-white">Phuong thuc xu ly don</h2>
              <div className="mt-5 grid gap-4 sm:grid-cols-2">
                <label className="rounded-[24px] border border-[rgba(215,245,111,0.22)] bg-[rgba(215,245,111,0.08)] p-4">
                  <p className="text-sm font-semibold text-white">Nhan cau hinh co san</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Don co san, xuat kho nhanh va giao noi thanh trong ngay.
                  </p>
                </label>
                <label className="rounded-[24px] border border-white/[0.08] bg-white/[0.04] p-4">
                  <p className="text-sm font-semibold text-white">Build va kiem tra theo yeu cau</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Team lap may, test nhiet do va quay checklist truoc khi giao.
                  </p>
                </label>
              </div>
            </section>

            <section className="surface-soft rounded-[30px] p-5 sm:p-6">
              <h2 className="text-xl font-semibold text-white">Thanh toan</h2>
              <div className="mt-5 grid gap-4">
                <label className="rounded-[24px] border border-[rgba(215,245,111,0.22)] bg-[rgba(215,245,111,0.08)] p-4">
                  <p className="text-sm font-semibold text-white">Chuyen khoan / QR</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Xac nhan nhanh, thuan tien cho don doanh nghiep hoac don gia tri cao.
                  </p>
                </label>
                <label className="rounded-[24px] border border-white/[0.08] bg-white/[0.04] p-4">
                  <p className="text-sm font-semibold text-white">Thanh toan khi nhan hang</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Ap dung voi san pham co san va khu vuc ho tro COD.
                  </p>
                </label>
              </div>
            </section>
          </div>

          <aside className="surface-soft rounded-[30px] p-5 sm:p-6 lg:sticky lg:top-36">
            <h2 className="text-xl font-semibold text-white">Tom tat don hang</h2>

            <div className="mt-5 space-y-4">
              {summaryItems.map((item) => (
                <div
                  key={item.id}
                  className="flex items-center gap-4 rounded-[24px] border border-white/[0.08] bg-black/[0.12] p-4"
                >
                  <img
                    src={item.imageUrl}
                    alt={item.name}
                    className="h-20 w-20 rounded-[18px] object-contain"
                  />
                  <div className="min-w-0 flex-1">
                    <p className="text-sm uppercase tracking-[0.22em] text-white/[0.45]">{item.category}</p>
                    <h3 className="mt-2 text-sm font-semibold leading-6 text-white">{item.name}</h3>
                  </div>
                  <p className="text-sm font-semibold text-white">{formatCurrency(item.price)}</p>
                </div>
              ))}
            </div>

            <div className="mt-6 rounded-[26px] border border-white/[0.08] bg-black/[0.12] p-5">
              <div className="flex items-center gap-3 text-sm text-white/75">
                <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                  <FaTruck />
                </span>
                Giao noi thanh mien phi cho don tren 10 trieu.
              </div>
              <div className="mt-4 flex items-center gap-3 text-sm text-white/75">
                <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                  <FaShieldAlt />
                </span>
                Bao hanh mot dau moi, cap nhat tien do xu ly ro rang.
              </div>
            </div>

            <div className="mt-6 space-y-3 text-sm text-white/[0.74]">
              <div className="flex items-center justify-between">
                <span>Tam tinh</span>
                <span>{formatCurrency(subtotal)}</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Phi setup</span>
                <span>{formatCurrency(serviceFee)}</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Van chuyen</span>
                <span>{shippingFee === 0 ? "Mien phi" : formatCurrency(shippingFee)}</span>
              </div>
              <div className="flex items-center justify-between border-t border-white/[0.08] pt-4 text-base font-semibold text-white">
                <span>Tong cong</span>
                <span>{formatCurrency(total)}</span>
              </div>
            </div>

            <button type="button" className="primary-button mt-6 w-full">
              Xac nhan don hang
              <FaArrowRight className="text-xs" />
            </button>
          </aside>
        </div>
      </div>
    </section>
  );
}
