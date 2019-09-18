<template>
  <!-- App -->
  <f7-app :params="f7params">

    <!-- Statusbar -->
    <f7-statusbar></f7-statusbar>

    <userPannel></userPannel>

    <!-- Main View -->
    <f7-view 
        main
        id="main-view"
        url="/"
        class="safe-areas"
        :iosSwipeBack="false"
    ></f7-view>
  </f7-app>
</template>

<script>
import routes     from '{ROUTE}/routes.js'
import userPannel from '{PAGE}/user/pannel/user-pannel.vue'

export default {
  data() {
    return {

      f7params: {
        id   : 'sight.spa.baram', // App bundle ID
        name : 'Framework7',      // App name
        //theme: 'auto',            // Automatic theme detection
        theme: 'ios',
        
        // App routes
        routes: routes,
      },
    }
  },
  components : {
    userPannel
  }
}
</script>