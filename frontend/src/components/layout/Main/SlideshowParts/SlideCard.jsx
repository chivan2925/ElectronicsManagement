export default function SlideCard({ product }) {
    return (
        <div className="bg-white w-full flex flex-col group cursor-pointer h-full">
            <div className="relative w-full flex items-center justify-center mb-4 h-[172px]">
                {product.tag && (
                    <span className={`absolute top-0 left-0 text-[10px] font-bold px-2 py-1 rounded-full z-10 text-white shadow-sm ${product.tag === 'Hết hàng' ? 'bg-gray-400' : 'bg-blue-600'}`}>
                        {product.tag}
                    </span>
                )}
                <img
                    src={product.img}
                    alt={product.name}
                    className="w-[172px] h-[172px] object-contain filter group-hover:scale-105 transition-transform duration-300"
                />
            </div>

            <div className="flex flex-col flex-grow text-left">
                <h3 className="text-[13px] font-bold text-gray-900 leading-snug line-clamp-2 mb-1 group-hover:text-blue-600 transition-colors">
                    {product.name}
                </h3>
                <p className="text-[13px] text-gray-500 mb-3">
                    {product.price}
                </p>

                {product.variants && (
                    <div className="flex gap-2 items-center mt-auto">
                        {product.variants.map((variant, idx) => (
                            <div
                                key={idx}
                                className={`w-[26px] h-9 rounded-[3px] p-[2px] border relative overflow-hidden ${variant.active ? 'border-gray-800' : 'border-gray-200 hover:border-gray-400 transition-colors'}`}
                            >
                                <img src={variant.img} alt="variant" className="w-full h-full object-contain" />
                                {variant.crossed && (
                                    <div className="absolute inset-0 flex items-center justify-center">
                                        <div className="w-[150%] h-[1.5px] bg-gray-500/80 rotate-[-55deg] origin-center -ml-1"></div>
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}
