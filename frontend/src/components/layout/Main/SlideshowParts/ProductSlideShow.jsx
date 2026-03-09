import Slideshow from "./Slideshow";
export default function ProductSlideShow() {
  return (
    <div className="bg-white py-16 w-full mt-4">
      <div className="max-w-7xl mx-auto px-6">
        <div className="flex justify-between items-end mb-10">
          <div>
            <p className="text-gray-800 font-bold text-sm mb-3">Tụi mình luôn cắm thử trước khi gear đến tay bạn. Mới về, đã test, sẵn sàng chiến rổi đó.</p>
            <h2 className="text-[40px] font-extrabold text-gray-900 tracking-tight">Gear mới nóng hổi</h2>
          </div>
          <a href="#" className="text-gray-600 hover:text-black font-semibold flex items-center gap-2 text-sm transition-colors group pb-2">
            Xem hàng mới về
            <span className="bg-gray-100 text-gray-400 p-1.5 rounded-full group-hover:bg-gray-200 transition-colors">
              <svg className="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M9 5l7 7-7 7" /></svg>
            </span>
          </a>
        </div>
        <Slideshow />
      </div>
    </div>
  );
}
