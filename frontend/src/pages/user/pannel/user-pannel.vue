<!--
    유저 로그인 패널
    
    case 1) 로그인 한 상태일 경우
    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-panel left resizable theme-dark id="user-pannel">
        <f7-view>
            <f7-page>
                <!-- case [1] 로그인한 경우 -->
                <f7-block class="profile-wrapper" v-if="true == true"> <!--  v-if="isSignin == true" -->
                    
                    <f7-block class="profile-info">
                        <f7-block class="notify-wrapper">
                            <f7-icon f7="bell" color="white"></f7-icon>
                        </f7-block>

                        <f7-block class="grade-wrapper">
                            <f7-chip class="user-grade" text="산들바람" color="blue"></f7-chip>
                        </f7-block>
                        
                        <f7-list>
                            <f7-list-item link="#">
                                <f7-block class="user-wrapper">
                                    <p class="user-name">설동민</p>
                                    <p class="user-email">sdm3285@naver.com</p>
                                </f7-block>
                            </f7-list-item>
                        </f7-list>
                    </f7-block>
                    
                    <f7-block class='profile-content'>
                        <f7-list class="profile-menu">
                            <f7-list-item link="#" title="즐겨찾기"   ></f7-list-item>
                            <f7-list-item link="#" title="장소등록"   ></f7-list-item>
                            <f7-list-item link="#" title="커뮤니티"   ></f7-list-item>
                            <f7-list-item link="#" title="포인트 상점"></f7-list-item>
                            <f7-list-item link="#" title="고객센터"   ></f7-list-item>
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
                            로그인을 해주세요.
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
    #user-pannel { position: absolute; z-index: 9998; }
    #user-pannel .profile-wrapper { text-align: center; } 
    #user-pannel .profile-wrapper .profile-info { text-align: left; padding-bottom : 20px} 
    #user-pannel .profile-wrapper .profile-info .notify-wrapper{ text-align: right; margin-top: 15px;}
    #user-pannel .profile-wrapper .profile-info .grade-wrapper{ margin-top: 15px; margin-bottom: 15px; padding-left: 0;}
    #user-pannel .profile-wrapper .profile-info .user-wrapper{ padding-left: 0; }

    #user-pannel .profile-wrapper .profile-content {padding: 0;}
    #user-pannel .profile-wrapper .profile-content .profile-name{color:#fff; font-size: 1.1rem;}
    #user-pannel .profile-wrapper .profile-content .profile-menu{width: 100%;}
</style>

