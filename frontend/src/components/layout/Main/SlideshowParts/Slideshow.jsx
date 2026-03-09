import React, { useRef } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Scrollbar } from "swiper/modules";
import "swiper/css";
import "swiper/css/scrollbar";
import { products } from "./products";
import SlideCard from "./SlideCard";
import { GrPrevious, GrNext } from "react-icons/gr";

export default function Slideshow() {
  const swiperRef = useRef(null);

  return (
    <div className="w-full relative py-6">
      <Swiper
        modules={[Navigation, Scrollbar]}
        spaceBetween={20}
        slidesPerView={2.5}
        breakpoints={{
          640: { slidesPerView: 3.5 },
          768: { slidesPerView: 4.5 },
          1024: { slidesPerView: 5.5 },
        }}
        scrollbar={{ draggable: true, el: '.custom-scrollbar', hide: false }}
        onBeforeInit={(swiper) => {
          swiperRef.current = swiper;
        }}
        className="!pb-6"
      >
        {products.map((product) => (
          <SwiperSlide key={product.id}>
            <SlideCard product={product} />
          </SwiperSlide>
        ))}
      </Swiper>

      {/* Custom Scrollbar and Nav container */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between mt-6 gap-6 px-1">
        <div className="custom-scrollbar h-[2px] bg-gray-200 flex-grow relative overflow-visible mr-0 sm:mr-10">
          {/* Swiper injects the scrollbar progress here as a div with absolute positioning */}
        </div>
        <div className="flex gap-2 shrink-0 self-end sm:self-auto">
          <button onClick={() => swiperRef.current?.slidePrev()} className="w-10 h-10 flex items-center justify-center rounded-full border border-gray-200 hover:border-gray-400 hover:text-black transition-colors bg-white text-gray-500">
            <GrPrevious className="text-[14px] ml-[-2px]" />
          </button>
          <button onClick={() => swiperRef.current?.slideNext()} className="w-10 h-10 flex items-center justify-center rounded-full border border-gray-200 hover:border-gray-400 hover:text-black transition-colors bg-white text-gray-500">
            <GrNext className="text-[14px]" />
          </button>
        </div>
      </div>
    </div>
  );
}
