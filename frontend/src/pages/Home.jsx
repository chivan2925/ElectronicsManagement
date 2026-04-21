import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  FaArrowRight,
  FaCheck,
  FaChevronLeft,
  FaChevronRight,
  FaClock,
  FaMapMarkerAlt,
  FaPhoneAlt,
  FaQuoteLeft,
  FaStar,
} from "react-icons/fa";
import ProductCard from "../components/common/ProductCard";
import banner1 from "../assets/banner1.jpg";
import banner2 from "../assets/banner2.png";
import lap1 from "../assets/lap1.png";
import lap1Webp from "../assets/lap1.webp";
import ram1 from "../assets/ram1.png";
import {
  brandPartners,
  brandPromises,
  catalogFilters,
  productCatalog,
  reviewStats,
  testimonials,
  trustReasons,
} from "../data/storefront";

const heroSlides = [
  {
    id: "gaming",
    eyebrow: "Gaming setup cập nhật",
    title: "Not.",
    description:
      "Phong cách tham chiếu mở trang bằng slideshow ảnh lớn, text đặt trên overlay tối và CTA rất rõ. Ở đây tôi đổi hero sang cùng cách trình bày đó.",
    background: banner1,
    foreground: banner2,
    foregroundAlt: "Featured gaming laptop",
    spotlight: "Acer Nitro 5 RTX 4060",
    note: "Nhận máy sẵn, test tại showroom và giao nhanh trong ngày.",
    tone: "from-[rgba(24,28,29,0.88)] via-[rgba(24,28,29,0.62)] to-[rgba(41,48,50,0.26)]",
    visualClassName:
      "bottom-[12%] left-1/2 w-[82vw] max-w-[920px] -translate-x-1/2 object-contain sm:w-[68vw] lg:left-[68%] lg:max-w-[760px] lg:-translate-x-1/2",
  },
  {
    id: "creator",
    eyebrow: "Creator workstation",
    title: "Not.",
    description:
      "Slide thứ hai đẩy màn hình và workflow lên trước, giống cách website tham chiếu thay đổi thông điệp nhưng vẫn giữ cùng bố cục.",
    background: lap1Webp,
    foreground: lap1,
    foregroundAlt: "Creator workstation display",
    spotlight: "Predator X34 OLED cong",
    note: "Màn OLED, màu sâu, dễ test tại showroom cùng hub và phụ kiện đồng bộ.",
    tone: "from-[rgba(24,28,29,0.88)] via-[rgba(24,28,29,0.62)] to-[rgba(41,48,50,0.26)]",
    visualClassName:
      "bottom-[10%] left-1/2 w-[76vw] max-w-[820px] -translate-x-1/2 object-contain sm:w-[62vw] lg:left-[66%] lg:max-w-[680px] lg:-translate-x-1/2",
  },
  {
    id: "components",
    eyebrow: "Linh kiện và nâng cấp",
    title: "Not.",
    description:
      "Slide thứ ba đẩy phần linh kiện lên để hero có nhiều điểm nhấn hơn thay vì chỉ một loại sản phẩm. Hướng nhìn và CTA vẫn được giữ thống nhất.",
    background: banner1,
    foreground: ram1,
    foregroundAlt: "High-performance RAM upgrade",
    spotlight: "Kingston Fury Beast DDR5",
    note: "Nâng cấp nhanh, test mem trước khi giao và hỗ trợ clone, setup tại chỗ.",
    tone: "from-[rgba(24,28,29,0.88)] via-[rgba(24,28,29,0.62)] to-[rgba(41,48,50,0.26)]",
    visualClassName:
      "bottom-[10%] left-1/2 w-[66vw] max-w-[620px] -translate-x-1/2 object-contain sm:w-[52vw] lg:left-[68%] lg:max-w-[520px] lg:-translate-x-1/2",
  },
];

function SectionHeading({ eyebrow, title, description, action }) {
  return (
    <div className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">
      <div className="max-w-3xl">
        <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">
          {eyebrow}
        </p>
        <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
          {title}
        </h2>
        <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">
          {description}
        </p>
      </div>
      {action}
    </div>
  );
}

function getBrandMonogram(brand) {
  const normalized = brand.replace(/[^A-Za-z0-9]/g, "").toUpperCase();
  return normalized.slice(0, 2);
}

