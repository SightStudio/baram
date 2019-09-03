import IntroPage        from  '{PAGE}/common/intro.vue';
import HomePage         from  '{PAGE}/common/home.vue';

/* user-pannel 메뉴 */
import Gmap             from  '{PAGE}/main/gmap/gmap.vue';
import RegisterMap      from  '{PAGE}/main/register/smoke-register.vue';
import Login            from  '{PAGE}/user/login/login.vue';
import BaramSignup      from  '{PAGE}/user/signup/baramSignup/signup.vue';
import Favor            from  '{PAGE}/user/favor/favor-list.vue';
import RecentList       from  '{PAGE}/user/recent/recent-list.vue';

import CommnityList     from  '{PAGE}/main/community/community-list.vue';
import CommnityWrite    from  '{PAGE}/main/community/community-write.vue';
import CommnityView     from  '{PAGE}/main/community/community-view.vue';

// Error Page
import NotFoundPage     from  '{PAGE}/error/404_not_found.vue';

/**
 *  페이지 경로 설정
 *  
 *  @author Dong-Min Seol
 *  @since  2019.05.10
 */
export default [
  { path: '/'                     , component: IntroPage    , comment: '초기화면'                  },
  { path: '/index'                , component: HomePage     , comment: '메인 화면'                 },
  { path: '/user/login'           , component: Login        , comment: '회원 로그인'               },
  { path: '/user/signup'          , component: BaramSignup  , comment: '바람 전용 회원 가입'        },
  { path: '/user/favor'           , component: Favor        , comment: '회원 즐겨찾기'              },
  { path: '/user/recent'          , component: RecentList   , comment: '최근 추가된 리스트'         },
  { path: '/gmap'                 , component: Gmap         , comment: '금연 / 흡연구역 지도'       },
  { path: '/gmap/:lat&:lng/:type' , component: Gmap         , comment: '금연 / 흡연구역 지도'       },
  { path: '/gmap/register'        , component: RegisterMap  , comment: '금연 / 흡연구역 사용자 등록' },
  { path: '/community/list'       , component: CommnityList , comment: '커뮤니티 페이지'            },
  { path: '/community/write'      , component: CommnityWrite, comment: '커뮤니티 게시글 작성 페이지' },
  { path: '/community/view/:seq/' , component: CommnityView , comment: '커뮤니티 게시글 조회 페이지' },
  { path: '(.*)'                  , component: NotFoundPage , comment: '404 페이지'                },
];
