import banner1 from "../assets/banner1.jpg";
import banner2 from "../assets/banner2.png";
import lap1 from "../assets/lap1.png";
import lap2 from "../assets/lap2.png";
import ram1 from "../assets/ram1.png";

export const utilityHighlights = [
  "Giao nhanh 24h khu vuc noi thanh",
  "Tu van dung cau hinh, dung ngan sach",
  "Tra gop 0% cho don tu 3 trieu",
  "Bao hanh mot dau moi, phan hoi ro rang",
];

export const primaryNav = [
  { label: "Trang chu", href: "/" },
  { label: "Hang moi", href: "/#new-arrivals" },
  { label: "Danh muc", href: "/#categories" },
  { label: "Ly do chon", href: "/#trust" },
  { label: "Showroom", href: "/#showroom" },
  { label: "Thanh toan", href: "/checkout" },
];

export const heroMetrics = [
  { value: "30+", label: "cau hinh moi cap nhat moi tuan" },
  { value: "4h", label: "thoi gian lap may nhanh cho cau hinh co san" },
  { value: "1:1", label: "kenh ho tro rieng sau khi ban giao" },
];

export const catalogFilters = [
  "Tat ca",
  "Laptop gaming",
  "Linh kien",
  "Man hinh",
  "Phu kien",
];

export const productCatalog = [
  {
    id: "nitro-5",
    name: "Acer Nitro 5 RTX 4060",
    category: "Laptop gaming",
    badge: "Ban chay",
    price: 25990000,
    compareAt: 27990000,
    imageUrl: banner2,
    description: "Man 165Hz, GPU RTX 4060, san hang va nang cap SSD ngay tai showroom.",
    availability: "San may demo",
    specs: ["i7 H-series", "16GB DDR5", "1TB SSD"],
    accent: "from-[#0c241a] via-[#17362a] to-[#234734]",
  },
  {
    id: "predator-x34",
    name: "Predator X34 OLED cong",
    category: "Man hinh",
    badge: "Moi cap ben",
    price: 32990000,
    compareAt: 35990000,
    imageUrl: lap1,
    description: "Man cong 34 inch, mau sau va do tre thap cho editor lan game thu can do on dinh.",
    availability: "Nhan test tai cho",
    specs: ["QD-OLED", "175Hz", "USB-C hub"],
    accent: "from-[#0d1c1a] via-[#153833] to-[#1e5a53]",
  },
  {
    id: "ram-kingston",
    name: "Kingston Fury Beast 32GB",
    category: "Linh kien",
    badge: "Linh kien hot",
    price: 3290000,
    compareAt: 3790000,
    imageUrl: ram1,
    description: "Kit DDR5 on dinh cho ca gaming va render, duoc test mem truoc khi giao.",
    availability: "Con hang",
    specs: ["DDR5", "6000MT/s", "CL36"],
    accent: "from-[#121b17] via-[#243329] to-[#375036]",
  },
  {
    id: "legion-pro",
    name: "Lenovo Legion Pro 7i",
    category: "Laptop gaming",
    badge: "Flagship",
    price: 48990000,
    compareAt: 52990000,
    imageUrl: lap2,
    description: "Laptop hieu nang cao cho streamer va motion designer can may khoe, tan nhiet sach.",
    availability: "Cho dat lich xem may",
    specs: ["RTX 4070", "240Hz", "Thunderbolt 4"],
    accent: "from-[#10151a] via-[#1b2834] to-[#254258]",
  },
  {
    id: "workstation-dock",
    name: "Dock 12-in-1 Creator Hub",
    category: "Phu kien",
    badge: "Setup gon",
    price: 2490000,
    compareAt: 2890000,
    imageUrl: banner1,
    description: "Mo rong cong ket noi cho dan laptop mong nhe va workstation di dong.",
    availability: "San kho HCM",
    specs: ["2x HDMI", "2.5GbE", "PD 100W"],
    accent: "from-[#111716] via-[#1a312a] to-[#2a5141]",
  },
  {
    id: "asus-proart",
    name: "ASUS ProArt 27 4K",
    category: "Man hinh",
    badge: "Do mau chuan",
    price: 18990000,
    compareAt: 20990000,
    imageUrl: lap1,
    description: "Man hinh 4K cho designer can can mau va quy trinh lam viec on dinh ca ngay.",
    availability: "Cho xem mau thuc te",
    specs: ["4K UHD", "Delta E < 2", "USB-C 96W"],
    accent: "from-[#101618] via-[#213338] to-[#35545c]",
  },
  {
    id: "ssd-990-pro",
    name: "Samsung 990 Pro 2TB",
    category: "Linh kien",
    badge: "Nang cap nhanh",
    price: 4290000,
    compareAt: 4790000,
    imageUrl: ram1,
    description: "SSD toc do cao cho workflow dung phim va gaming, co clone du lieu tai cho.",
    availability: "San kit clone",
    specs: ["PCIe 4.0", "2TB", "7000MB/s"],
    accent: "from-[#121512] via-[#223227] to-[#426144]",
  },
  {
    id: "mx-master",
    name: "Logitech MX Master 3S",
    category: "Phu kien",
    badge: "Office pro",
    price: 2390000,
    compareAt: 2690000,
    imageUrl: banner2,
    description: "Chuot cong thai hoc cho ban lam viec dai gio, nhay chuan tren nhieu be mat.",
    availability: "Nhan tay ngay",
    specs: ["Silent click", "Flow", "USB-C"],
    accent: "from-[#141414] via-[#232a2f] to-[#3a4247]",
  },
];

