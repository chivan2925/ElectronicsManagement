import { FaSearch, FaUser, FaShoppingCart } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useState } from "react";
import Cart from "./Cart/Cart";

export default function Header() {
  const [openCart, setOpenCart] = useState(false);
  return (
    <header className="bg-white w-full sticky top-0 z-50 shadow-sm border-b border-gray-100">
      <div className="max-w-7xl mx-auto h-[70px] px-6 flex items-center justify-between">
        {/* logo */}
        <div className="flex-shrink-0">
          <Link to="/">
            <span className="text-3xl font-extrabold text-gray-900 cursor-pointer tracking-wider">
              2KVH
            </span>
          </Link>
        </div>

        {/* menu */}
        <nav className="flex gap-7 text-gray-800 text-[14px] font-bold whitespace-nowrap pt-1">
          <span className="cursor-pointer hover:text-pcx-blue transition-colors flex items-center gap-1 group">
            Gaming Gear
            <svg className="w-3 h-3 text-gray-400 group-hover:text-pcx-blue transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M19 9l-7 7-7-7" /></svg>
          </span>
          <span className="cursor-pointer hover:text-pcx-blue transition-colors flex items-center gap-1 group">
            Office Gear
            <svg className="w-3 h-3 text-gray-400 group-hover:text-pcx-blue transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M19 9l-7 7-7-7" /></svg>
          </span>
          <span className="cursor-pointer hover:text-pcx-blue transition-colors">
            Blog
          </span>
          <span className="cursor-pointer hover:text-pcx-blue transition-colors">
            Sản Phẩm 2N
          </span>
          <span className="cursor-pointer hover:text-pcx-blue transition-colors">
            Tích Điểm EXP
          </span>
          <span className="cursor-pointer hover:text-pcx-blue transition-colors">
            Liên Hệ
          </span>
        </nav>

        {/* button */}
        <div className="flex items-center gap-6">
          <div className="relative flex items-center">
            <input
              type="text"
              placeholder="Tìm chuột, bàn phím, tai nghe..."
              className="w-64 bg-transparent border-none py-2 px-1 text-[13px] text-gray-800 focus:outline-none placeholder-gray-400"
            />
            <div className="absolute left-0 top-1/2 -translate-y-1/2 w-[2px] h-4 bg-pcx-blue rounded-full"></div>
            <FaSearch className="absolute right-0 top-1/2 -translate-y-1/2 text-gray-400 text-[15px] cursor-pointer hover:text-pcx-blue" />
          </div>

          <div className="flex items-center gap-5 text-gray-700 text-lg">
            <div className="cursor-pointer hover:text-pcx-blue transition-colors">
              <FaUser />
            </div>
            <div className="relative cursor-pointer hover:text-pcx-blue transition-colors" onClick={() => setOpenCart(!openCart)}>
              <FaShoppingCart />
              <span className="text-white absolute -top-2 -right-2 bg-pcx-blue text-[9px] font-bold w-4 h-4 rounded-full flex items-center justify-center">
                10
              </span>
              {openCart && <Cart onClose={() => setOpenCart(false)} />}
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}
