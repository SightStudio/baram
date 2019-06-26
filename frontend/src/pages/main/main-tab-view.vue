<template>
    <f7-page>
        <f7-tabs>
            <f7-tab id="tab-1" class="page-content" tab-active>
                <Gmap></Gmap>
            </f7-tab>
            
            <f7-tab id="tab-2" class="page-content">
                <f7-block>
                </f7-block>
            </f7-tab>
            
            <f7-tab id="tab-3" class="page-content">
                <f7-block>
                </f7-block>
            </f7-tab>

            <f7-tab id="tab-4" class="page-content">
                <RegisterMap></RegisterMap>
            </f7-tab>

            <f7-tab id="tab-5" class="page-content">
                <f7-block>
                </f7-block>
            </f7-tab>
        </f7-tabs>

        <f7-toolbar tabbar bottom>
            <f7-link
                tab-link="#tab-1"
                :animate="false">
                <f7-icon f7="home"></f7-icon>
            </f7-link>
            
            <f7-link 
                tab-link="#tab-2"
                :animate="false">
                <f7-icon f7="bars"></f7-icon>
            </f7-link>

            <f7-link
                tab-link="#tab-3"
                :animate="false">
                <f7-icon f7="search"></f7-icon>
            </f7-link>

            <f7-link
                tab-link="#tab-4"
                :animate="false">
                <f7-icon f7="add"></f7-icon>
            </f7-link>

            <f7-link
                tab-link="#tab-5"
                :animate="false">
                <f7-icon f7="person"></f7-icon>
            </f7-link>
        </f7-toolbar>

    </f7-page>
</template>
<script>
/* 지도 관련 API */
import Gmap          from  '{PAGE}/main/gmap/gmap.vue';
import RegisterMap   from  '{PAGE}/main/register/smoke-register.vue'

export default {
    components : {
        Gmap,
        RegisterMap
    },
    mounted() { },
}
</script>
<style scoped>
    .tabs{height : 100%; width: 100%;}
</style>