export const trustReasons = [
  {
    eyebrow: "01",
    title: "Tu van sat nhu cau thuc te",
    description:
      "Khong ban theo trend. Team di tu budget, phan mem, muc dich su dung roi moi de xuat cau hinh.",
  },
  {
    eyebrow: "02",
    title: "Checklist test truoc khi giao",
    description:
      "Nhiet do, xung nhip, cong ket noi va kha nang nang cap deu duoc kiem tra de tranh loi vat ve sau nay.",
  },
  {
    eyebrow: "03",
    title: "Showroom de cam va so sanh",
    description:
      "Laptop, man hinh, phu kien va linh kien demo duoc xep thanh tung cum de ban xem nhanh va chot nhanh.",
  },
  {
    eyebrow: "04",
    title: "Ho tro sau ban co quy trinh",
    description:
      "Can huong dan, bao hanh hay nang cap them? Moi yeu cau deu co mot dau moi theo doi den khi xong.",
  },
];

export const reviewStats = [
  { value: "4.9/5", label: "muc hai long trung binh tren cac don demo va don showroom" },
  { value: "1-2h", label: "toc do giao noi thanh cho cac cau hinh san kho" },
  { value: "92%", label: "khach quay lai de nang cap hoac mua them phu kien" },
];

export const testimonials = [
  {
    name: "Minh Quan",
    role: "Streamer FPS",
    badge: "Da mua 7 don",
    quote:
      "Mua tai showroom de so tay va test chuot, xong ve van duoc team theo den luc set xong dongle va profile.",
  },
  {
    name: "Thao Vy",
    role: "Designer motion",
    badge: "Setup workstation",
    quote:
      "Phan minh can nhat la khong bi tu van theo trend. Team di thang vao workflow va bo tri dung combo man hinh, dock, SSD.",
  },
  {
    name: "Duc Anh",
    role: "Sinh vien nganh ky thuat",
    badge: "Build theo budget",
    quote:
      "Tu luc chot cau hinh den luc nhan may rat ro rang. Co checklist test va bao nhiet do, khong phai hoi tung buoc.",
  },
  {
    name: "Bao Han",
    role: "Lead editor",
    badge: "Da nang cap 3 lan",
    quote:
      "Diem an tien la hau mai. Can nang cap RAM hay doi monitor thi van tim lai duoc dung lich su mua va goi y nhanh.",
  },
];

export const showroomHighlights = [
  "Khu test laptop gaming va workstation theo tung tan nhiet",
  "Ban trai nghiem man hinh can mau, loa monitor va dock mo rong",
  "Goc build PC voi linh kien san kho de chot nhanh trong ngay",
];

export const brandHeadline = {
  eyebrow: "Section hang",
  title: "Chi dua len nhung hang co the tu tin gioi thieu.",
  description:
    "Phong cach tham chieu day mot block brand ro rang o cuoi trang. O day toi giu cung tinh than do bang logo-text grid va bang thong tin phan phoi.",
};

export const brandPartners = [
  "ASUS",
  "Acer",
  "Lenovo",
  "Samsung",
  "LG",
  "Logitech",
  "Corsair",
  "Kingston",
  "MSI",
  "Dell",
  "ViewSonic",
  "HyperX",
];

export const brandPromises = [
  "Nhap hang ro nguon goc va thong tin bao hanh",
  "Co san mau demo voi nhom hang chu luc",
  "Tu van combo theo he sinh thai, khong ban le tung mon mot cach roi rac",
];

