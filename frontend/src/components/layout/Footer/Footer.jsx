import logo from "../../../assets/logo.png";

export default function Footer() {
    return (
        <footer className="bg-white border-t border-gray-200 pt-16 pb-8">
            <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-4 gap-8">
                {/* Column 1: Company Info */}
                <div className="space-y-4">
                    <h3 className="text-3xl font-extrabold text-gray-900 tracking-wider mb-6">
                        2kvh
                    </h3>
                    <p className="text-[13px] text-gray-600 leading-relaxed">
                        Track Nhanh – Bấm Mượt – Không Đổ Lỗi Cho Gear. Thương hiệu phân phối các sản phẩm Gaming Gear hi-end hàng đầu Việt Nam.
                    </p>
                    <div className="text-[13px] text-gray-600 mt-4 space-y-2">
                        <p><strong>Địa chỉ:</strong> 87/2 Võ Thị Sáu, Phường 6, Quận 3, TP.HCM</p>
                        <p><strong>Hotline:</strong> 0904 135 321</p>
                        <p><strong>Email:</strong> support@2kvh.com</p>
                    </div>
                </div>

                {/* Column 2: Policies */}
                <div>
                    <h3 className="text-[15px] font-bold text-gray-900 mb-6 uppercase">Về Phong Cách Xanh</h3>
                    <ul className="space-y-3">
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Giới thiệu</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Tuyển dụng</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Chính sách bảo mật</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Hệ thống cửa hàng</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Tin tức công nghệ</a></li>
                    </ul>
                </div>

                {/* Column 3: Support */}
                <div>
                    <h3 className="text-[15px] font-bold text-gray-900 mb-6 uppercase">Hỗ trợ khách hàng</h3>
                    <ul className="space-y-3">
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Chính sách giao hàng</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Chính sách đổi trả bảo hành</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Hướng dẫn mua trả góp</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Hướng dẫn thanh toán</a></li>
                        <li><a href="#" className="text-[13px] text-gray-600 hover:text-pcx-blue transition-colors">Câu hỏi thường gặp</a></li>
                    </ul>
                </div>

                {/* Column 4: Newsletter */}
                <div>
                    <h3 className="text-[15px] font-bold text-gray-900 mb-6 uppercase">Đăng ký nhận tin</h3>
                    <p className="text-[13px] text-gray-600 leading-relaxed mb-4">
                        Nhận thông tin về các sản phẩm mới và chương trình khuyến mãi sớm nhất.
                    </p>
                    <div className="flex">
                        <input
                            type="email"
                            placeholder="Nhập email của bạn"
                            className="w-full bg-gray-100 border-none py-2 px-3 text-[13px] text-gray-800 focus:outline-none focus:ring-1 focus:ring-pcx-blue"
                        />
                        <button className="bg-gray-900 text-white px-4 py-2 text-[13px] font-bold hover:bg-pcx-blue transition-colors">
                            Đăng ký
                        </button>
                    </div>
                </div>
            </div>

            <div className="max-w-7xl mx-auto px-6 mt-16 pt-6 border-t border-gray-200 flex flex-col md:flex-row justify-between items-center gap-4">
                <p className="text-gray-500 text-[12px]">&copy; 2026 2kvh (Clone of Phong Cach Xanh). All Rights Reserved.</p>
                <div className="flex gap-4">
                    {/* Placeholder for social icons */}
                    <div className="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center cursor-pointer hover:bg-pcx-blue hover:text-white transition-colors">FB</div>
                    <div className="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center cursor-pointer hover:bg-pcx-blue hover:text-white transition-colors">IG</div>
                    <div className="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center cursor-pointer hover:bg-pcx-blue hover:text-white transition-colors">YT</div>
                </div>
            </div>
        </footer>
    );
}