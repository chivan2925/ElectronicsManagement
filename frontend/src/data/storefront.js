import banner1 from "../assets/banner1.jpg";
import banner2 from "../assets/banner2.png";
import lap1 from "../assets/lap1.png";
import lap2 from "../assets/lap2.png";
import ram1 from "../assets/ram1.png";


export const primaryNav = [
  { label: "Trang chủ", href: "/" },
  { label: "Hàng mới", href: "/#new-arrivals" },
  { label: "Danh mục", href: "/#categories" },
  { label: "Lý do chọn", href: "/#trust" },
  { label: "Showroom", href: "/#showroom" },
  { label: "Thanh toán", href: "/checkout" },
];

export const heroMetrics = [
  { value: "30+", label: "cấu hình mới cập nhật mỗi tuần" },
  { value: "4h", label: "thời gian lắp máy nhanh cho cấu hình có sẵn" },
  { value: "1:1", label: "kênh hỗ trợ riêng sau khi bàn giao" },
];

export const catalogFilters = [
  "Tất cả",
  "Laptop gaming",
  "Linh kiện",
  "Màn hình",
  "Phụ kiện",
];

export const productCatalog = [
  {
    id: "nitro-5",
    name: "Acer Nitro 5 RTX 4060",
    category: "Laptop gaming",
    badge: "Bán chạy",
    price: 25990000,
    compareAt: 27990000,
    imageUrl: banner2,
    description: "Màn 165Hz, GPU RTX 4060, sẵn hàng và nâng cấp SSD ngay tại showroom.",
    availability: "Sẵn máy demo",
    specs: ["i7 H-series", "16GB DDR5", "1TB SSD"],
    accent: "from-[#0c241a] via-[#17362a] to-[#234734]",
  },
  {
    id: "predator-x34",
    name: "Predator X34 OLED cong",
    category: "Màn hình",
    badge: "Mới cập bến",
    price: 32990000,
    compareAt: 35990000,
    imageUrl: lap1,
    description: "Màn cong 34 inch, màu sâu và độ trễ thấp cho editor lẫn game thủ cần độ ổn định.",
    availability: "Nhận test tại chỗ",
    specs: ["QD-OLED", "175Hz", "USB-C hub"],
    accent: "from-[#0d1c1a] via-[#153833] to-[#1e5a53]",
  },
  {
    id: "ram-kingston",
    name: "Kingston Fury Beast 32GB",
    category: "Linh kiện",
    badge: "Linh kiện hot",
    price: 3290000,
    compareAt: 3790000,
    imageUrl: ram1,
    description: "Kit DDR5 ổn định cho cả gaming và render, được test mem trước khi giao.",
    availability: "Còn hàng",
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
    description: "Laptop hiệu năng cao cho streamer và motion designer cần máy khỏe, tản nhiệt sạch.",
    availability: "Chờ đặt lịch xem máy",
    specs: ["RTX 4070", "240Hz", "Thunderbolt 4"],
    accent: "from-[#10151a] via-[#1b2834] to-[#254258]",
  },
  {
    id: "workstation-dock",
    name: "Dock 12-in-1 Creator Hub",
    category: "Phụ kiện",
    badge: "Setup gọn",
    price: 2490000,
    compareAt: 2890000,
    imageUrl: banner1,
    description: "Mở rộng cổng kết nối cho dàn laptop mỏng nhẹ và workstation di động.",
    availability: "Sẵn kho HCM",
    specs: ["2x HDMI", "2.5GbE", "PD 100W"],
    accent: "from-[#111716] via-[#1a312a] to-[#2a5141]",
  },
  {
    id: "asus-proart",
    name: "ASUS ProArt 27 4K",
    category: "Màn hình",
    badge: "Độ màu chuẩn",
    price: 18990000,
    compareAt: 20990000,
    imageUrl: lap1,
    description: "Màn hình 4K cho designer cần cân màu và quy trình làm việc ổn định cả ngày.",
    availability: "Cho xem màu thực tế",
    specs: ["4K UHD", "Delta E < 2", "USB-C 96W"],
    accent: "from-[#101618] via-[#213338] to-[#35545c]",
  },
  {
    id: "ssd-990-pro",
    name: "Samsung 990 Pro 2TB",
    category: "Linh kiện",
    badge: "Nâng cấp nhanh",
    price: 4290000,
    compareAt: 4790000,
    imageUrl: ram1,
    description: "SSD tốc độ cao cho workflow dựng phim và gaming, có clone dữ liệu tại chỗ.",
    availability: "Sẵn kit clone",
    specs: ["PCIe 4.0", "2TB", "7000MB/s"],
    accent: "from-[#121512] via-[#223227] to-[#426144]",
  },
  {
    id: "mx-master",
    name: "Logitech MX Master 3S",
    category: "Phụ kiện",
    badge: "Office pro",
    price: 2390000,
    compareAt: 2690000,
    imageUrl: banner2,
    description: "Chuột công thái học cho bạn làm việc dài giờ, nhảy chuẩn trên nhiều bề mặt.",
    availability: "Nhận tay ngay",
    specs: ["Silent click", "Flow", "USB-C"],
    accent: "from-[#141414] via-[#232a2f] to-[#3a4247]",
  },
];

