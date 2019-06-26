<template>
  <!-- App -->
  <f7-app :params="f7params">

    <!-- Statusbar -->
    <f7-statusbar></f7-statusbar>

    <userPannel></userPannel>

    <!-- Main View -->
    <f7-view id="main-view" url="/" main class="safe-areas"></f7-view>

  </f7-app>
</template>

<script>
import routes     from '{ROUTE}/routes.js'
import userPannel from '{PAGE}/user/pannel/user-pannel.vue'

export default {
  data() {
    return {
      // Framework7 parameters here
      f7params: {
        id   : 'io.framework7.testapp', // App bundle ID
        name : 'Framework7',            // App name
        theme: 'ios',                   // Automatic theme detection

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