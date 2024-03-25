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
        <div className='w-[100%] h-[100%]'>          
            <Swiper
                pagination={{ clickable: true }}
                className='w-[100%] h-[100%]'
                slidesPerView={1}
                
                autoplay={{ delay: 2000 }}
                loop={true}
                // centeredSlidesBounds={true}
               
            >   
                <SwiperSlide className={styles.card}>롤 플레이어의 전적 데이터를 분석하여 플레이어에게 적합한 챔피언 및 전략을 추천합니다. 이 기능은 플레이어의 플레이 스타일, 선호하는 챔피언, 최근 전적 등을 고려하여 맞춤형 추천을 제공하여 플레이어가 게임에서 더욱 효과적으로 활약할 수 있도록 돕습니다.</SwiperSlide>
                <SwiperSlide className={styles.card}>롤 게임에서 발생하는 다양한 상황 및 논란에 대해 토론할 수 있는 게시판을 제공합니다. 특히, 플레이어들이 게임 상황에서 경험한 문제를 공유하고, 해당 상황에서 어떤 플레이어의 귀책이 큰지를 논의할 수 있는 공간을 제공합니다. 이를 통해 게임 커뮤니티 간의 의견 교환과 합의를 이끌어내어 게임 플레이의 질을 향상시킵니다.</SwiperSlide>
                <SwiperSlide className={styles.card}>친구와 실시간으로 음성 채팅을 할 수 있는 기능을 제공합니다. 이를 통해 플레이어들은 게임 중에도 효율적으로 의사소통하고 전략을 수립할 수 있습니다. 게임 전략에 필요한 정보를 실시간으로 공유하고, 팀원들과의 협력을 강화하여 승리에 도달할 수 있도록 돕습니다.플레이어들의 전적 데이터를 분석하여 게임에서의 성과를 분석하고 통계를 제공합니다. 이를 통해 플레이어는 자신의 플레이 스타일과 강점, 약점을 파악하고 개선할 수 있는 방향을 찾을 수 있습니다. 또한, 상위 랭킹 플레이어들의 전략과 플레이 스타일을 분석하여 배울 수 있는 기회를 제공합니다.</SwiperSlide>
            </Swiper>
        </div>
    );
}