export const trustReasons = [
  {
    eyebrow: "01",
    title: "Tư vấn sát nhu cầu thực tế",
    description:
      "Không bán theo trend. Team đi từ budget, phần mềm, mục đích sử dụng rồi mới đề xuất cấu hình.",
  },
  {
    eyebrow: "02",
    title: "Checklist test trước khi giao",
    description:
      "Nhiệt độ, xung nhịp, cổng kết nối và khả năng nâng cấp đều được kiểm tra để tránh lỗi vặt về sau.",
  },
  {
    eyebrow: "03",
    title: "Showroom để cầm và so sánh",
    description:
      "Laptop, màn hình, phụ kiện và linh kiện demo được xếp thành từng cụm để bạn xem nhanh và chốt nhanh.",
  },
  {
    eyebrow: "04",
    title: "Hỗ trợ sau bán có quy trình",
    description:
      "Cần hướng dẫn, bảo hành hay nâng cấp thêm? Mọi yêu cầu đều có một đầu mối theo dõi đến khi xong.",
  },
];

export const reviewStats = [
  { value: "4.9/5", label: "mức hài lòng trung bình trên các đơn demo và đơn showroom" },
  { value: "1-2h", label: "tốc độ giao nội thành cho các cấu hình sẵn kho" },
  { value: "92%", label: "khách quay lại để nâng cấp hoặc mua thêm phụ kiện" },
];

export const testimonials = [
  {
    name: "Minh Quân",
    role: "Streamer FPS",
    badge: "Đã mua 7 đơn",
    quote:
      "Mua tại showroom để sờ tay và test chuột, xong về vẫn được team theo đến lúc set xong dongle và profile.",
  },
  {
    name: "Thảo Vy",
    role: "Designer motion",
    badge: "Setup workstation",
    quote:
      "Phần mình cần nhất là không bị tư vấn theo trend. Team đi thẳng vào workflow và bố trí đúng combo màn hình, dock, SSD.",
  },
  {
    name: "Đức Anh",
    role: "Sinh viên ngành kỹ thuật",
    badge: "Build theo budget",
    quote:
      "Từ lúc chốt cấu hình đến lúc nhận máy rất rõ ràng. Có checklist test và báo nhiệt độ, không phải hỏi từng bước.",
  },
  {
    name: "Bảo Hân",
    role: "Lead editor",
    badge: "Đã nâng cấp 3 lần",
    quote:
      "Điểm ăn tiền là hậu mãi. Cần nâng cấp RAM hay đổi monitor thì vẫn tìm lại được đúng lịch sử mua và gợi ý nhanh.",
  },
];

export const showroomHighlights = [
  "Khu test laptop gaming và workstation theo từng tản nhiệt",
  "Bàn trải nghiệm màn hình cân màu, loa monitor và dock mở rộng",
  "Góc build PC với linh kiện sẵn kho để chốt nhanh trong ngày",
];

export const brandHeadline = {
  eyebrow: "Section hãng",
  title: "Chỉ đưa lên những hãng có thể tự tin giới thiệu.",
  description:
    "Phong cách tham chiếu đẩy một block brand rõ ràng ở cuối trang. Ở đây tôi giữ cùng tinh thần đó bằng logo-text grid và bảng thông tin phân phối.",
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
  "Nhập hàng rõ nguồn gốc và thông tin bảo hành",
  "Có sẵn mẫu demo với nhóm hàng chủ lực",
  "Tư vấn combo theo hệ sinh thái, không bán lẻ từng món một cách rời rạc",
];

