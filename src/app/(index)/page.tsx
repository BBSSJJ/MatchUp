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
        <>
            <p>This is the landing page...</p>
            <Swiper
                slidesPerView={1}
                navigation
                pagination={{ clickable: true }}
                autoplay={{ delay: 3000 }}
            >   
                <SwiperSlide><div className={styles.container}>slide1</div></SwiperSlide>
                <SwiperSlide><div className={styles.container}>slide2</div></SwiperSlide>
                <SwiperSlide><div className={styles.container}>slide3</div></SwiperSlide>
            </Swiper>
        </>
    );
}
