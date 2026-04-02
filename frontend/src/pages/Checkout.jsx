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
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">Thanh toán đơn hàng</p>
          </div>

          <Link className="secondary-button" to="/">
            <FaArrowLeft className="text-xs" />
            Quay lại trang chủ
          </Link>
        </div>

        <div className="mt-8 grid gap-6 lg:grid-cols-[1.08fr_0.92fr]">
          <div className="space-y-6">
            <section className="surface-soft rounded-[30px] p-5 sm:p-6">
              <h2 className="text-xl font-semibold text-white">Thông tin nhận hàng</h2>
              <div className="mt-5 grid gap-4 sm:grid-cols-2">
                <input className="field-shell" placeholder="Họ và tên" type="text" />
                <input className="field-shell" placeholder="Số điện thoại" type="tel" />
                <input className="field-shell sm:col-span-2" placeholder="Email" type="email" />
                <input className="field-shell sm:col-span-2" placeholder="Địa chỉ giao hàng" type="text" />
                <input className="field-shell" placeholder="Tỉnh / Thành phố" type="text" />
                <input className="field-shell" placeholder="Quận / Huyện" type="text" />
                <textarea
                  className="field-shell min-h-[140px] resize-none sm:col-span-2"
                  placeholder="Ghi chú giao hàng, nhu cầu nâng cấp, phần mềm cần cài..."
                />
              </div>
            </section>
            
            <section className="surface-soft rounded-[30px] p-5 sm:p-6">
              <h2 className="text-xl font-semibold text-white">Thanh toán</h2>
              <div className="mt-5 grid gap-4">
                <label className="rounded-[24px] border border-[rgba(215,245,111,0.22)] bg-[rgba(215,245,111,0.08)] p-4">
                  <p className="text-sm font-semibold text-white">Chuyển khoản / QR</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Xác nhận nhanh, thuận tiện cho đơn doanh nghiệp hoặc đơn giá trị cao.
                  </p>
                </label>
                <label className="rounded-[24px] border border-white/[0.08] bg-white/[0.04] p-4">
                  <p className="text-sm font-semibold text-white">Thanh toán khi nhận hàng</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    Áp dụng với sản phẩm có sẵn và khu vực hỗ trợ COD.
                  </p>
                </label>
              </div>
            </section>
          </div>

          <aside className="surface-soft rounded-[30px] p-5 sm:p-6 lg:sticky lg:top-36">
            <h2 className="text-xl font-semibold text-white">Tóm tắt đơn hàng</h2>

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
                Giao nội thành miễn phí cho đơn trên 10 triệu.
              </div>
              <div className="mt-4 flex items-center gap-3 text-sm text-white/75">
                <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                  <FaShieldAlt />
                </span>
                Bảo hành một đầu mối, cập nhật tiến độ xử lý rõ ràng.
              </div>
            </div>

            <div className="mt-6 space-y-3 text-sm text-white/[0.74]">
              <div className="flex items-center justify-between">
                <span>Tạm tính</span>
                <span>{formatCurrency(subtotal)}</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Phí setup</span>
                <span>{formatCurrency(serviceFee)}</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Vận chuyển</span>
                <span>{shippingFee === 0 ? "Miễn phí" : formatCurrency(shippingFee)}</span>
              </div>
              <div className="flex items-center justify-between border-t border-white/[0.08] pt-4 text-base font-semibold text-white">
                <span>Tổng cộng</span>
                <span>{formatCurrency(total)}</span>
              </div>
            </div>

            <button type="button" className="primary-button mt-6 w-full">
              Xác nhận đơn hàng
              <FaArrowRight className="text-xs" />
            </button>
          </aside>
        </div>
      </div>
    </section>
  );
}
