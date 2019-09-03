<!--
    유저 로그인 패널
    
    case 1) 로그인 한 상태일 경우
    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-panel id="user-pannel" left resizable theme-dark
              @panel:open="getUser">
        <f7-page>
            <!-- case [1] 로그인한 경우 -->
            <f7-block class="profile-wrapper" v-if="userToken != null">
                <f7-block class="profile-info">
                    <f7-button class="notify-wrapper" href="/user/recent" panel-close>
                        <f7-icon f7="bell" color="white">
                            <f7-badge color="red">{{ recentSmokeCnt }}</f7-badge>
                        </f7-icon>
                    </f7-button>

                    <f7-block class="grade-wrapper">
                        <f7-chip class="user-grade" :text="userInfo.RANK_NAME" color="blue"></f7-chip>
                    </f7-block>
                    
                    <f7-list>
                        <f7-list-item link="#">
                            <f7-block class="user-wrapper">
                                <p class="user-name" >{{ userInfo.NAME      }}</p>
                                <p class="user-email">{{ userInfo.EMAIL     }}</p>
                                <p class="user-phone">{{ userInfo.PHONE_NUM }}</p>
                            </f7-block>
                        </f7-list-item>
                    </f7-list>
                </f7-block><!-- .profile-info end -->
                
                <f7-block class='profile-content'>
                    <f7-list class="profile-menu">
                        <f7-list-item panel-close title="구역찾기"    link="/gmap"></f7-list-item>
                        <f7-list-item panel-close title="장소등록"    link="/gmap/register"></f7-list-item>
                        <f7-list-item panel-close title="즐겨찾기"    link="/user/favor"></f7-list-item>
                        <f7-list-item panel-close title="커뮤니티"    link="/community/list"></f7-list-item>
                        <f7-list-item panel-close title="포인트 상점" link="#"></f7-list-item>
                        <f7-list-item panel-close title="고객센터"    link="#"></f7-list-item>
                        <f7-list-item panel-close title="로그아웃"    link="#" @click="logout"></f7-list-item>
                    </f7-list> <!-- .profile-menu END -->
                </f7-block> <!-- .profile-content END -->
            </f7-block> <!-- .profile-warpper END -->

            <!-- case [2] 로그인 안한 경우 -->
            <f7-block class="profile-wrapper" v-else>
                <f7-block class="profile-img">
                    <img src="@/assets/image/user/smoker.png" alt="">
                </f7-block>
                
                <f7-block class='profile-content'>
                    <p> 로그인이 되어있지 않습니다. </p>
                    <p> 로그인을 해주세요.         </p>
                    
                    <f7-list class="profile-menu">
                        <f7-list-item panel-close title="구역찾기"    link="/gmap"></f7-list-item>
                        <f7-list-item panel-close title="장소등록"    link="/gmap/register"></f7-list-item>
                        <f7-list-item panel-close title="커뮤니티"    link="/community/list"></f7-list-item>
                        <f7-list-item panel-close title="포인트 상점" link="#"></f7-list-item>
                        <f7-list-item panel-close title="고객센터"    link="#"></f7-list-item>
                        <f7-list-item panel-close title="로그인"      link="/user/login"></f7-list-item>
                    </f7-list> <!-- .profile-menu END -->
                </f7-block>
            </f7-block>
        </f7-page>
    </f7-panel>
</template>

<script>
export default {
    data() {
        return {
            userToken      : null,
            userInfo       : null,
            recentSmokeCnt : 0,
            accessLog:[]
        }
    },
    methods: {
        async getUser() {

            // [1] 유저 정보
            this.userToken = this.$store.state.userJwt;
            this.userInfo  = JSON.parse(this.$store.state.userInfo);

            // [2] 최근 등록 개수 [7 일전]
            const URI = `api/user-smoke/recent/count/${7}`
            this.$http.get(URI)
                  .then( data => {
                      this.recentSmokeCnt = data.data.response.cnt.RECENT_SMOKE_CNT;
                  })
        },

        logout() {
            this.userToken = null;
            this.userInfo  = null;

            // [1] 로그인 actions 실행
            this.$store.dispatch('LOGOUT')
                       .then(() => {
                            this.$f7.dialog.alert("시작페이지로 이동합니다", '로그아웃 완료', () => {
                                this.$f7.views.main.router.navigate('/', { clearPreviousHistory : true });
                            })
                       });
            
        }
    }
}
</script>
<style scoped>
    .profile-wrapper { padding: 0; }
    #user-pannel { position: absolute; z-index: 9998; }
    #user-pannel .profile-wrapper { text-align: center; } 
    #user-pannel .profile-wrapper .profile-info { text-align: left; padding-bottom : 20px} 
    #user-pannel .profile-wrapper .profile-img img {width: 90%; margin-top: 10px;}
    #user-pannel .profile-wrapper .profile-info .notify-wrapper{ text-align: right; margin-top: 15px;}
    #user-pannel .profile-wrapper .profile-info .grade-wrapper{ margin-top: 15px; margin-bottom: 15px; padding-left: 0;}
    #user-pannel .profile-wrapper .profile-info .user-wrapper{ padding-left: 0; }

    #user-pannel .profile-wrapper .profile-content {padding: 0;}
    #user-pannel .profile-wrapper .profile-content .profile-name{color:#fff; font-size: 1.1rem;}
    #user-pannel .profile-wrapper .profile-content .profile-menu{width: 100%;}
</style>