export const insightCards = [
  {
    title: "Build theo ngan sach thay vi mua thua",
    description:
      "Cac goi cau hinh duoc chia ro theo moc 15, 25 va 40 trieu de ban quyet nhanh hon.",
    cta: "Xem goi de xuat",
  },
  {
    title: "Lam viec voi man hinh va phu kien dong bo",
    description:
      "Tu workstation, monitor den hub ket noi deu duoc xep thanh combo de setup gon va on dinh.",
    cta: "Tu van combo",
  },
];

export const brandMetrics = [
  { value: "12", label: "hang dang co line-up test tai showroom" },
  { value: "100%", label: "san pham uu tien kenh bao hanh ro va de doi soat" },
  { value: "48h", label: "thoi gian phan loai va xu ly truong hop can gui hang" },
];

export const supportedBrands = [
  { name: "ACER", focus: "Laptop gaming va Predator line", tone: "bg-[rgba(215,245,111,0.1)]" },
  { name: "ASUS", focus: "ProArt, ROG, monitor va linh kien", tone: "bg-[rgba(41,96,79,0.28)]" },
  { name: "LENOVO", focus: "Legion, ThinkPad va workstation", tone: "bg-[rgba(18,39,31,0.86)]" },
  { name: "DELL", focus: "XPS, UltraSharp va may van phong", tone: "bg-[rgba(215,245,111,0.08)]" },
  { name: "LG", focus: "Man hinh OLED va UltraWide", tone: "bg-[rgba(13,28,21,0.92)]" },
  { name: "LOGITECH", focus: "Phu kien setup va office pro", tone: "bg-[rgba(41,96,79,0.22)]" },
  { name: "SAMSUNG", focus: "SSD, monitor va storage workflow", tone: "bg-[rgba(18,39,31,0.9)]" },
  { name: "KINGSTON", focus: "RAM, SSD va nang cap nhanh", tone: "bg-[rgba(215,245,111,0.08)]" },
];

export const reviewMetrics = [
  { value: "4.9/5", label: "muc hai long tu nhom khach mua setup va nang cap" },
  { value: "96%", label: "khach quay lai khi can mua them man hinh hoac SSD" },
  { value: "1 ngay", label: "thoi gian trung binh de chot va giao noi thanh" },
];

export const customerReviews = [
  {
    name: "Minh Quan",
    role: "Editor freelance",
    purchase: "Nang cap man hinh 4K + SSD clone du lieu",
    quote:
      "Team tu van rat sat workflow, khong ep len cau hinh. Ban giao xong la vao viec duoc ngay.",
    rating: 5,
  },
  {
    name: "Ha Vy",
    role: "Streamer",
    purchase: "Laptop Legion + dock + man hinh phu",
    quote:
      "Diem tot nhat la cach ho gom thanh combo va set san ket noi, khong mat thoi gian mua le tung mon.",
    rating: 5,
  },
  {
    name: "Duy Khang",
    role: "Chu studio nho",
    purchase: "Build PC render + 2 monitor can mau",
    quote:
      "Checklist truoc giao ro, co quay lai nhiet do va thong so. Cam giac chot don de hon rat nhieu.",
    rating: 5,
  },
  {
    name: "Lan Anh",
    role: "PM agency",
    purchase: "Mua 6 may office + phu kien dong bo",
    quote:
      "Can nhat cua team la phan giao tiep va theo doi sau ban. Can them hub va SSD la xu ly tiep rat nhanh.",
    rating: 5,
  },
];

export const footerColumns = [
  {
    title: "Danh muc",
    links: ["Laptop gaming", "Linh kien PC", "Man hinh", "Phu kien ban lam viec"],
  },
  {
    title: "Dich vu",
    links: ["Tu van build PC", "Nang cap tai cho", "Can mau man hinh", "Bao tri dinh ky"],
  },
  {
    title: "Chinh sach",
    links: ["Bao hanh minh bach", "Giao hang toan quoc", "Tra gop 0%", "Ho tro doanh nghiep"],
  },
];

export const contactDetails = [
  {
    label: "Showroom",
    value: "Tang 2, khu trai nghiem Electronics Management, Q.3, TP.HCM",
  },
  {
    label: "Hotline",
    value: "0900 000 126 | 028 7300 1126",
  },
  {
    label: "Khung gio",
    value: "08:30 - 20:00 moi ngay, tiep nhan bao hanh den 17:30",
  },
];

export function formatCurrency(value) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    maximumFractionDigits: 0,
  }).format(value);
}
