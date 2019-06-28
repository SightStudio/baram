<!--
    앱 시작시 실행되는 인트로 페이지

    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-page class="intro">
        
        <f7-swiper pagination 
                   :params="{speed:700, loop: true, slidesPerView: 1, spaceBetween: 0, autoplay: {delay: 4000 }}"
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
            <f7-button href="/index" large fill color="green" >시작</f7-button>
            <f7-button href="/user/signin" large fill>로그인</f7-button>
            <f7-button href="/user/signup" large fill color="gray">회원가입</f7-button>
        </f7-segmented>
    </f7-page>
</template>
<script>
    export default {}
</script>
<style scoped>
    .intro-swiper{width: 100%; height: 94%; }
    .intro-swiper img {width: 100%; height: 100%}

    .intro-nav {height: 6%;}
</style>
