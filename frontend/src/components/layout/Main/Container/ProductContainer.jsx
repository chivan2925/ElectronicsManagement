import { useState } from "react";
import ProductCard from "../../../common/ProductCard";
import lap1 from "../../../../assets/lap1.png";
import lap2 from "../../../../assets/lap2.png";
import Pagination from "../../../common/Pagination";

export default function ProductContainer() {
  const allProducts = [
    {
      id: 1,
      name: "RAM Kingston Fury Beast 16GB",
      price: "2.500.000đ",
      imageUrl: lap1,
    },
    {
      id: 2,
      name: "CPU Intel Core i9-13900K",
      price: "15.000.000đ",
      imageUrl: lap2,
    },
    { id: 3, name: "VGA RTX 4090", price: "50.000.000đ" },
    { id: 4, name: "Laptop Gaming Acer Nitro 5", price: "25.000.000đ" },
    { id: 5, name: "Mainboard ASUS ROG STRIX Z790", price: "10.000.000đ" },
    { id: 6, name: "Ổ cứng SSD Samsung 980 Pro 1TB", price: "3.000.000đ" },
    { id: 7, name: "Nguồn Corsair RM850x", price: "4.000.000đ" },
    { id: 8, name: "Tản nhiệt nước Cooler Master", price: "3.500.000đ" },
    { id: 9, name: "Product 9", price: "1.000.000đ" },
    { id: 10, name: "Product 10", price: "1.000.000đ" },
    { id: 11, name: "Product 11", price: "1.000.000đ" },
    { id: 12, name: "Product 12", price: "1.000.000đ" },
  ];

  const [page, setPage] = useState(1);
  const pageSize = 8;

  const end = page * pageSize;
  const start = end - pageSize;
  const pageItems = allProducts.slice(start, end);

  const paginate = (pageNumber) => setPage(pageNumber);

  return (
    <div className="bg-white py-16 w-full pb-24">
      <div className="max-w-7xl mx-auto px-6">
        <div className="flex justify-between items-end mb-10">
          <div>
            <p className="text-gray-800 font-bold text-sm mb-3">Hàng nghìn game thủ đã chốt đơn. Bạn thì sao?</p>
            <h2 className="text-[40px] font-extrabold text-gray-900 tracking-tight">Top bán chạy - con số không biết nói dối</h2>
          </div>
          <a href="#" className="text-gray-600 hover:text-black font-semibold flex items-center gap-2 text-sm transition-colors group pb-2">
            Xem top bán chạy
            <span className="bg-gray-100 text-gray-400 p-1.5 rounded-full group-hover:bg-gray-200 transition-colors">
              <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M9 5l7 7-7 7" /></svg>
            </span>
          </a>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
          {pageItems.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>

        <Pagination
          pageSize={pageSize}
          totalItems={allProducts.length}
          onPageChange={paginate}
          page={page}
        />
      </div>
    </div>
  );
}
