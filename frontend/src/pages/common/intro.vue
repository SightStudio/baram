<!--
    앱 시작시 실행되는 인트로 페이지

    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-page class="intro"
             @page:mounted="onAutoPlay"
             @page:beforeout="offAutoPlay">
        
        <f7-swiper pagination 
                   :params="swiperConfig"
                   class="intro-swiper">
            <f7-swiper-slide>
                <img src="@/assets/image/intro/slide1.jpg" alt="">
            </f7-swiper-slide>
            <f7-swiper-slide>
                <img src="@/assets/image/intro/slide2.jpg" alt="">
            </f7-swiper-slide>
            <f7-swiper-slide>
                <img src="@/assets/image/intro/slide3.jpg" alt="">
            </f7-swiper-slide>
            <f7-swiper-slide>
                <img src="@/assets/image/intro/slide4.jpg" alt="">
            </f7-swiper-slide>
        </f7-swiper>

        <f7-segmented no-gap class="intro-nav">
            <f7-button href="/gmap"       color="blue" large fill>시작</f7-button>
            <f7-button href="/user/login" color="gray" large fill>로그인</f7-button>
        </f7-segmented>
    </f7-page>
</template>
<script>
    export default {
        data(){
            return {
                swiperConfig : {speed:700, loop: true, slidesPerView: 1, spaceBetween: 0, autoplay: {delay: 4000}}
            }
        },
        methods : {
            onAutoPlay()  { this.swiperConfig.autoplay = {delay: 4000}; },
            offAutoPlay() { this.swiperConfig.autoplay = false; }
        },
    }
</script>
<style scoped>
    .button{
        height: 100%;
        --f7-button-border-radius: 0;
        --f7-button-large-height : 70px;
        --f7-button-large-font-size : 1.3rem;
    }
    .intro-swiper{width: 100%; height: 90%;}
    .intro-swiper img {width: 100%; height: 100%}
    .intro-nav {height: 10%;}
</style>
