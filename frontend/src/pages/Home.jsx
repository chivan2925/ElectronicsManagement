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
  brandHeadline,
  brandPartners,
  brandPromises,
  catalogFilters,
  heroMetrics,
  insightCards,
  productCatalog,
  reviewStats,
  showroomHighlights,
  testimonials,
  trustReasons,
  utilityHighlights,
} from "../data/storefront";

const heroSlides = [
  {
    id: "gaming",
    eyebrow: "Gaming setup cap nhat",
    title: "Slide hero full man hinh de mo trang dung chat showroom.",
    description:
      "Phong cach tham chieu mo trang bang slideshow anh lon, text dat tren overlay toi va CTA rat ro. O day toi doi hero sang cung cach trinh bay do.",
    background: banner1,
    foreground: banner2,
    foregroundAlt: "Featured gaming laptop",
    spotlight: "Acer Nitro 5 RTX 4060",
    note: "Nhan may san, test tai showroom va giao nhanh trong ngay.",
    tone: "from-[rgba(4,10,8,0.82)] via-[rgba(7,17,13,0.55)] to-[rgba(15,52,39,0.24)]",
    visualClassName:
      "bottom-[12%] left-1/2 w-[82vw] max-w-[920px] -translate-x-1/2 object-contain sm:w-[68vw] lg:left-[68%] lg:max-w-[760px] lg:-translate-x-1/2",
  },
  {
    id: "creator",
    eyebrow: "Creator workstation",
    title: "Anh lon, thong diep ngan, mot nhin la thay phong cach trang.",
    description:
      "Slide thu hai day man hinh va workflow len truoc, giong cach website tham chieu thay doi thong diep nhung van giu cung bo cuc.",
    background: lap1Webp,
    foreground: lap1,
    foregroundAlt: "Creator workstation display",
    spotlight: "Predator X34 OLED cong",
    note: "Man OLED, mau sau, de test tai showroom cung hub va phu kien dong bo.",
    tone: "from-[rgba(5,11,13,0.84)] via-[rgba(8,18,23,0.58)] to-[rgba(28,70,74,0.28)]",
    visualClassName:
      "bottom-[10%] left-1/2 w-[76vw] max-w-[820px] -translate-x-1/2 object-contain sm:w-[62vw] lg:left-[66%] lg:max-w-[680px] lg:-translate-x-1/2",
  },
  {
    id: "components",
    eyebrow: "Linh kien va nang cap",
    title: "Khong chi la banner, day la slide ban hang co nhiep dieu huong ro.",
    description:
      "Slide thu ba day phan linh kien len de hero co nhieu diem nhan hon thay vi chi mot loai san pham. Huong nhin va CTA van duoc giu thong nhat.",
    background: banner1,
    foreground: ram1,
    foregroundAlt: "High-performance RAM upgrade",
    spotlight: "Kingston Fury Beast DDR5",
    note: "Nang cap nhanh, test mem truoc khi giao va ho tro clone, setup tai cho.",
    tone: "from-[rgba(9,8,5,0.84)] via-[rgba(20,16,8,0.62)] to-[rgba(69,87,19,0.22)]",
    visualClassName:
      "bottom-[10%] left-1/2 w-[66vw] max-w-[620px] -translate-x-1/2 object-contain sm:w-[52vw] lg:left-[68%] lg:max-w-[520px] lg:-translate-x-1/2",
  },
];

function SectionHeading({ eyebrow, title, description, action }) {
  return (
    <div className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">
      <div className="max-w-3xl">
        <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">{eyebrow}</p>
        <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">{title}</h2>
        <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">{description}</p>
      </div>
      {action}
    </div>
  );
}

