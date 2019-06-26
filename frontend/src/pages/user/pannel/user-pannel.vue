<!--
    유저 로그인 패널
    
    case 1) 로그인 한 상태일 경우
    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-panel right resizable theme-dark >
        <f7-view>
            <f7-page>
                <!-- case [1] 로그인한 경우 -->
                <f7-block class="profile-wrapper" v-if="true == true"> <!--  v-if="isSignin == true" -->
                    
                    <f7-block class="profile-img">
                        <img src="@/assets/image/user/profile.png" alt="">
                    </f7-block>
                    
                    <f7-block class='profile-content'>
                        <p class="profile-name">설동민<em>님</em></p>
                        <br>
                        <f7-list class="profile-menu">
                            <f7-list-item title="내 정보"   link="#"></f7-list-item>
                            <f7-list-item title="최근 조회 장소" link="#"></f7-list-item>
                            <f7-list-item title="도움말"  link="#"></f7-list-item>
                            <f7-list-item title="설정"  link="#"></f7-list-item>
                            <f7-list-item title="로그아웃"  link="#"></f7-list-item>
                        </f7-list> <!-- .profile-menu END -->
                    </f7-block> <!-- .profile-content END -->
                </f7-block> <!-- .profile-warpper END -->

                <!-- case [2] 로그인 안한 경우 -->
                <f7-block class="profile-wrapper" v-else>
                    <f7-block class="profile-img">
                        <img src="@/assets/image/user/smoker.png" alt="">
                    </f7-block>
                    
                    <f7-block class='profile-content'>
                        <p>
                            로그인이 되어있지 않습니다. 
                            로그인을 해주세요
                        </p>
                    </f7-block>
                </f7-block>
            </f7-page>
        </f7-view>
    </f7-panel>
</template>

<script>
export default { }
</script>
<style scoped>
    .profile-wrapper{padding: 0;}
    .panel-right { position: absolute; z-index: 9998; }
    .panel-right .profile-wrapper { text-align: center; } 
    
    .panel-right .profile-wrapper .profile-img { margin : 30px 0 10px 0; width : 80%; height: 25%;display: inline-block;}
    .panel-right .profile-wrapper .profile-img img{ display: inline-block; width: 100%; height: 100%;}

    /* .panel-right .profile-wrapper .profile-content {} #171717*/
    .panel-right .profile-wrapper .profile-content {padding: 0;}
    .panel-right .profile-wrapper .profile-content .profile-name{color :#fff; font-size: 1.1rem;}
    .panel-right .profile-wrapper .profile-content .profile-menu{width: 100%;}
</style>

