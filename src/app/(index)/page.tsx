"use client"
import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Scrollbar, Autoplay, Pagination } from 'swiper/modules';
import SwiperCore from 'swiper';

import styles from './styles.module.css';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';


SwiperCore.use([Navigation, Pagination, Autoplay]);

export default function IndexPage() {
    return (
        <div className='flex items-center justify-center flex-col h-screen'>          
            <Swiper
                pagination={{ clickable: true }}
                className='max-w-[90%] lg:max-w-[80%]'
                slidesPerView={1}
                navigation
                autoplay={{ delay: 5000 }}
                loop={true}
                // centeredSlidesBounds={true}
               
            >   
                <SwiperSlide ><div className={styles.card}>slide1</div></SwiperSlide>
                <SwiperSlide ><div className={styles.card}>slide2</div></SwiperSlide>
                <SwiperSlide ><div className={styles.card}>slide3</div></SwiperSlide>
            </Swiper>
        </div>
    );
}
