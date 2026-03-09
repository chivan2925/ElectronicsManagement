import React from "react";

const categories = [
    {
        id: 1,
        name: "Chuột gaming",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-12 h-12 transition-colors duration-300">
                <rect x="5" y="2" width="14" height="20" rx="7" stroke="currentColor" strokeWidth="1.5" />
                <path d="M5 9C5 9 12 9 19 9" stroke="currentColor" strokeWidth="1.5" />
                <path d="M12 2L12 9" stroke="currentColor" strokeWidth="1.5" />
                <rect x="11" y="4" width="2" height="3" rx="1" fill="currentColor" />
            </svg>
        ),
    },
    {
        id: 2,
        name: "Bàn phím cơ & HE",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-12 h-12 transition-colors duration-300">
                <rect x="2" y="6" width="20" height="12" rx="2" stroke="currentColor" strokeWidth="1.5" />
                <rect x="4" y="8" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="7" y="8" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="10" y="8" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="13" y="8" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="16" y="8" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="4" y="11" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="7" y="11" width="2" height="2" rx="0.5" fill="currentColor" />
                <rect x="10" y="11" width="2" height="2" rx="0.5" fill="currentColor" />
                <rect x="13" y="11" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="16" y="11" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="4" y="14" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
                <rect x="7" y="14" width="8" height="2" rx="0.5" fill="currentColor" />
                <rect x="16" y="14" width="2" height="2" rx="0.5" stroke="currentColor" strokeWidth="1" />
            </svg>
        ),
    },
    {
        id: 3,
        name: "Lót chuột",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-12 h-12 transition-colors duration-300">
                <rect x="3" y="5" width="18" height="14" rx="2" stroke="currentColor" strokeWidth="1.5" />
                <path d="M16 19L21 14V17C21 18.1046 20.1046 19 19 19H16Z" fill="currentColor" />
            </svg>
        ),
    },
    {
        id: 4,
        name: "Phụ kiện chuột gaming",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-12 h-12 transition-colors duration-300">
                <rect x="6" y="4" width="12" height="4" rx="2" stroke="currentColor" strokeWidth="1.5" />
                <rect x="5" y="10" width="14" height="4" rx="2" stroke="currentColor" strokeWidth="1.5" />
                <rect x="7" y="16" width="10" height="4" rx="2" stroke="currentColor" strokeWidth="1.5" />
            </svg>
        ),
    },
    {
        id: 5,
        name: "Ghế công thái học",
        icon: (
            <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" className="w-12 h-12 transition-colors duration-300">
                <path d="M8 2H16C17.1046 2 18 2.89543 18 4V12H6V4C6 2.89543 6.89543 2 8 2Z" stroke="currentColor" strokeWidth="1.5" />
                <rect x="9" y="5" width="6" height="3" rx="1" fill="currentColor" />
                <path d="M4 12H20V14C20 15.1046 19.1046 16 18 16H6C4.89543 16 4 15.1046 4 14V12Z" stroke="currentColor" strokeWidth="1.5" />
                <path d="M12 16V22" stroke="currentColor" strokeWidth="1.5" />
                <path d="M8 22H16" stroke="currentColor" strokeWidth="1.5" />
                <circle cx="9" cy="22" r="1" fill="currentColor" />
                <circle cx="15" cy="22" r="1" fill="currentColor" />
            </svg>
        ),
    },
];

export default function CategoryRow() {
    return (
        <div className="bg-[#f5f5f5] py-8 w-full border-b border-gray-100">
            <div className="max-w-7xl mx-auto px-6">
                <div className="flex justify-between items-center bg-white shadow-sm overflow-x-auto overflow-y-hidden min-w-max md:min-w-0">
                    {categories.map((cat, index) => (
                        <div
                            key={cat.id}
                            className={`flex flex-col items-center justify-center p-8 flex-1 min-w-[200px] h-[180px] cursor-pointer group hover:bg-pcx-blue transition-colors 
              ${index !== categories.length - 1 ? "border-r border-gray-100" : ""}`}
                        >
                            <div className="text-gray-900 group-hover:text-white mb-4 flex items-center justify-center">
                                {cat.icon}
                            </div>
                            <span className="font-bold text-[14px] text-gray-900 group-hover:text-white text-center">
                                {cat.name}
                            </span>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
