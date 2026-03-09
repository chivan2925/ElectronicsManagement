import ram1 from "../../assets/ram1.png";

export default function ProductCard({ product }) {
    const displayProduct = product || {
        name: "Tên sản phẩm mẫu",
        price: "10.000.000đ",
        imageUrl: ram1
    };

    return (
        <div className="bg-white group overflow-hidden flex flex-col h-full cursor-pointer pb-4">
            <div className="w-full h-[172px] flex items-center justify-center mb-4 relative">
                <img src={displayProduct.imageUrl || displayProduct.img} alt={displayProduct.name} className="w-[172px] h-[172px] object-contain transition-transform duration-300 filter group-hover:scale-105" />
            </div>
            <div className="flex flex-col flex-grow text-left px-2">
                <div className="flex justify-between items-start gap-2 mb-1">
                    <h3 className="text-[13px] font-bold text-gray-900 leading-snug group-hover:text-pcx-blue transition-colors line-clamp-2" title={displayProduct.name}>
                        {displayProduct.name}
                    </h3>
                    <div className="flex shrink-0 items-center gap-1 text-[13px] text-gray-500 font-medium mt-[2px]">
                        5.0 <span className="text-yellow-400">★</span>
                    </div>
                </div>
                <p className="text-[13px] text-gray-500 mt-auto pt-1">
                    {displayProduct.price}
                </p>
            </div>
        </div>
    );
}