function Home() {
  const [activeFilter, setActiveFilter] = useState("Tất cả");
  const [activeHero, setActiveHero] = useState(0);

  const filteredProducts =
    activeFilter === "Tất cả"
      ? productCatalog
      : productCatalog.filter((product) => product.category === activeFilter);

  useEffect(() => {
    const timerId = window.setInterval(() => {
      setActiveHero((current) => (current + 1) % heroSlides.length);
    }, 5600);

    return () => window.clearInterval(timerId);
  }, []);

  const currentHero = heroSlides[activeHero];

  return (
    <div className="space-y-20 pb-20 pt-6 sm:space-y-24 sm:pt-8">
      {/* banner slideshow */}
      <section className="relative left-1/2 w-screen -translate-x-1/2 overflow-hidden">
        <div className="relative min-h-[calc(100vh-132px)] bg-[rgb(24,28,29)]">
          {heroSlides.map((slide, index) => (
            <article
              key={slide.id}
              className={`absolute inset-0 transition-all duration-700 ${
                index === activeHero
                  ? "opacity-100"
                  : "pointer-events-none opacity-0"
              }`}
            >
              <img
                src={slide.background}
                alt={slide.title}
                className="absolute inset-0 h-full w-full scale-105 object-cover"
              />
              <div
                className={`absolute inset-0 bg-gradient-to-r ${slide.tone}`}
              />
              <div className="absolute inset-0 bg-[linear-gradient(180deg,rgba(0,0,0,0.12),rgba(0,0,0,0.6))]" />
              <div className="absolute inset-0 bg-[radial-gradient(circle_at_top_right,rgba(41,48,50,0.24),transparent_26%),radial-gradient(circle_at_bottom_left,rgba(41,48,50,0.24),transparent_32%)]" />
            </article>
          ))}

          {/* --------------------Nội dung giữa */}
          <div className="page-shell relative z-10 flex min-h-[calc(100vh-132px)] items-center justify-center py-10 pb-32 text-center sm:py-14 sm:pb-36">
            <div className="mx-auto flex w-full max-w-5xl flex-col items-center">
              <p className="mt-5 max-w-3xl text-sm leading-7 text-white/[0.78] sm:text-base lg:text-lg">
                {currentHero.description}
              </p>
              <div className="mt-8 flex flex-wrap justify-center gap-3">
                <a
                  className="primary-button min-w-[220px]"
                  href="#new-arrivals"
                >
                  Xem nhiều sản phẩm
                  <FaArrowRight className="text-xs" />
                </a>
                <Link
                  className="secondary-button min-w-[190px] bg-black/[0.24]"
                  to="/checkout"
                >
                  Đi đến checkout
                </Link>
              </div>
            </div>
          </div>

          <div className="page-shell pointer-events-none absolute inset-x-0 top-1/2 z-20 hidden -translate-y-1/2 justify-between lg:flex">
            <button
              type="button"
              aria-label="Slide trước"
              className="pointer-events-auto inline-flex h-14 w-14 items-center justify-center rounded-full border border-white/[0.08] bg-[rgba(24,28,29,0.68)] text-white backdrop-blur-md transition hover:border-white/40 hover:text-[var(--accent)]"
              onClick={() =>
                setActiveHero((current) =>
                  current === 0 ? heroSlides.length - 1 : current - 1,
                )
              }
            >
              <FaChevronLeft />
            </button>
            <button
              type="button"
              aria-label="Slide kế tiếp"
              className="pointer-events-auto inline-flex h-14 w-14 items-center justify-center rounded-full border border-white/[0.08] bg-[rgba(24,28,29,0.68)] text-white backdrop-blur-md transition hover:border-white/40 hover:text-[var(--accent)]"
              onClick={() =>
                setActiveHero((current) =>
                  current === heroSlides.length - 1 ? 0 : current + 1,
                )
              }
            >
              <FaChevronRight />
            </button>
          </div>

          <div className="absolute inset-x-0 bottom-0 z-20">
            <div className="page-shell pb-5 sm:pb-6">
              <div className="rounded-[30px] border border-white/[0.08] bg-[rgba(24,28,29,0.72)] p-4 backdrop-blur-xl sm:p-5">
                <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
                  <div className="flex items-center gap-3">
                    {heroSlides.map((slide, index) => (
                      <button
                        key={slide.id}
                        type="button"
                        aria-label={`Chuyển đến slide ${index + 1}`}
                        onClick={() => setActiveHero(index)}
                        className={`h-2.5 rounded-full transition-all duration-300 ${
                          index === activeHero
                            ? "w-14 bg-[var(--accent)]"
                            : "w-8 bg-white/[0.28] hover:bg-white/[0.5]"
                        }`}
                      />
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      {/* Section PRODUCT */}
      <section id="new-arrivals" className="page-shell">
        {/* cateGory */}
        <div>
          <div
            id="categories"
            className="flex gap-3 overflow-x-auto text-sm justify-center  "
          >
            {catalogFilters.map((filter) => (
              <button
                key={filter}
                type="button"
                onClick={() => setActiveFilter(filter)}
                className={`chip-button whitespace-nowrap ${activeFilter === filter ? "is-active" : ""}`}
              >
                {filter}
              </button>
            ))}
          </div>
        </div>

        <SectionHeading
          eyebrow="Sản phẩm nổi bật"
          action={
            <Link
              className="secondary-button self-start lg:self-auto"
              to="/checkout"
            >
              Đến trang thanh toán
              <FaChevronRight className="text-xs" />
            </Link>
          }
        />

        <div className="mt-8 grid gap-5 md:grid-cols-2 xl:grid-cols-4">
          {filteredProducts.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      </section>

      <section id="trust" className="page-shell">
        <div className="grid items-start gap-8 xl:grid-cols-[1fr_1.12fr]">
          <div>
            <SectionHeading
              eyebrow="Lý do chọn"
              title="4 Lý do quan trọng phải chọn chúng tôi"
            />

            <div className="mt-8 grid gap-5 sm:grid-cols-2">
              {trustReasons.map((reason, index) => (
                <article
                  key={reason.title}
                  className={`h-full rounded-[30px] border p-5 shadow-[0_18px_55px_rgba(0,0,0,0.18)] xl:p-6 ${
                    index % 2 === 0
                      ? "border-white/[0.08] bg-[rgb(41,48,50)]"
                      : "border-white/[0.14] bg-[rgba(41,48,50,0.42)]"
                  }`}
                >
                  <p className="font-display text-4xl font-semibold text-[var(--accent)]">
                    {reason.eyebrow}
                  </p>
                  <h3 className="mt-4 text-xl font-semibold text-white">
                    {reason.title}
                  </h3>
                  <p className="mt-3 text-sm leading-7 text-[var(--muted)]">
                    {reason.description}
                  </p>
                </article>
              ))}
            </div>
          </div>

          <div id="reviews" className="surface-panel rounded-[34px] p-6 lg:p-8">
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">
              Đánh giá
            </p>
            <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              Không phải tụi mình nói – anh em nói
            </h2>

            <div className="mt-8 grid gap-4 sm:grid-cols-3">
              {reviewStats.map((stat) => (
                <div
                  key={stat.label}
                  className="surface-soft rounded-[24px] p-4"
                >
                  <p className="font-display text-3xl font-semibold text-white">
                    {stat.value}
                  </p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">
                    {stat.label}
                  </p>
                </div>
              ))}
            </div>

            <div className="mt-6 grid gap-5 md:grid-cols-2">
              {testimonials.map((item) => (
                <article
                  key={item.name}
                  className="surface-soft rounded-[30px] p-6"
                >
                  <div className="flex items-start justify-between gap-4">
                    <span className="inline-flex h-12 w-12 items-center justify-center rounded-full bg-[rgba(41,48,50,0.78)] text-[var(--accent)]">
                      <FaQuoteLeft />
                    </span>
                    <span className="rounded-full border border-white/[0.2] bg-[rgba(41,48,50,0.65)] px-3 py-1 text-[11px] font-semibold uppercase tracking-[0.24em] text-[var(--accent)]">
                      {item.badge}
                    </span>
                  </div>

                  <div className="mt-5 flex gap-1 text-[var(--accent)]">
                    {Array.from({ length: 5 }).map((_, index) => (
                      <FaStar
                        key={`${item.name}-${index}`}
                        className="text-sm"
                      />
                    ))}
                  </div>

                  <p className="mt-5 text-sm leading-7 text-white/[0.82]">
                    {item.quote}
                  </p>

                  <div className="mt-6 border-t border-white/[0.08] pt-4">
                    <p className="font-display text-xl font-semibold text-white">
                      {item.name}
                    </p>
                    <p className="mt-1 text-sm text-white/[0.5]">{item.role}</p>
                  </div>
                </article>
              ))}
            </div>
          </div>
        </div>
      </section>

      <section id="brands" className="page-shell ">
        <div className="surface-panel overflow-hidden rounded-[34px] p-6 lg:p-8">
          <div className="">
            <div>
              <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">
                Hãng tin dùng
              </p>
              <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
                Chỉ bán hãng mà tụi mình dám xài
              </h2>
              <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">
                Nhà phân phối chính hãng 20+ thương hiệu gaming gear hàng đầu
                thế giới. Tụi mình chọn đối tác như chọn gear: phải đủ tốt để tự
                tin giới thiệu cho anh em, không phải hãng nào cũng được.
              </p>

              <div className="mt-8 space-y-4">
                {brandPromises.map((item) => (
                  <div
                    key={item}
                    className="surface-soft flex items-start gap-3 rounded-[22px] p-4"
                  >
                    <span className="mt-1 inline-flex h-8 w-8 items-center justify-center rounded-full bg-[rgba(41,48,50,0.78)] text-[var(--accent)]">
                      <FaCheck className="text-xs" />
                    </span>
                    <p className="text-sm leading-7 text-white/[0.84]">
                      {item}
                    </p>
                  </div>
                ))}
              </div>
            </div>

            <div className="relative">
              <div className="brand-marquee brand-marquee--left">
                <div className="brand-marquee__track">
                  {[...brandPartners, ...brandPartners].map((brand, index) => (
                    <div key={`${brand}-left-${index}`} className="brand-tile">
                      <span className="brand-tile__icon">{getBrandMonogram(brand)}</span>
                      <span className="brand-tile__name">{brand}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="brand-marquee brand-marquee--right mt-4">
                <div className="brand-marquee__track">
                  {[
                    ...brandPartners.slice().reverse(),
                    ...brandPartners.slice().reverse(),
                  ].map((brand, index) => (
                    <div key={`${brand}-right-${index}`} className="brand-tile">
                      <span className="brand-tile__icon">{getBrandMonogram(brand)}</span>
                      <span className="brand-tile__name">{brand}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;
