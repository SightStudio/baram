import IntroPage            from  '{PAGE}/common/intro.vue';
import HomePage             from  '{PAGE}/common/home.vue';

/* 메인 tab view */
import MainTabView          from  '{PAGE}/main/main-tab-view.vue';

/* 메인 tab 하위 view */
import Gmap                 from  '{PAGE}/main/gmap/gmap.vue';
import RegisterMap          from  '{PAGE}/main/register/smoke-register.vue';

import Signin               from  '{PAGE}/user/signin/signin.vue';
import Signup               from  '{PAGE}/user/signup/signup.vue';
import BaramSignup          from  '{PAGE}/user/signup/baramSignup/signup.vue';

// Error Page
import NotFoundPage         from  '{PAGE}/error/404_not_found.vue';

/**
 *  페이지 경로 설정
 *  
 *  @author Dong-Min Seol
 *  @since  2019.05.10
 */
export default [
  {
    path: '/',
    component:  IntroPage,
  },
  {
    path: '/index',
    component: HomePage,
    comment: '메인 화면'
  },
  {
    path: '/main',
    component: MainTabView,
    comment: '메인 텝 화면'
  },
  {
    path: '/user/signin',
    component: Signup,
    comment: '회원 로그인'
  },
  {
    path: '/user/signup',
    component: Signup,
    comment: '회원 가입 페이지'
  },
  {
    path: '/user/signup/baram',
    component: BaramSignup,
    comment: '바람 전용 회원 가입'
  },
  {
    path      : '/gmap',
    component : Gmap,
    comment   : '금연 / 흡연구역 지도'
  },
  {
    path      : '/gmap/register',
    component : RegisterMap,
    comment   : '금연 / 흡연구역 사용자 등록'
  },
  {
    path: '(.*)',
    component: NotFoundPage,
  },
];
