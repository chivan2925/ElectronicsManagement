import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, Autoplay } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

import banner1 from "../../../assets/banner1.jpg";
import banner2 from "../../../assets/banner2.png"; // fallback

const banners = [
  {
    id: 1,
    img: banner1,
    subtitle: "Bộ sưu tập esport hợp tác giữa Pulsar và VAXEE",
    title: "Pulsar eS 2026 - <br /> Hoàn toàn mới",
    linkText: "Xem chi tiết"
  },
  {
    id: 2,
    img: banner1, // Replace with another image if available
    subtitle: "Trải nghiệm cảm giác gõ hoàn hảo",
    title: "Bàn phím cơ HE - <br /> Độ trễ cực thấp",
    linkText: "Khám phá ngay"
  }
];

export default function HomeBanner() {
  return (
    <div className="w-full relative group banner-slider-container">
      <Swiper
        modules={[Navigation, Pagination, Autoplay]}
        slidesPerView={1}
        loop={true}
        autoplay={{ delay: 5000, disableOnInteraction: false }}
        pagination={{
          clickable: true,
          renderBullet: function (index, className) {
            return '<span class="' + className + ' custom-bullet">' + (index + 1) + '</span>';
          }
        }}
        navigation={{
          nextEl: '.swiper-button-next-custom',
          prevEl: '.swiper-button-prev-custom',
        }}
        className="h-[500px] md:h-[650px] w-full"
      >
        {banners.map((slide) => (
          <SwiperSlide key={slide.id}>
            <div
              className="w-full h-full bg-cover bg-center relative"
              style={{ backgroundImage: `url(${slide.img})` }}
            >
              <div className="absolute inset-0 bg-black/40 flex flex-col justify-center items-center text-center px-4">
                <div className="z-10 mt-10">
                  <h3 className="text-gray-200 text-sm md:text-lg font-medium mb-3 tracking-wide drop-shadow-md">
                    {slide.subtitle}
                  </h3>
                  <h1
                    className="text-white text-5xl md:text-7xl font-extrabold mb-10 leading-[1.15] drop-shadow-xl"
                    dangerouslySetInnerHTML={{ __html: slide.title }}
                  />
                  <button className="bg-white text-black font-bold py-3.5 px-10 rounded-sm hover:bg-gray-100 transition-colors shadow-lg text-[15px]">
                    {slide.linkText}
                  </button>
                </div>
              </div>
            </div>
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Custom Navigation Arrows */}
      <div className="hidden md:flex absolute top-1/2 -translate-y-1/2 left-8 z-20 w-12 h-12 bg-white/10 backdrop-blur-sm border border-white/20 rounded-full items-center justify-center text-white cursor-pointer hover:bg-white/30 transition-colors swiper-button-prev-custom">
        <svg className="w-5 h-5 ml-[-2px]" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M15 19l-7-7 7-7" /></svg>
      </div>
      <div className="hidden md:flex absolute top-1/2 -translate-y-1/2 right-8 z-20 w-12 h-12 bg-white/10 backdrop-blur-sm border border-white/20 rounded-full items-center justify-center text-white cursor-pointer hover:bg-white/30 transition-colors swiper-button-next-custom">
        <svg className="w-5 h-5 ml-[2px]" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M9 5l7 7-7 7" /></svg>
      </div>

      {/* Target styles for custom bullets */}
      <style dangerouslySetInnerHTML={{
        __html: `
        .banner-slider-container .swiper-pagination {
          bottom: 30px !important;
          right: 30px !important;
          left: auto !important;
          width: auto !important;
          display: flex;
          gap: 12px;
        }
        .banner-slider-container .custom-bullet {
          width: 32px;
          height: 32px;
          display: flex;
          align-items: center;
          justify-content: center;
          border-radius: 50%;
          border: 1px solid rgba(255,255,255,0.4);
          color: rgba(255,255,255,0.7);
          font-size: 13px;
          font-weight: 600;
          cursor: pointer;
          transition: all 0.3s ease;
          opacity: 1;
          background: transparent;
        }
        .banner-slider-container .custom-bullet:hover {
          color: white;
          border-color: white;
        }
        .banner-slider-container .custom-bullet.swiper-pagination-bullet-active {
          background: rgba(255,255,255,0.2);
          color: white;
          border-color: white;
        }
      `}} />
    </div>
  );
}