export const insightCards = [
  {
    title: "Build theo ngân sách thay vì mua thừa",
    description:
      "Các gói cấu hình được chia rõ theo mốc 15, 25 và 40 triệu để bạn quyết nhanh hơn.",
    cta: "Xem gợi ý",
  },
  {
    title: "Làm việc với màn hình và phụ kiện đồng bộ",
    description:
      "Từ workstation, monitor đến hub kết nối đều được xếp thành combo để setup gọn và ổn định.",
    cta: "Tư vấn combo",
  },
];

export const brandMetrics = [
  { value: "12", label: "hãng đang có line-up test tại showroom" },
  { value: "100%", label: "sản phẩm ưu tiên kênh bảo hành rõ và dễ đối soát" },
  { value: "48h", label: "thời gian phân loại và xử lý trường hợp cần gửi hãng" },
];

export const supportedBrands = [
  { name: "ACER", focus: "Laptop gaming và Predator line", tone: "bg-[rgba(215,245,111,0.1)]" },
  { name: "ASUS", focus: "ProArt, ROG, monitor và linh kiện", tone: "bg-[rgba(41,96,79,0.28)]" },
  { name: "LENOVO", focus: "Legion, ThinkPad và workstation", tone: "bg-[rgba(18,39,31,0.86)]" },
  { name: "DELL", focus: "XPS, UltraSharp và máy văn phòng", tone: "bg-[rgba(215,245,111,0.08)]" },
  { name: "LG", focus: "Màn hình OLED và UltraWide", tone: "bg-[rgba(13,28,21,0.92)]" },
  { name: "LOGITECH", focus: "Phụ kiện setup và office pro", tone: "bg-[rgba(41,96,79,0.22)]" },
  { name: "SAMSUNG", focus: "SSD, monitor và storage workflow", tone: "bg-[rgba(18,39,31,0.9)]" },
  { name: "KINGSTON", focus: "RAM, SSD và nâng cấp nhanh", tone: "bg-[rgba(215,245,111,0.08)]" },
];

export const reviewMetrics = [
  { value: "4.9/5", label: "mức hài lòng từ nhóm khách mua setup và nâng cấp" },
  { value: "96%", label: "khách quay lại khi cần mua thêm màn hình hoặc SSD" },
  { value: "1 ngày", label: "thời gian trung bình để chốt và giao nội thành" },
];

export const customerReviews = [
  {
    name: "Minh Quân",
    role: "Editor freelance",
    purchase: "Nâng cấp màn hình 4K + SSD clone dữ liệu",
    quote:
      "Team tư vấn rất sát workflow, không ép lên cấu hình. Bàn giao xong là vào việc được ngay.",
    rating: 5,
  },
  {
    name: "Hà Vy",
    role: "Streamer",
    purchase: "Laptop Legion + dock + màn hình phụ",
    quote:
      "Điểm tốt nhất là cách họ gom thành combo và set sẵn kết nối, không mất thời gian mua lẻ từng món.",
    rating: 5,
  },
  {
    name: "Duy Khang",
    role: "Chủ studio nhỏ",
    purchase: "Build PC render + 2 monitor cân màu",
    quote:
      "Checklist trước giao rõ, có quay lại nhiệt độ và thông số. Cảm giác chốt đơn dễ hơn rất nhiều.",
    rating: 5,
  },
  {
    name: "Lan Anh",
    role: "PM agency",
    purchase: "Mua 6 máy office + phụ kiện đồng bộ",
    quote:
      "Cân nhất của team là phần giao tiếp và theo dõi sau bán. Cần thêm hub và SSD là xử lý tiếp rất nhanh.",
    rating: 5,
  },
];

export const footerColumns = [
  {
    title: "Danh mục",
    links: ["Laptop gaming", "Linh kiện PC", "Màn hình", "Phụ kiện bàn làm việc"],
  },
  {
    title: "Dịch vụ",
    links: ["Tư vấn build PC", "Nâng cấp tại chỗ", "Cân màu màn hình", "Bảo trì định kỳ"],
  },
  {
    title: "Chính sách",
    links: ["Bảo hành minh bạch", "Giao hàng toàn quốc", "Trả góp 0%", "Hỗ trợ doanh nghiệp"],
  },
];

export const contactDetails = [
  {
    label: "Showroom",
    value: "Tầng 2, khu trải nghiệm Electronics Management, Q.3, TP.HCM",
  },
  {
    label: "Hotline",
    value: "0900 000 126 | 028 7300 1126",
  },
  {
    label: "Khung giờ",
    value: "08:30 - 20:00 mỗi ngày, tiếp nhận bảo hành đến 17:30",
  },
];

export function formatCurrency(value) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    maximumFractionDigits: 0,
  }).format(value);
}
