import React, { useEffect, useState } from "react";
import "swiper/css";
import "swiper/css/pagination";
import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Pagination } from "swiper/modules";
import axios from "axios";

function Carousel(numberOfSlides = 1, autoplay = false) {
  const [banners, setBanners] = useState([]);

  const fetchBanner = async () => {
    const response = await axios.get("");
    setBanners(response.data);
  };

  useEffect(() => {
    fetchBanner();
  }, []);

  return (
    <div>
      <Swiper
        autoplay={{ delay: 2500, disableOnInteraction: false }}
        pagination={true}
        modules={[Pagination, Autoplay]}
        className="mySwiper"
        // slidesPerView={numberOfSlides}
      >
        {/* {banners.map((banner) => {
          <SwiperSlide>
            <img src={banner.image} alt="" />
          </SwiperSlide>;
        })} */}

        <SwiperSlide>
          <img
            src="https://image.tmdb.org/t/p/original/ciizJ9Okzt9tBBGK7Q3T14LFT2j.jpg"
            alt=""
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src="https://image.tmdb.org/t/p/original/j5aOOtsJinQtyRaqiB12TTRHdeX.jpg"
            alt=""
          />
        </SwiperSlide>
        <SwiperSlide>
          <img
            src="https://image.tmdb.org/t/p/original/foGkPxpw9h8zln81j63mix5B7m8.jpg"
            alt=""
          />
        </SwiperSlide>
      </Swiper>
    </div>
  );
}

export default Carousel;