function Home() {
  const [activeFilter, setActiveFilter] = useState("Tat ca");
  const [activeHero, setActiveHero] = useState(0);

  const filteredProducts =
    activeFilter === "Tat ca"
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
      <section className="relative left-1/2 w-screen -translate-x-1/2 overflow-hidden">
        <div className="relative min-h-[calc(100vh-132px)] bg-[#030a08]">
          {heroSlides.map((slide, index) => (
            <article
              key={slide.id}
              className={`absolute inset-0 transition-all duration-700 ${
                index === activeHero ? "opacity-100" : "pointer-events-none opacity-0"
              }`}
            >
              <img
                src={slide.background}
                alt={slide.title}
                className="absolute inset-0 h-full w-full scale-105 object-cover"
              />
              <div className={`absolute inset-0 bg-gradient-to-r ${slide.tone}`} />
              <div className="absolute inset-0 bg-[linear-gradient(180deg,rgba(0,0,0,0.12),rgba(0,0,0,0.6))]" />
              <div className="absolute inset-0 bg-[radial-gradient(circle_at_top_right,rgba(215,245,111,0.16),transparent_26%),radial-gradient(circle_at_bottom_left,rgba(44,121,96,0.22),transparent_32%)]" />
              <img
                src={slide.foreground}
                alt={slide.foregroundAlt}
                className={`pointer-events-none absolute ${slide.visualClassName} max-h-[58vh] drop-shadow-[0_40px_80px_rgba(0,0,0,0.48)] transition-all duration-700 ${
                  index === activeHero ? "scale-100 opacity-100" : "scale-95 opacity-0"
                }`}
              />
            </article>
          ))}

          <div className="page-shell relative z-10 flex min-h-[calc(100vh-132px)] items-center justify-center py-10 pb-32 text-center sm:py-14 sm:pb-36">
            <div className="mx-auto flex w-full max-w-5xl flex-col items-center">
              <div className="inline-flex rounded-full border border-[rgba(215,245,111,0.18)] bg-[rgba(7,17,13,0.48)] px-4 py-2 text-xs uppercase tracking-[0.32em] text-[var(--accent)] backdrop-blur-md">
                {currentHero.eyebrow}
              </div>

              <h1 className="font-display mt-5 max-w-4xl text-4xl font-semibold leading-tight text-white sm:text-5xl lg:text-7xl">
                {currentHero.title}
              </h1>
              <p className="mt-5 max-w-3xl text-sm leading-7 text-white/[0.78] sm:text-base lg:text-lg">
                {currentHero.description}
              </p>

              <div className="mt-6 flex flex-wrap justify-center gap-3">
                {utilityHighlights.slice(0, 3).map((item) => (
                  <span
                    key={item}
                    className="rounded-full border border-white/[0.08] bg-black/[0.28] px-4 py-2 text-xs uppercase tracking-[0.2em] text-white/[0.72] backdrop-blur-md"
                  >
                    {item}
                  </span>
                ))}
              </div>
              <div className="mt-8 flex flex-wrap justify-center gap-3">

                <a className="primary-button min-w-[220px]" href="#new-arrivals">
                  Xem nhieu san pham
                  <FaArrowRight className="text-xs" />
                </a>
                <Link className="secondary-button min-w-[190px] bg-black/[0.24]" to="/checkout">
                  Di den checkout
                </Link>
              </div>

              <div className="mt-8 rounded-[30px] border border-white/[0.08] bg-[rgba(8,16,13,0.52)] px-6 py-5 backdrop-blur-xl">
                <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Slide hien tai</p>
                <h2 className="mt-3 font-display text-2xl font-semibold text-white sm:text-3xl">
                  {currentHero.spotlight}
                </h2>
                <p className="mt-3 max-w-2xl text-sm leading-7 text-white/[0.72]">{currentHero.note}</p>
              </div>

              <div className="mt-10 grid w-full max-w-4xl gap-4 sm:grid-cols-3">
                {heroMetrics.map((metric) => (
                  <div
                    key={metric.label}
                    className="rounded-[24px] border border-white/[0.08] bg-[rgba(5,13,10,0.48)] p-4 backdrop-blur-md"
                  >
                    <p className="font-display text-3xl font-semibold text-white">{metric.value}</p>
                    <p className="mt-2 text-sm leading-6 text-white/[0.68]">{metric.label}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="page-shell pointer-events-none absolute inset-x-0 top-1/2 z-20 hidden -translate-y-1/2 justify-between lg:flex">
            <button
              type="button"
              aria-label="Slide truoc"
              className="pointer-events-auto inline-flex h-14 w-14 items-center justify-center rounded-full border border-white/[0.08] bg-[rgba(7,17,13,0.55)] text-white backdrop-blur-md transition hover:border-[rgba(215,245,111,0.4)] hover:text-[var(--accent)]"
              onClick={() =>
                setActiveHero((current) => (current === 0 ? heroSlides.length - 1 : current - 1))
              }
            >
              <FaChevronLeft />
            </button>
            <button
              type="button"
              aria-label="Slide ke tiep"
              className="pointer-events-auto inline-flex h-14 w-14 items-center justify-center rounded-full border border-white/[0.08] bg-[rgba(7,17,13,0.55)] text-white backdrop-blur-md transition hover:border-[rgba(215,245,111,0.4)] hover:text-[var(--accent)]"
              onClick={() =>
                setActiveHero((current) => (current === heroSlides.length - 1 ? 0 : current + 1))
              }
            >
              <FaChevronRight />
            </button>
          </div>

          <div className="absolute inset-x-0 bottom-0 z-20">
            <div className="page-shell pb-5 sm:pb-6">
              <div className="rounded-[30px] border border-white/[0.08] bg-[rgba(7,17,13,0.5)] p-4 backdrop-blur-xl sm:p-5">
                <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
                  <div className="flex items-center gap-3">
                    {heroSlides.map((slide, index) => (
                      <button
                        key={slide.id}
                        type="button"
                        aria-label={`Chuyen den slide ${index + 1}`}
                        onClick={() => setActiveHero(index)}
                        className={`h-2.5 rounded-full transition-all duration-300 ${
                          index === activeHero
                            ? "w-14 bg-[var(--accent)]"
                            : "w-8 bg-white/[0.28] hover:bg-white/[0.5]"
                        }`}
                      />
                    ))}
                  </div>

                  <div
                    id="categories"
                    className="flex gap-3 overflow-x-auto text-sm"
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
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id="new-arrivals" className="page-shell">
        <SectionHeading
          eyebrow="San pham noi bat"
          title="Grid san pham duoc reset theo giao dien showroom."
          description="Phan card san pham duoc lam lai de de quet mat hon: badge ro, thong so ngan, gia tach lop va mot huong CTA duy nhat."
          action={
            <Link className="secondary-button self-start lg:self-auto" to="/checkout">
              Den trang thanh toan
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
        <SectionHeading
          eyebrow="Ly do chon"
          title="Bon block trust tach rieng, giong cach reference day uy tin sau khu san pham."
          description="Thay vi mot doan van dai, moi ly do duoc tach thanh card rieng de nguoi dung doc nhanh va nho nhanh."
        />

        <div className="mt-8 grid gap-5 lg:grid-cols-4">
          {trustReasons.map((reason, index) => (
            <article
              key={reason.title}
              className={`rounded-[30px] border p-6 shadow-[0_18px_55px_rgba(0,0,0,0.18)] ${
                index % 2 === 0
                  ? "border-white/[0.08] bg-[rgba(13,28,21,0.92)]"
                  : "border-[rgba(215,245,111,0.14)] bg-[rgba(215,245,111,0.08)]"
              }`}
            >
              <p className="font-display text-4xl font-semibold text-[var(--accent)]">{reason.eyebrow}</p>
              <h3 className="mt-5 text-xl font-semibold text-white">{reason.title}</h3>
              <p className="mt-4 text-sm leading-7 text-[var(--muted)]">{reason.description}</p>
            </article>
          ))}
        </div>
      </section>

      <section id="showroom" className="page-shell">
        <div className="grid gap-6 lg:grid-cols-[0.95fr_1.05fr]">
          <div className="surface-panel rounded-[34px] p-6 lg:p-8">
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">Showroom section</p>
            <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              Them mot khoi trai nghiem de layout co diem dung giua trang.
            </h2>
            <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">
              Reference co mot khoi ke ve trai nghiem thu hang va tu van. O day toi chuyen hoa
              thanh section showcase de nguoi dung thay loi the mua tai cho.
            </p>

            <div className="mt-8 space-y-4">
              {showroomHighlights.map((item) => (
                <div key={item} className="surface-soft flex items-start gap-3 rounded-[22px] p-4">
                  <span className="mt-1 inline-flex h-8 w-8 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                    <FaCheck className="text-xs" />
                  </span>
                  <p className="text-sm leading-7 text-white/[0.84]">{item}</p>
                </div>
              ))}
            </div>

            <div className="mt-8 grid gap-4 sm:grid-cols-3">
              <div className="surface-soft rounded-[24px] p-4">
                <FaMapMarkerAlt className="text-[var(--accent)]" />
                <p className="mt-4 text-sm text-white/[0.72]">Q.3, TP.HCM</p>
              </div>
              <div className="surface-soft rounded-[24px] p-4">
                <FaClock className="text-[var(--accent)]" />
                <p className="mt-4 text-sm text-white/[0.72]">08:30 - 20:00</p>
              </div>
              <div className="surface-soft rounded-[24px] p-4">
                <FaPhoneAlt className="text-[var(--accent)]" />
                <p className="mt-4 text-sm text-white/[0.72]">0900 000 126</p>
              </div>
            </div>
          </div>

          <div className="relative overflow-hidden rounded-[34px] border border-white/[0.08] bg-[linear-gradient(160deg,rgba(215,245,111,0.08),rgba(12,23,18,0.94))] p-6 lg:p-8">
            <div className="absolute left-[-40px] top-[-40px] h-40 w-40 rounded-full bg-[rgba(215,245,111,0.14)] blur-3xl" />
            <div className="absolute bottom-[-40px] right-[-20px] h-44 w-44 rounded-full bg-[rgba(57,130,103,0.18)] blur-3xl" />

            <div className="relative grid h-full gap-5 sm:grid-cols-2">
              <div className="surface-soft rounded-[28px] p-5">
                <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Focus area</p>
                <h3 className="mt-3 text-2xl font-semibold text-white">Hero, grid, trust, checkout.</h3>
                <p className="mt-4 text-sm leading-7 text-white/[0.76]">
                  Day la bo khung chi phoi toan bo giao dien moi de project co mot huong layout
                  thong nhat.
                </p>
              </div>

              <div className="surface-soft rounded-[28px] p-5">
                <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Section flow</p>
                <h3 className="mt-3 text-2xl font-semibold text-white">Hero xong den card san pham va trust.</h3>
                <p className="mt-4 text-sm leading-7 text-white/[0.76]">
                  Nhip section duoc sap lai de nguoi dung khong phai nhay qua nhieu kieu block.
                </p>
              </div>

              <div className="surface-soft rounded-[28px] p-5 sm:col-span-2">
                <img
                  src={banner2}
                  alt="Featured laptop"
                  className="mx-auto h-[280px] w-full object-contain drop-shadow-[0_36px_55px_rgba(0,0,0,0.34)]"
                />
                <div className="mt-5 flex flex-wrap items-center justify-between gap-3">
                  <div>
                    <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Cap ben moi</p>
                    <p className="mt-2 text-lg font-semibold text-white">Acer Nitro 5 RTX 4060</p>
                  </div>
                  <Link className="primary-button" to="/checkout">
                    Tao don nhanh
                    <FaArrowRight className="text-xs" />
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id="reviews" className="page-shell">
        <div className="grid gap-6 lg:grid-cols-[0.92fr_1.08fr]">
          <div className="surface-panel rounded-[34px] p-6 lg:p-8">
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">Section danh gia</p>
            <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              Khong phai team noi, nguoi da mua noi.
            </h2>
            <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">
              Tren trang tham chieu, block review duoc dua vao de tang do tin sau khi di qua
              phan san pham va trust. Toi them mot khoi cung nhip do de giu dong cam xuc do.
            </p>

            <div className="mt-8 grid gap-4 sm:grid-cols-3 lg:grid-cols-1">
              {reviewStats.map((stat) => (
                <div key={stat.label} className="surface-soft rounded-[24px] p-4">
                  <p className="font-display text-3xl font-semibold text-white">{stat.value}</p>
                  <p className="mt-2 text-sm leading-6 text-[var(--muted)]">{stat.label}</p>
                </div>
              ))}
            </div>
          </div>

          <div className="grid gap-5 md:grid-cols-2">
            {testimonials.map((item) => (
              <article key={item.name} className="surface-soft rounded-[30px] p-6">
                <div className="flex items-start justify-between gap-4">
                  <span className="inline-flex h-12 w-12 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                    <FaQuoteLeft />
                  </span>
                  <span className="rounded-full border border-[rgba(215,245,111,0.18)] bg-[rgba(215,245,111,0.08)] px-3 py-1 text-[11px] font-semibold uppercase tracking-[0.24em] text-[var(--accent)]">
                    {item.badge}
                  </span>
                </div>

                <div className="mt-5 flex gap-1 text-[var(--accent)]">
                  {Array.from({ length: 5 }).map((_, index) => (
                    <FaStar key={`${item.name}-${index}`} className="text-sm" />
                  ))}
                </div>

                <p className="mt-5 text-sm leading-7 text-white/[0.82]">{item.quote}</p>

                <div className="mt-6 border-t border-white/[0.08] pt-4">
                  <p className="font-display text-xl font-semibold text-white">{item.name}</p>
                  <p className="mt-1 text-sm text-white/[0.5]">{item.role}</p>
                </div>
              </article>
            ))}
          </div>
        </div>
      </section>

      <section id="brands" className="page-shell">
        <div className="surface-panel overflow-hidden rounded-[34px] p-6 lg:p-8">
          <div className="grid gap-8 lg:grid-cols-[0.88fr_1.12fr]">
            <div>
              <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">{brandHeadline.eyebrow}</p>
              <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
                {brandHeadline.title}
              </h2>
              <p className="mt-4 text-sm leading-7 text-[var(--muted)] sm:text-base">
                {brandHeadline.description}
              </p>

              <div className="mt-8 space-y-4">
                {brandPromises.map((item) => (
                  <div key={item} className="surface-soft flex items-start gap-3 rounded-[22px] p-4">
                    <span className="mt-1 inline-flex h-8 w-8 items-center justify-center rounded-full bg-[rgba(215,245,111,0.14)] text-[var(--accent)]">
                      <FaCheck className="text-xs" />
                    </span>
                    <p className="text-sm leading-7 text-white/[0.84]">{item}</p>
                  </div>
                ))}
              </div>
            </div>

            <div className="relative">
              <div className="brand-marquee brand-marquee--left">
                <div className="brand-marquee__track">
                  {[...brandPartners, ...brandPartners].map((brand, index) => (
                    <div
                      key={`${brand}-left-${index}`}
                      className="brand-tile"
                    >
                      <span className="brand-tile__name">{brand}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="brand-marquee brand-marquee--right mt-4">
                <div className="brand-marquee__track">
                  {[...brandPartners.slice().reverse(), ...brandPartners.slice().reverse()].map((brand, index) => (
                    <div
                      key={`${brand}-right-${index}`}
                      className="brand-tile"
                    >
                      <span className="brand-tile__name">{brand}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="mt-6 grid gap-4 sm:grid-cols-3">
                <div className="surface-soft rounded-[24px] p-4">
                  <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Danh muc chinh</p>
                  <p className="mt-3 text-lg font-semibold text-white">Laptop, monitor, gear, linh kien</p>
                </div>
                <div className="surface-soft rounded-[24px] p-4">
                  <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">He sinh thai</p>
                  <p className="mt-3 text-lg font-semibold text-white">Tu man hinh den phu kien dong bo</p>
                </div>
                <div className="surface-soft rounded-[24px] p-4">
                  <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Muc tieu</p>
                  <p className="mt-3 text-lg font-semibold text-white">Ban dung hang, dung doi tuong su dung</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="page-shell">
        <div className="grid gap-6 lg:grid-cols-[1.08fr_0.92fr]">
          <div className="surface-panel rounded-[34px] p-6 lg:p-8">
            <p className="text-xs uppercase tracking-[0.32em] text-[var(--accent)]">Block ket trang</p>
            <h2 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              Ket trang bang mot CTA lon thay vi ket thuc dot ngot.
            </h2>
            <p className="mt-4 max-w-2xl text-sm leading-7 text-[var(--muted)] sm:text-base">
              Phong cach tham chieu rat coi trong viec ket section bang mot loi moi hanh dong ro.
              Toi giu tinh than do bang block cuoi noi sang checkout va footer.
            </p>

            <div className="mt-8 flex flex-wrap gap-3">
              <Link className="primary-button" to="/checkout">
                Mo trang thanh toan
                <FaArrowRight className="text-xs" />
              </Link>
              <a className="secondary-button" href="#contact">
                Xuong footer
              </a>
            </div>
          </div>

          <div className="grid gap-5">
            {insightCards.map((card) => (
              <article key={card.title} className="surface-soft rounded-[28px] p-6">
                <p className="text-xs uppercase tracking-[0.28em] text-white/[0.45]">Store insight</p>
                <h3 className="mt-3 text-2xl font-semibold text-white">{card.title}</h3>
                <p className="mt-4 text-sm leading-7 text-white/[0.76]">{card.description}</p>
                <a className="mt-6 inline-flex items-center gap-2 text-sm font-semibold text-[var(--accent)]" href="/">
                  {card.cta}
                  <FaArrowRight className="text-xs" />
                </a>
              </article>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;
